package io.ona.ziggy.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import io.ona.ziggy.util.Session;

import java.io.File;

public class Repository extends SQLiteOpenHelper {
    private ZiggyRepository[] repositories;
    private Context context;
    private String dbName;

    public Repository(Context context, Session session, ZiggyRepository... repositories) {
        super(context, session.repositoryName(), null, 1);
        this.repositories = repositories;
        context.getDatabasePath(session.repositoryName());
        this.context = context;
        this.dbName = session.repositoryName();

        for (ZiggyRepository repository : repositories) {
            repository.updateMasterRepository(this);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        for (ZiggyRepository repository : repositories) {
            repository.onCreate(database);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

}
