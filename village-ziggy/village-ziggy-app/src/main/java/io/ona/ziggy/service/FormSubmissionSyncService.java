package io.ona.ziggy.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.ona.ziggy.domain.FetchStatus;
import io.ona.ziggy.domain.Response;
import io.ona.ziggy.domain.form.FormSubmission;
import io.ona.ziggy.dto.FormSubmissionDTO;
import io.ona.ziggy.repository.AllSettings;
import io.ona.ziggy.repository.FormDataRepository;

import java.util.ArrayList;
import java.util.List;

import static org.thoughtworkers.shivaganda.village.AllConstants.SERVER_BASE_URL;
import static io.ona.ziggy.convertor.FormSubmissionConvertor.toDomain;
import static io.ona.ziggy.domain.FetchStatus.*;
import static io.ona.ziggy.util.Log.*;
import static java.text.MessageFormat.format;

public class FormSubmissionSyncService {
    public static final String FORM_SUBMISSIONS_PATH = "/form-submissions";
    private final HTTPAgent httpAgent;
    private final FormDataRepository formDataRepository;
    private AllSettings allSettings;
    private FormSubmissionService formSubmissionService;

    public FormSubmissionSyncService(FormSubmissionService formSubmissionService, HTTPAgent httpAgent, FormDataRepository formDataRepository, AllSettings allSettings) {
        this.formSubmissionService = formSubmissionService;
        this.httpAgent = httpAgent;
        this.formDataRepository = formDataRepository;
        this.allSettings = allSettings;
    }

    public FetchStatus sync() {
        pushToServer();
        return pullFromServer();
    }

    public void pushToServer() {
        List<FormSubmission> pendingFormSubmissions = formDataRepository.getPendingFormSubmissions();
        logInfo(format("Number of pending submissions: {0}", pendingFormSubmissions.size()));
        if (pendingFormSubmissions.isEmpty()) {
            logWarn("Pending submission list is empty");
            return;
        }
        String jsonPayload = mapToFormSubmissionDTO(pendingFormSubmissions);
        Response<String> response = httpAgent.postJSONRequest(SERVER_BASE_URL + FORM_SUBMISSIONS_PATH, jsonPayload);
        if (response.isFailure()) {
            logError(format("Form submissions sync failed. Submissions:  {0}", pendingFormSubmissions));
            return;
        }
        formDataRepository.markFormSubmissionsAsSynced(pendingFormSubmissions);
        logInfo(format("Form submissions sync successfully. Submissions:  {0}", pendingFormSubmissions));
    }

    public FetchStatus pullFromServer() {
        String uri = SERVER_BASE_URL + FORM_SUBMISSIONS_PATH + "?reporter-id=" + allSettings.fetchRegisteredReporter() + "&timestamp=" + allSettings.fetchPreviousFormSyncIndex();
        Response<String> response = httpAgent.fetch(uri);
        if (response.isFailure()) {
            logError(format("Form submissions pull failed."));
            return fetchedFailed;
        }
        List<FormSubmissionDTO> formSubmissions = new Gson().fromJson(response.payload(), new TypeToken<List<FormSubmissionDTO>>() {
        }.getType());
        if (formSubmissions.isEmpty()) {
            return nothingFetched;
        }
        formSubmissionService.processSubmissions(toDomain(formSubmissions));
        return fetched;
    }

    private String mapToFormSubmissionDTO(List<FormSubmission> pendingFormSubmissions) {
        List<FormSubmissionDTO> formSubmissions = new ArrayList<FormSubmissionDTO>();
        for (FormSubmission pendingFormSubmission : pendingFormSubmissions) {
            formSubmissions.add(new FormSubmissionDTO(allSettings.fetchRegisteredReporter(), pendingFormSubmission.instanceId(),
                    pendingFormSubmission.entityId(), pendingFormSubmission.formName(), pendingFormSubmission.instance(), pendingFormSubmission.version(),
                    pendingFormSubmission.formDataDefinitionVersion()));
        }
        return new Gson().toJson(formSubmissions);
    }
}
