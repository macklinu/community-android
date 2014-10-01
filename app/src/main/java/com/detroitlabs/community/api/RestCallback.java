package com.detroitlabs.community.api;

public interface RestCallback<T> {
    void onSuccess(T response);
    void onFailure(Exception e);
}
