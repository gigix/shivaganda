package io.ona.ziggy.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.ona.ziggy.domain.Village;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VillageRepository extends ZiggyRepository {
    private static final String VILLAGE_SQL = "CREATE TABLE village(id VARCHAR PRIMARY KEY, name VARCHAR, code VARCHAR, details VARCHAR)";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String CODE_COLUMN = "code";
    private static final String DETAILS_COLUMN = "details";
    public static final String VILLAGE_TABLE_NAME = "village";
    public static final String[] VILLAGE_TABLE_COLUMNS = new String[]{"id", "name", "code", "details"};

    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(VILLAGE_SQL);
    }

    public void add(Village village) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(VILLAGE_TABLE_NAME, null, creteValuesFor(village));
    }

    public Village findByEntityID(String entityId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(VILLAGE_TABLE_NAME, VILLAGE_TABLE_COLUMNS, ID_COLUMN + " = ?", new String[]{entityId}, null, null, null);
        return readAll(cursor).get(0);
    }

    public List<Village> allVillages() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(VILLAGE_TABLE_NAME, VILLAGE_TABLE_COLUMNS, null, null, null, null, null);
        return readAll(cursor);
    }

    private ContentValues creteValuesFor(Village village) {
        ContentValues values = new ContentValues();
        values.put(ID_COLUMN, village.entityId());
        values.put(NAME_COLUMN, village.name());
        values.put(CODE_COLUMN, village.code());
        values.put(DETAILS_COLUMN, new Gson().toJson(village.details()));
        return values;
    }

    private List<Village> readAll(Cursor cursor) {
        cursor.moveToFirst();
        List<Village> villages = new ArrayList<Village>();
        while (!cursor.isAfterLast()) {
            Map<String, String> details = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(DETAILS_COLUMN)), new TypeToken<Map<String, String>>() {
            }.getType());

            villages.add(new Village(cursor.getString(cursor.getColumnIndex(ID_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(NAME_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(CODE_COLUMN)),
                    details));
            cursor.moveToNext();
        }
        cursor.close();
        return villages;
    }
}
