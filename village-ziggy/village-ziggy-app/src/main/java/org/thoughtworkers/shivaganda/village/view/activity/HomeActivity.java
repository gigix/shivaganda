package org.thoughtworkers.shivaganda.village.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import io.ona.ziggy.view.activity.WebFormActivity;
import org.thoughtworkers.shivaganda.village.R;

public class HomeActivity extends WebFormActivity {
    public HomeActivity() {
        Log.d("Shivaganda", "HomeActivity constructor");
    }

    @Override
    protected void onInitialization() {
        Log.d("Shivaganda", "start initialization");
        webView.loadUrl("file:///android_asset/shivaganda/index.html");
        Log.d("Shivaganda", "done initialization");
    }
}
