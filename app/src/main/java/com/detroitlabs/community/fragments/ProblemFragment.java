package com.detroitlabs.community.fragments;

import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.detroitlabs.community.R;
import com.detroitlabs.community.activities.BaseActivity;
import com.detroitlabs.community.adapters.EventAdapter;
import com.detroitlabs.community.api.RestApi;
import com.detroitlabs.community.api.RestCallback;
import com.detroitlabs.community.model.Event;
import com.detroitlabs.community.model.Problem;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
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
    @ViewById Button addEvent;
    @Bean RestApi api;

    private Problem problem;
    private EventAdapter adapter;

    public void setProblem(final Problem problem){
        this.problem = problem;
    }

    @AfterViews
    public void onAfterViews(){
        //if (problem.getImageUrl() != null && problem.getImageUrl().length() > 0)
        Picasso.with(getActivity()).load("http://media.mlive.com/newsnow_impact/photo/-9c8f42d0fb37339f.jpg").into(problemPhoto);
        description.setText(problem.getDescription());
        adapter = new EventAdapter(getActivity(), new ArrayList<Event>());
        eventsList.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                ((BaseActivity)getActivity()).changeFragment(EventDetailsFragment_.builder().problem(problem).event((Event)parent.getAdapter().getItem(position)).build(), true);
            }
        });
        eventsList.setAdapter(adapter);
        api.getEventsByProblemId(problem.getId(), eventsCallback);
    }

    @UiThread
    public void updateEvents(List<Event> response){
        adapter.addAll(response);
        adapter.notifyDataSetChanged();
    }

    @Click
    public void addEvent(){
        AddEventFragment f = new AddEventFragment_();
        f.setProblemId(problem.getId());
        ((BaseActivity)getActivity()).changeFragment(f, true);
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
