package com.detroitlabs.community.activities;

import android.app.ActionBar;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.detroitlabs.community.R;
import com.detroitlabs.community.fragments.CreateProblemFragment_;
import com.detroitlabs.community.fragments.NavigationDrawerFragment;
import com.detroitlabs.community.managers.LocationManager;
import com.detroitlabs.community.managers.LocationManager.OnLocationReceivedListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.UiThread;

import java.util.Timer;
import java.util.TimerTask;

@EActivity(R.layout.activity_navigation)
public class NavigationActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnLocationReceivedListener {

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence title;

    @FragmentById(R.id.navigation_drawer)
    NavigationDrawerFragment drawerFragment;

    MapFragment mapFragment;

    @Bean LocationManager locationManager;

    private Timer setMapOptionsTimer;

    @AfterViews
    void afterViews() {
        title = getTitle();

        // Set up the drawer.
        drawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mapFragment = new MapFragment();
        changeFragment(mapFragment, true);
        setUpMap();
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
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    }

    @Override
    public void onReportProblemClicked() {
        changeFragment(new CreateProblemFragment_(), true);
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

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationReceived(LatLng location){
        locationManager.setOnLocationReceivedListener(null);
        mapFragment.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
    }

    private boolean isMapReady() {
        return mapFragment.getMap() != null;
    }

    private void configureMap() {
        GoogleMap map = mapFragment.getMap();
        map.setMyLocationEnabled(true);
        locationManager.setOnLocationReceivedListener(this);
        locationManager.onStart();
    }
}
