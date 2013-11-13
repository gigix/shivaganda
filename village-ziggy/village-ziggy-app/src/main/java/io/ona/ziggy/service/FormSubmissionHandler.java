package io.ona.ziggy.service;

import io.ona.ziggy.domain.form.FormSubmission;

public interface FormSubmissionHandler {
    public void handle(FormSubmission submission);
}
