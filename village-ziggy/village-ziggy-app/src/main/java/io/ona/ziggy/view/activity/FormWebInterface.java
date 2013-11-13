package io.ona.ziggy.view.activity;

import android.app.Activity;

import static io.ona.ziggy.util.Log.logInfo;

public class FormWebInterface {
    private final String model;
    private final String form;
    private Activity activity;

    public FormWebInterface(String model, String form, Activity activity) {
        this.model = model;
        this.form = form;
        this.activity = activity;
    }

    public String getModel() {
        return model;
    }

    public String getForm() {
        return form;
    }

    public void goBack() {
        activity.finish();
    }

    public void log(String message) {
        logInfo(message);
    }
}
