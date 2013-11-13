package io.ona.ziggy.view.controller;

import io.ona.ziggy.view.activity.WebFormActivity;

public class FormController {
    private WebFormActivity activity;

    public FormController(WebFormActivity activity) {
        this.activity = activity;
    }

    public void startFormActivity(String formName, String entityId, String metadata) {
        activity.startFormActivity(formName, entityId, metadata);
    }
}
