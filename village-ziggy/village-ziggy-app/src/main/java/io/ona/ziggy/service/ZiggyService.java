package io.ona.ziggy.service;

import io.ona.ziggy.repository.FormDataRepository;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

import java.util.Map;

import static org.thoughtworkers.shivaganda.village.AllConstants.*;
import static io.ona.ziggy.util.Log.logError;
import static io.ona.ziggy.util.Log.logInfo;
import static java.text.MessageFormat.format;
import static org.mozilla.javascript.Context.*;

public class ZiggyService {
    private static final String SAVE_METHOD_NAME = "save";
    private static final String JS_INIT_SCRIPT = "var formDataRepository = new enketo.FormDataRepository();\n" +
            "    var controller = new enketo.FormDataController(\n" +
            "        new enketo.EntityRelationshipLoader(),\n" +
            "        new enketo.FormDefinitionLoader(),\n" +
            "        new enketo.FormModelMapper(formDataRepository, new enketo.SQLQueryBuilder(formDataRepository), new enketo.IdFactory(new enketo.IdFactoryBridge())),\n" +
            "        formDataRepository, new enketo.FormSubmissionRouter());";

    private ZiggyFileLoader ziggyFileLoader;
    private FormDataRepository dataRepository;
    private FormSubmissionRouter formSubmissionRouter;
    private Context context;
    private ScriptableObject scope;
    private Function saveFunction;

    public ZiggyService(ZiggyFileLoader ziggyFileLoader, FormDataRepository dataRepository, FormSubmissionRouter formSubmissionRouter) {
        this.ziggyFileLoader = ziggyFileLoader;
        this.dataRepository = dataRepository;
        this.formSubmissionRouter = formSubmissionRouter;
        initRhino();
    }

    public void saveForm(String params, String formInstance) throws Exception {
        context = enter();
        saveFunction.call(context, scope, scope, new Object[]{params, formInstance});
        logInfo(format("Saving form successful, with params: {0}, with instance {1}.", params, formInstance));
        exit();
    }

    private void initRhino() {
        try {
            context = enter();
            context.setOptimizationLevel(-1);
            scope = context.initStandardObjects();
            String jsFiles = ziggyFileLoader.getJSFiles();
            scope.put(REPOSITORY, scope, toObject(dataRepository, scope));
            scope.put(ZIGGY_FILE_LOADER, scope, toObject(ziggyFileLoader, scope));
            scope.put(FORM_SUBMISSION_ROUTER, scope, toObject(formSubmissionRouter, scope));
            context.evaluateString(scope, jsFiles + JS_INIT_SCRIPT, "code", 1, null);
            saveFunction = ((Function) ((Map) scope.get("controller", scope)).get(SAVE_METHOD_NAME));
        } catch (Exception e) {
            logError("Rhino initialization failed. We are screwed. EOW!!!. Evil: " + e);
        } finally {
            exit();
        }
    }
}
