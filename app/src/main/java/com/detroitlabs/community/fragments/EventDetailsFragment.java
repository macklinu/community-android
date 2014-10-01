package com.detroitlabs.community.fragments;


import android.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.detroitlabs.community.R;
import com.detroitlabs.community.api.RestApi;
import com.detroitlabs.community.api.RestCallback;
import com.detroitlabs.community.model.Comment;
import com.detroitlabs.community.model.Event;
import com.detroitlabs.community.model.Problem;
import com.detroitlabs.community.utils.MapTimer;
import com.detroitlabs.community.utils.SnoopLogg;
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

import java.util.List;

import static com.detroitlabs.community.utils.Dialogger.showWebRequestErrorDialog;

@EFragment(R.layout.fragment_event_details)
public class EventDetailsFragment extends Fragment implements RestCallback<List<Event>>, MapTimer.OnMapReadyListener {

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

    @AfterViews
    void afterViews() {
        api.getEventsByProblemId(problem.getId(), this);
    }

    @Override
    @UiThread
    public void onSuccess(List<Event> response) {
        final Event event = response.get(0);
        new MapTimer(map, this);
        description.setText(event.getDescription());
        startTime.setText(new DateTime(event.getStartTime()).toString("mm/dd/yyyy"));
        endTime.setText(new DateTime(event.getEndTime()).toString("mm/dd/yyyy"));
        comments.setAdapter(new ArrayAdapter<Comment>(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        android.R.id.text1,
                        event.getComments())
        );
    }

    @Override
    public void onFailure(Exception e) {
        SnoopLogg.e(e);
        showWebRequestErrorDialog(getActivity());
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Problem.latLngFrom(problem), 13));
    }
}
