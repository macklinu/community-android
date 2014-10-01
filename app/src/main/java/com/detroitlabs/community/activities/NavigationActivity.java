package com.detroitlabs.community.activities;

import android.app.ActionBar;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.detroitlabs.community.R;
import com.detroitlabs.community.adapters.FragmentClassEntry;
import com.detroitlabs.community.api.RestApi;
import com.detroitlabs.community.api.RestCallback;
import com.detroitlabs.community.fragments.CreateProblemFragment_;
import com.detroitlabs.community.fragments.NavigationDrawerFragment;
import com.detroitlabs.community.fragments.ProblemFragment;
import com.detroitlabs.community.fragments.ProblemFragment_;
import com.detroitlabs.community.fragments.ProblemListFragment;
import com.detroitlabs.community.fragments.ProblemListFragment_;
import com.detroitlabs.community.managers.LocationManager;
import com.detroitlabs.community.managers.LocationManager.OnLocationReceivedListener;
import com.detroitlabs.community.managers.MarkerMaker;
import com.detroitlabs.community.managers.MarkerMaker.OnProblemClickedListener;
import com.detroitlabs.community.model.Problem;
import com.detroitlabs.community.prefs.AppPrefs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.detroitlabs.community.fragments.CreateProblemFragment.CreateProblemFragmentCallbacks;
import static com.detroitlabs.community.fragments.NavigationDrawerFragment.NavigationDrawerCallbacks;

@EActivity(R.layout.activity_navigation)
public class NavigationActivity extends BaseActivity implements
        NavigationDrawerCallbacks,
        OnLocationReceivedListener,
        CreateProblemFragmentCallbacks{

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence title;

    @Bean
    AppPrefs appPrefs;

    @FragmentById(R.id.navigation_drawer)
    NavigationDrawerFragment drawerFragment;

    MapFragment mapFragment;
    ProblemListFragment problemListFragment;

    @Bean LocationManager locationManager;

    private Timer setMapOptionsTimer;
    @Bean RestApi api;

    private ArrayList<Problem> problems = new ArrayList<Problem>();
    private LatLng location = new LatLng(0, 0);

    @AfterViews
    void afterViews() {
        title ="Welcome " + appPrefs.getUser().getName() + "!";
        // Set up the drawer.
        drawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        locationManager.setOnLocationReceivedListener(this);
        locationManager.onStart();
    }

    /**
     * method pulled from previous app
     */
    @UiThread
    void setUpMap() {
        if (setMapOptionsTimer != null) {
            setMapOptionsTimer.cancel();
            setMapOptionsTimer.purge();
            setMapOptionsTimer = null;
        }

        if (isMapReady()) {
            configureMap();
        } else {
            setMapOptionsTimer = new Timer();
            setMapOptionsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setUpMap();
                }
            }, 200);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(FragmentClassEntry fragmentClassEntry) {

        if (fragmentClassEntry.getFragment().getName().equals(MapFragment.class.getName())) {
            mapFragment = new MapFragment();
            changeFragment(mapFragment, false);
            setUpMap();
        } else if (fragmentClassEntry.getFragment().getName().equals(ProblemListFragment.class.getName())) {
            problemListFragment = ProblemListFragment_.builder().problems(problems).build();
            changeFragment(problemListFragment, false);
            mapFragment = null;
        }

    }

    @Override
    public void onReportProblemClicked() {
        changeFragment(new CreateProblemFragment_(), true);
    }

    @Override
    public void onProblemSuccessfullyCreated() {
        changeFragment(mapFragment, false);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.navigation, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @OptionsItem(R.id.action_log_out)
    void onLogOutClick() {
        appPrefs.clearUser();
        RegistrationActivity_
                .intent(this)
                .start();
        finish();
    }

    @Override
    public void onLocationReceived(LatLng location){
        this.location = location;
        locationManager.setOnLocationReceivedListener(null);
        updateMapCamera(location);
        api.getProblemsByLocation(location.latitude, location.longitude, problemsCallback);
    }

    private void updateMapCamera(LatLng location) {
        if (hasMap()) {
            mapFragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
        }
    }

    private boolean isMapReady() {
        return hasMap() && mapFragment.getMap() != null;
    }

    private void configureMap() {
        if (hasMap()) {
            GoogleMap map = mapFragment.getMap();
            map.setMyLocationEnabled(true);
            updateMapCamera(this.location);
            populateMap();
        }
    }

    private boolean hasMap() {
        return mapFragment != null;
    }

    @UiThread
    public void populateMap(){
        MarkerMaker markerMaker = new MarkerMaker(mapFragment.getMap(), problems, new OnProblemClickedListener(){
            @Override
            public void onProblemClicked(Problem problem){
                ProblemFragment pf = new ProblemFragment_();
                pf.setProblem(problem);
                changeFragment(pf,true);
            }
        });
        markerMaker.populate();
    }

    RestCallback<List<Problem>> problemsCallback = new RestCallback<List<Problem>>(){
        @Override
        public void onSuccess(List<Problem> response){
            problems = new ArrayList<Problem>(response);
            if (hasMap()) {
                populateMap();
            } else {
                problemListFragment.updateProblemList(response);
            }
        }

        @Override
        public void onFailure(RestClientException e){
        }
    };
}
