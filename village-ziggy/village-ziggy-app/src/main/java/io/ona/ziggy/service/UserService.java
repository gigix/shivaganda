package io.ona.ziggy.service;

import io.ona.ziggy.repository.AllSettings;
import io.ona.ziggy.util.Session;

public class UserService {
    private final AllSettings allSettings;
    private final Session session;

    public UserService(AllSettings allSettings, Session session) {
        this.allSettings = allSettings;
        this.session = session;
    }

    public void initUser(String username, String password) {
        session.setPassword(password);
        allSettings.registerReporter(username, password);
    }
}
