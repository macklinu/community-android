package com.detroitlabs.community.fragments;


import android.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.detroitlabs.community.R;
import com.detroitlabs.community.api.RestApi;
import com.detroitlabs.community.model.Event;
import com.detroitlabs.community.model.Problem;
import com.detroitlabs.community.utils.MapTimer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;

import static android.view.View.GONE;
import static com.detroitlabs.community.utils.MapTimer.OnMapReadyListener;

@EFragment(R.layout.fragment_event_details)
public class EventDetailsFragment extends Fragment implements OnMapReadyListener {

    @Bean
    RestApi api;

    @ViewById
    MapView map;

    @ViewById
    TextView description;

    @ViewById
    TextView startTime;

    @ViewById
    TextView endTime;

    @ViewById
    ListView comments;

    @FragmentArg
    Problem problem;

    @FragmentArg
    Event event;

    @AfterViews
    void afterViews() {
        //api.getEventsByProblemId(problem.getId(), this);
    }
    
    @UiThread
    public void onSuccess(Event event) {
        new MapTimer(map, this);
        description.setText(event.getDescription());
        startTime.setText(new DateTime(event.getStartTime()).toString("mm/dd/yyyy"));
        endTime.setText(new DateTime(event.getEndTime()).toString("mm/dd/yyyy"));
        comments.setAdapter(new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        android.R.id.text1,
                        event.getComments())
        );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (problem == null) {
            map.setVisibility(GONE);
        } else {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Problem.latLngFrom(problem), 13));
        }
    }
}
