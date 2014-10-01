package com.detroitlabs.community.managers;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import static com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import static com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;
import static org.androidannotations.annotations.EBean.Scope.Singleton;

@EBean(scope = Singleton)
public class LocationManager implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    public static final long REQUEST_INTERVAL = 5000L;
    public static final long REQUEST_FASTEST_INTERVAL = 1000L;
    @RootContext
    Context context;

    private LocationClient locationClient;
    private LocationRequest locationRequest;
    private Location location;

    @AfterInject
    void afterInject() {
        locationClient = new LocationClient(context, this, this);
        setUpLocationRequest();
    }

    public void onStart() {
        locationClient.connect();
    }

    public void onStop() {
        if (locationClient.isConnected()) {
            locationClient.removeLocationUpdates(this);
        }
        locationClient.disconnect();
    }

    /**
     * @return last reported location, or null if location hasn't been reported
     */
    public Location getLocation() {
        return location;
    }

    @Override
    public void onConnected(Bundle bundle) {
        locationClient.requestLocationUpdates(locationRequest, this);
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    private void setUpLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(REQUEST_INTERVAL);
        locationRequest.setFastestInterval(REQUEST_FASTEST_INTERVAL);
    }
}
