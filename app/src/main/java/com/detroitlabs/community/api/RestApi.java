package com.detroitlabs.community.api;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

@EBean(scope = Singleton)
public class RestApi {

    @RestService
    RestClient restClient;
}
