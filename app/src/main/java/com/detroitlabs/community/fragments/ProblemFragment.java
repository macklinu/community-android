package com.detroitlabs.community.fragments;

import android.app.Fragment;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.detroitlabs.community.R;
import com.detroitlabs.community.adapters.EventAdapter;
import com.detroitlabs.community.api.RestApi;
import com.detroitlabs.community.api.RestCallback;
import com.detroitlabs.community.model.Event;
import com.detroitlabs.community.model.Problem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_problem)
public class ProblemFragment extends Fragment{

    @ViewById TextView  description;
    @ViewById ListView  eventsList;
    @ViewById ImageView problemPhoto;
    @Bean RestApi api;

    private Problem problem;
    private EventAdapter adapter;

    public void setProblem(final Problem problem){
        this.problem = problem;
    }

    @AfterViews
    public void onAfterViews(){
        //Picasso.with(getActivity()).load(problem.getImageUrl()).into(problemPhoto);
        description.setText(problem.getDescription());
        adapter = new EventAdapter(getActivity(), new ArrayList<Event>());
        eventsList.setAdapter(adapter);
        api.getEventsByProblemId(problem.getId(), eventsCallback);
    }

    @UiThread
    public void updateEvents(List<Event> response){
        adapter.addAll(response);
        adapter.notifyDataSetChanged();
    }

    RestCallback<List<Event>> eventsCallback = new RestCallback<List<Event>>(){
        @Override
        public void onSuccess(List<Event> response){
            updateEvents(response);
        }

        @Override
        public void onFailure(RestClientException e){

        }
    };
}
