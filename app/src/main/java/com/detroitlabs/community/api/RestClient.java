package com.detroitlabs.community.api;

import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientHeaders;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Rest(rootUrl = "" /* TODO add root url */, converters = {GsonHttpMessageConverter.class})
public interface RestClient extends RestClientHeaders, RestClientSupport, RestClientRootUrl {
}
