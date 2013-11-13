package io.ona.ziggy.repository;

import android.content.SharedPreferences;

import static org.thoughtworkers.shivaganda.village.AllConstants.IS_SYNC_IN_PROGRESS_PREFERENCE_KEY;

public class AllSettings {
    public static final String PREVIOUS_FETCH_INDEX_SETTING_KEY = "previousFetchIndex";
    public static final String PREVIOUS_FORM_SYNC_INDEX_SETTING_KEY = "previousFormSyncIndex";
    public static final String REPORTER_IDENTIFIER_PREFERENCE_KEY = "reporterIdentifier";
    private static final String REPORTER_PASSWORD_PREFERENCE_KEY = "reporterPassword";
    private SharedPreferences preferences;
    private SettingsRepository settingsRepository;

    public AllSettings(SharedPreferences preferences, SettingsRepository settingsRepository) {
        this.preferences = preferences;
        this.settingsRepository = settingsRepository;
    }

    public void registerReporter(String userName, String password) {
        preferences.edit().putString(REPORTER_IDENTIFIER_PREFERENCE_KEY, userName).commit();
        settingsRepository.updateSetting(REPORTER_PASSWORD_PREFERENCE_KEY, password);
    }

    public String fetchRegisteredReporter() {
        return preferences.getString(REPORTER_IDENTIFIER_PREFERENCE_KEY, "").trim();
    }

    public void savePreviousFetchIndex(String value) {
        settingsRepository.updateSetting(PREVIOUS_FETCH_INDEX_SETTING_KEY, value);
    }

    public String fetchPreviousFetchIndex() {
        return settingsRepository.querySetting(PREVIOUS_FETCH_INDEX_SETTING_KEY, "0");
    }

    public String fetchReporterPassword() {
        return settingsRepository.querySetting(REPORTER_PASSWORD_PREFERENCE_KEY, "");
    }

    public Boolean fetchIsSyncInProgress() {
        return preferences.getBoolean(IS_SYNC_IN_PROGRESS_PREFERENCE_KEY, false);
    }

    public void saveIsSyncInProgress(Boolean isSyncInProgress) {
        preferences.edit().putBoolean(IS_SYNC_IN_PROGRESS_PREFERENCE_KEY, isSyncInProgress).commit();
    }

    public String fetchPreviousFormSyncIndex() {
        return settingsRepository.querySetting(PREVIOUS_FORM_SYNC_INDEX_SETTING_KEY, "0");
    }

    public void savePreviousFormSyncIndex(String value) {
        settingsRepository.updateSetting(PREVIOUS_FORM_SYNC_INDEX_SETTING_KEY, value);
    }
}
