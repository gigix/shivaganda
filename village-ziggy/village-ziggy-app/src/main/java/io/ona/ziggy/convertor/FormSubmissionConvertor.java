package io.ona.ziggy.convertor;

import io.ona.ziggy.domain.form.FormSubmission;
import io.ona.ziggy.dto.FormSubmissionDTO;

import java.util.ArrayList;
import java.util.List;

import static io.ona.ziggy.domain.SyncStatus.SYNCED;

public class FormSubmissionConvertor {
    public static List<FormSubmission> toDomain(List<FormSubmissionDTO> formSubmissionsDto) {
        List<FormSubmission> submissions = new ArrayList<FormSubmission>();
        for (FormSubmissionDTO formSubmission : formSubmissionsDto) {
            submissions.add(new FormSubmission(
                    formSubmission.instanceId(),
                    formSubmission.entityId(),
                    formSubmission.formName(),
                    formSubmission.instance(),
                    formSubmission.clientVersion(),
                    SYNCED,
                    formSubmission.formDataDefinitionVersion(),
                    formSubmission.serverVersion()
            ));
        }
        return submissions;
    }
}
