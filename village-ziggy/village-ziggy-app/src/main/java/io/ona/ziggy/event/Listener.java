package io.ona.ziggy.event;

public interface Listener<T> {
    void onEvent(T data);
}
