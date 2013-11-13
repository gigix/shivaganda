package io.ona.ziggy.view.activity;

import io.ona.ziggy.event.Listener;
import io.ona.ziggy.view.controller.VillageListController;

import static io.ona.ziggy.event.Event.FORM_SUBMITTED;

public class VillageListActivity extends WebFormActivity {
    private Listener<String> formSubmittedListener;

    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new VillageListController(context.allVillages()), "context");
        webView.loadUrl("file:///android_asset/www/village_list.html");
        formSubmittedListener = new Listener<String>() {
            @Override
            public void onEvent(String data) {
                webView.loadUrl("javascript:if(window.pageView) {window.pageView.reload();}");
            }
        };
        FORM_SUBMITTED.addListener(formSubmittedListener);
    }
}
