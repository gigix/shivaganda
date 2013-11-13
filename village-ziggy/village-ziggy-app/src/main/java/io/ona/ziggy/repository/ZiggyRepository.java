package io.ona.ziggy.repository;

import android.database.sqlite.SQLiteDatabase;

public abstract class ZiggyRepository {
    protected Repository masterRepository;

    public void updateMasterRepository(Repository repository) {
        this.masterRepository = repository;
    }

    abstract protected void onCreate(SQLiteDatabase database);
}
