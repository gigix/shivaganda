package io.ona.ziggy.sync;

import io.ona.ziggy.domain.FetchStatus;

public interface AfterFetchListener {
    void afterFetch(FetchStatus fetchStatus);
}
