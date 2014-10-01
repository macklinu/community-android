package com.detroitlabs.community.fragments;


import android.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.detroitlabs.community.R;
import com.detroitlabs.community.api.RestApi;
import com.detroitlabs.community.api.RestCallback;
import com.detroitlabs.community.model.Comment;
import com.detroitlabs.community.model.Event;
import com.detroitlabs.community.model.Problem;
import com.detroitlabs.community.prefs.AppPrefs;
import com.detroitlabs.community.utils.Dialogger;
import com.detroitlabs.community.utils.MapTimer;
import com.detroitlabs.community.utils.SnoopLogg;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;
import org.springframework.web.client.RestClientException;

import static android.view.View.GONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_SEND;
import static com.detroitlabs.community.utils.MapTimer.OnMapReadyListener;

@EFragment(R.layout.fragment_event_details)
public class EventDetailsFragment extends Fragment implements OnMapReadyListener, RestCallback<Comment> {

    private static final String DATE_PATTERN = "MMMM d h:mm";

    @SystemService
    InputMethodManager inputMethodManager;

    @Bean
    AppPrefs appPrefs;

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

    @ViewById
    EditText commentEntry;

    @FragmentArg
    Problem problem;

    @FragmentArg
    Event event;
    private ArrayAdapter<Comment> adapter;

    @AfterViews
    void afterViews() {
        new MapTimer(map, this);
        description.setText(event.getDescription());
        startTime.setText("Start time: " + new DateTime(event.getStartTime()).toString(DATE_PATTERN));
        endTime.setText("End time: " + new DateTime(event.getEndTime()).toString(DATE_PATTERN));
        adapter = new ArrayAdapter<Comment>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                event.getComments()
        );
        comments.setAdapter(adapter);
    }

    @EditorAction(R.id.commentEntry)
    boolean onCommentEntry(int actionId) {
        if (actionId == IME_ACTION_SEND) {
            inputMethodManager.hideSoftInputFromWindow(description.getWindowToken(), 0);
            final Comment comment = new Comment.Builder()
                    .message(commentEntry.getText().toString())
                    .id(appPrefs.getUser().getId())
                    .eventId(event.getId())
                    .time(System.currentTimeMillis())
                    .build();
            api.addComment(comment, this);
            return true;
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (problem == null) {
            map.setVisibility(GONE);
        } else {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Problem.latLngFrom(problem), 13));
        }
    }

    @Override
    @UiThread
    public void onSuccess(Comment comment) {
        commentEntry.setText(null);
        adapter.add(comment);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(RestClientException e) {
        SnoopLogg.e(e);
        Dialogger.showWebRequestErrorDialog(getActivity());
    }
}
