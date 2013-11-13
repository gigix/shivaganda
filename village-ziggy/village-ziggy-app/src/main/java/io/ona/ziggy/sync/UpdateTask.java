package io.ona.ziggy.sync;

import android.content.Context;
import android.widget.Toast;
import io.ona.ziggy.domain.FetchStatus;
import io.ona.ziggy.service.FormSubmissionSyncService;

import static io.ona.ziggy.domain.FetchStatus.nothingFetched;

public class UpdateTask {
    private final LockingBackgroundTask task;
    private Context context;
    private FormSubmissionSyncService formSubmissionSyncService;

    public UpdateTask(Context context, FormSubmissionSyncService formSubmissionSyncService, ProgressIndicator progressIndicator) {
        this.context = context;
        this.formSubmissionSyncService = formSubmissionSyncService;
        task = new LockingBackgroundTask(progressIndicator);
    }

    public void updateFromServer(final AfterFetchListener afterFetchListener) {
        task.doActionInBackground(new BackgroundAction<FetchStatus>() {
            public FetchStatus actionToDoInBackgroundThread() {
                FetchStatus fetchStatus = formSubmissionSyncService.sync();
                return fetchStatus;
            }

            public void postExecuteInUIThread(FetchStatus result) {
                if (result != null && context != null && result != nothingFetched) {
                    Toast.makeText(context, result.displayValue(), Toast.LENGTH_SHORT).show();
                }
                afterFetchListener.afterFetch(result);
            }
        });
    }
}
