package com.detroitlabs.community.api;

import com.detroitlabs.community.model.Comment;
import com.detroitlabs.community.model.Event;
import com.detroitlabs.community.model.Problem;
import com.detroitlabs.community.model.User;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientHeaders;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.List;

import static org.androidannotations.api.rest.MediaType.APPLICATION_JSON;

@Rest(rootUrl = "http://community-backend.herokuapp.com", converters = {GsonHttpMessageConverter.class})
@Accept(APPLICATION_JSON)
public interface RestClient extends RestClientHeaders, RestClientSupport, RestClientRootUrl {

    @Post("/comments")
    ResponseObject<Comment> addComment(Comment comment);

    @Post("/events")
    ResponseObject<Event> addEvent(Event event);

    @Post("/problems")
    ResponseObject<Problem> addProblem(Problem problem);

    @Get("/problems?lat={lat}&lng={lng}")
    ResponseObject<List<Problem>> getProblemsByLocation(double lat, double lng);

    @Get("/events/{id}")
    ResponseObject<Event> getEventByProblemId(int id);

    @Delete("/problems/{id}")
    void deleteProblem(int id);

    @Post("/login")
    ResponseObject<User> login(User user);

    @Post("/register")
    ResponseObject<User> register(User user);
}
