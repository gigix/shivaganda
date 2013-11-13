package io.ona.ziggy.repository;

import io.ona.ziggy.domain.Village;

import java.util.List;

public class AllVillages {
    private VillageRepository repository;

    public AllVillages(VillageRepository repository) {
        this.repository = repository;
    }

    public List<Village> getVillages() {
        return repository.allVillages();
    }
}
