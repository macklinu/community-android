package com.detroitlabs.community.fragments;

import android.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.detroitlabs.community.R;
import com.detroitlabs.community.api.RestApi;
import com.detroitlabs.community.api.RestCallback;
import com.detroitlabs.community.model.Event;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.springframework.web.client.RestClientException;

@EFragment(R.layout.fragment_add_event)
public class AddEventFragment extends Fragment{

    @ViewById TimePicker timePicker;
    @ViewById EditText   description;
    @ViewById Button     submit;

    @Bean RestApi api;

    private int problemId = 0;

    public void setProblemId(int problemId){
        this.problemId = problemId;
    }

    @Click
    public void submit(){
        Event event = new Event(problemId, System.currentTimeMillis(), System.currentTimeMillis());
        event.setDescription(description.getText().toString());
        api.addEvent(event, new RestCallback<Event>(){
            @Override
            public void onSuccess(Event response){
                getFragmentManager().popBackStack();
            }

            @Override
            public void onFailure(RestClientException e){

            }
        });
    }
}
