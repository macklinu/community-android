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
        setBasicAuth();
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
    public void getEventByProblemId(int id, RestCallback<Event> callback) {
        try {
            final ResponseObject<Event> response = restClient.getEventByProblemId(id);
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
            final User response = restClient.login(user).getResponse();
            appPrefs.setUser(response);
            setBasicAuth();
            callback.onSuccess(response);
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    @Background
    public void register(User user, RestCallback<User> callback) {
        try {
            final User response = restClient.register(user).getResponse();
            appPrefs.setUser(response);
            setBasicAuth();
            callback.onSuccess(response);
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    private void setBasicAuth() {
        final User user = appPrefs.getUser();
        if (user != null) {
            restClient.setHttpBasicAuth(user.getUsername(), user.getPassword());
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
