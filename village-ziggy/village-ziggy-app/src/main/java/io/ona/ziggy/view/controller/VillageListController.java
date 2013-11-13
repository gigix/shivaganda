package io.ona.ziggy.view.controller;

import com.google.gson.Gson;
import io.ona.ziggy.repository.AllVillages;

public class VillageListController {
    private AllVillages allVillages;

    public VillageListController(AllVillages allVillages) {
        this.allVillages = allVillages;
    }

    public String get() {
        return new Gson().toJson(allVillages.getVillages());
    }
}
