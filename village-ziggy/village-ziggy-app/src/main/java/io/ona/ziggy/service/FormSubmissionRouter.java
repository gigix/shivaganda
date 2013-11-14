package io.ona.ziggy.service;

import io.ona.ziggy.domain.form.FormSubmission;
import io.ona.ziggy.repository.FormDataRepository;

import java.util.HashMap;
import java.util.Map;

import static io.ona.ziggy.event.Event.FORM_SUBMITTED;
import static io.ona.ziggy.util.Log.logWarn;

public class FormSubmissionRouter {
    private final Map<String, FormSubmissionHandler> handlerMap;
    private FormDataRepository formDataRepository;

    public FormSubmissionRouter(FormDataRepository formDataRepository,
                                String formName, FormSubmissionHandler formSubmissionHandler) {
        this.formDataRepository = formDataRepository;
        handlerMap = new HashMap<String, FormSubmissionHandler>();
        handlerMap.put(formName, formSubmissionHandler);
    }

    public void route(String instanceId) {
        FormSubmission submission = formDataRepository.fetchFromSubmission(instanceId);
        FormSubmissionHandler handler = handlerMap.get(submission.formName());
        if (handler == null) {
            logWarn("Could not find a handler due to unknown form submission: " + submission);
        } else {
            handler.handle(submission);
        }
        FORM_SUBMITTED.notifyListeners(instanceId);
    }
}
