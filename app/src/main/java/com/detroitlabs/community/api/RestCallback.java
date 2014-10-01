package com.detroitlabs.community.api;

import org.springframework.web.client.RestClientException;

public interface RestCallback<T> {
    void onSuccess(T response);
    void onFailure(RestClientException e);
}
