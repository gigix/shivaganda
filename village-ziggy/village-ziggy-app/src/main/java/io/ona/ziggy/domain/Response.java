package io.ona.ziggy.domain;

import static io.ona.ziggy.domain.ResponseStatus.failure;

public class Response<T> {
    private ResponseStatus status;
    private T payload;

    public Response(ResponseStatus status, T payload) {
        this.status = status;
        this.payload = payload;
    }

    public T payload() {
        return payload;
    }

    public ResponseStatus status() {
        return status;
    }

    public boolean isFailure() {
        return status.equals(failure);
    }
}
