package com.detroitlabs.community.api;

import com.detroitlabs.community.model.Comment;
import com.detroitlabs.community.model.Event;
import com.detroitlabs.community.model.Problem;
import com.detroitlabs.community.model.User;
import com.detroitlabs.community.prefs.AppPrefs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
import static org.androidannotations.annotations.EBean.Scope.Singleton;

@EBean(scope = Singleton)
public class RestApi {

    @Bean
    AppPrefs appPrefs;

    @RestService
    RestClient restClient;

    @AfterInject
    void afterInject() {
        // restClient.setHttpBasicAuth(appPrefs.getUsername(), appPrefs.getPassword());
        updateGsonConverter();

    }

    @Background
    public void addComment(Comment comment, RestCallback<Comment> callback) {
        try {
            final ResponseObject<Comment> response = restClient.addComment(comment);
            callback.onSuccess(response.getResponse());
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    @Background
    public void addEvent(Event event, RestCallback<Event> callback) {
        try {
            final ResponseObject<Event> response = restClient.addEvent(event);
            callback.onSuccess(response.getResponse());
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    @Background
    public void addProblem(Problem problem, RestCallback<Problem> callback) {
        try {
            final ResponseObject<Problem> response = restClient.addProblem(problem);
            callback.onSuccess(response.getResponse());
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    @Background
    public void getProblemsByLocation(double lat, double lng, RestCallback<List<Problem>> callback) {
        try {
            final ResponseObject<List<Problem>> response = restClient.getProblemsByLocation(lat, lng);
            callback.onSuccess(response.getResponse());
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    @Background
    public void deleteProblem(int id, RestCallback<Void> callback) {
        try {
            restClient.deleteProblem(id);
            callback.onSuccess(null);
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    @Background
    public void login(User user, RestCallback<User> callback) {
        try {
            final ResponseObject<User> response = restClient.login(user);
            callback.onSuccess(response.getResponse());
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    @Background
    public void register(User user, RestCallback<User> callback) {
        try {
            final ResponseObject<User> response = restClient.register(user);
            restClient.setHttpBasicAuth(user.getUsername(), user.getPassword());
            callback.onSuccess(response.getResponse());
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    private void updateGsonConverter() {
        final RestTemplate restTemplate = restClient.getRestTemplate();
        final GsonHttpMessageConverter gsonConverter =
                (GsonHttpMessageConverter) restTemplate.getMessageConverters().get(0);
        gsonConverter.setGson(gson());
        restClient.setRestTemplate(restTemplate);
    }

    private Gson gson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
