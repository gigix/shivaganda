package io.ona.ziggy.view.controller;

import android.app.Activity;
import android.content.Intent;
import io.ona.ziggy.view.activity.VillageListActivity;

public class NavigationController {
    private Activity activity;

    public NavigationController(Activity activity) {
        this.activity = activity;
    }

    public void startVillageListActivity() {
        Intent intent = new Intent(activity, VillageListActivity.class);
        activity.startActivity(intent);
    }
}
