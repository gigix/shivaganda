package org.thoughtworkers.shivaganda.village;

import android.content.Context;
import android.util.Log;
import io.ona.ziggy.repository.*;
import io.ona.ziggy.service.*;
import io.ona.ziggy.util.Session;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class ZiggyContext {
    private static ZiggyContext ziggyContext;

    private Context applicationContext;

    private ZiggyFileLoader ziggyFileLoader;
    private Session session;

    private FormSubmissionRouter formSubmissionRouter;
    private VillageRegistrationHandler villageRegistrationHandler;

    private Repository repository;
    private SettingsRepository settingsRepository;
    private VillageRepository villageRepository;
    private FormDataRepository formDataRepository;

    private AllVillages allVillages;
    private AllSettings allSettings;

    private FormSubmissionSyncService formSubmissionSyncService;
    private HTTPAgent httpAgent;
    private FormSubmissionService formSubmissionService;
    private ZiggyService ziggyService;
    private UserService userService;

    public static ZiggyContext getInstance() {
        if (ziggyContext == null) {
            ziggyContext = new ZiggyContext();
        }
        return ziggyContext;
    }

    public static void setInstance(ZiggyContext context) {
        ziggyContext = context;
    }

    private Repository initRepository() {
        if (repository == null) {
            repository = new Repository(this.applicationContext, session(),
                    settingsRepository(),
                    villageRepository(),
                    formDataRepository());
        }
        return repository;
    }

    private Session session() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    private SettingsRepository settingsRepository() {
        if (settingsRepository == null) {
            settingsRepository = new SettingsRepository();
        }
        return settingsRepository;
    }

    private VillageRepository villageRepository() {
        if (villageRepository == null) {
            villageRepository = new VillageRepository();
        }
        return villageRepository;
    }

    public FormDataRepository formDataRepository() {
        if (formDataRepository == null) {
            formDataRepository = new FormDataRepository();
        }
        return formDataRepository;
    }

    public ZiggyContext updateApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
        return this;
    }

    public ZiggyFileLoader ziggyFileLoader() {
        Log.d("ZIGGY", "this should be good");
        initRepository();
        Log.d("ZIGGY", "this should be bad");
        if (ziggyFileLoader == null) {
            ziggyFileLoader = new ZiggyFileLoader("www/io.ona.ziggy", "www/form", applicationContext.getAssets());
        }
        return ziggyFileLoader;
    }

    public FormSubmissionRouter formSubmissionRouter() {
        initRepository();
        if (formSubmissionRouter == null) {
            formSubmissionRouter = new FormSubmissionRouter(formDataRepository(), villageRegistrationHandler());
        }
        return formSubmissionRouter;
    }

    private VillageRegistrationHandler villageRegistrationHandler() {
        if (villageRegistrationHandler == null) {
            villageRegistrationHandler = new VillageRegistrationHandler();
        }
        return villageRegistrationHandler;
    }

    public AllVillages allVillages() {
        initRepository();
        if (allVillages == null) {
            allVillages = new AllVillages(villageRepository());
        }
        return allVillages;
    }

    private AllSettings allSettings() {
        initRepository();
        if (allSettings == null) {
            allSettings = new AllSettings(getDefaultSharedPreferences(this.applicationContext), settingsRepository());
        }
        return allSettings;
    }

    public FormSubmissionSyncService formSubmissionSyncService() {
        initRepository();
        if (formSubmissionSyncService == null) {
            formSubmissionSyncService = new FormSubmissionSyncService(
                    formSubmissionService(), httpAgent(), formDataRepository(), allSettings());
        }
        return formSubmissionSyncService;
    }

    private FormSubmissionService formSubmissionService() {
        initRepository();
        if (formSubmissionService == null) {
            formSubmissionService = new FormSubmissionService(ziggyService(), formDataRepository(), allSettings());
        }
        return formSubmissionService;
    }

    private ZiggyService ziggyService() {
        initRepository();
        if (ziggyService == null) {
            ziggyService = new ZiggyService(ziggyFileLoader(), formDataRepository(), formSubmissionRouter());
        }
        return ziggyService;
    }

    private HTTPAgent httpAgent() {
        initRepository();
        if (httpAgent == null) {
            httpAgent = new HTTPAgent(allSettings());
        }
        return httpAgent;
    }

    public UserService userService() {
        initRepository();
        if (userService == null) {
            userService = new UserService(allSettings(), session());
        }
        return userService;
    }
}
