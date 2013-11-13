package io.ona.ziggy.view.activity;

import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.ona.ziggy.view.controller.FormController;
import org.thoughtworkers.shivaganda.village.view.activity.FormActivity;

import java.util.Map;

import static org.thoughtworkers.shivaganda.village.AllConstants.*;

public abstract class WebFormActivity extends WebActivity {
    @Override
    protected void onCreation() {
        webView.addJavascriptInterface(new FormController(this), "formContext");

        onInitialization();
    }

    public void startFormActivity(String formName, String entityId, String metadata) {
        Log.d("ZIGGY", "satrtFormActivity:" + formName);
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra(FORM_NAME_PARAM, formName);
        intent.putExtra(ENTITY_ID_PARAM, entityId);
        Log.d("ZIGGY", "satrtFormActivity - intent ready : " + formName);
        addFieldOverridesIfExist(intent, metadata);
        Log.d("ZIGGY", "ready to start Activity");
        startActivity(intent);
    }

    private void addFieldOverridesIfExist(Intent intent, String metadata) {
        Map<String, String> metadataMap = new Gson().fromJson(metadata, new TypeToken<Map<String, String>>() {
        }.getType());
        if (metadataMap.containsKey(FIELD_OVERRIDES_PARAM)) {
            intent.putExtra(FIELD_OVERRIDES_PARAM, metadataMap.get(FIELD_OVERRIDES_PARAM));
        }
    }

    protected abstract void onInitialization();
}
