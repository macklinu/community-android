package com.detroitlabs.community.utils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;

import java.util.Timer;
import java.util.TimerTask;

public class MapTimer {
    private Timer mapTimer;

    public static interface OnMapReadyListener {
        void onMapReady(GoogleMap googleMap);
    }

    private MapView mapView;
    private MapFragment mapFragment;

    private OnMapReadyListener listener;

    public MapTimer(MapFragment mapFragment, OnMapReadyListener listener) {
        this.mapFragment = mapFragment;
        this.listener = listener;
    }

    public MapTimer(MapView mapView, OnMapReadyListener listener) {
        this.mapView = mapView;
        this.listener = listener;
    }

    public void setUpMap() {
        if (mapTimer != null) {
            destroyTimer();
        }

        if (isMapReady()) {
            if (listener != null) {
                listener.onMapReady(mapView != null ? mapView.getMap() :mapFragment.getMap());
                destroyTimer();
            }
        } else {
            mapTimer = new Timer();
            mapTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setUpMap();
                }
            }, 200);
        }
    }

    private void destroyTimer() {
        mapTimer.cancel();
        mapTimer.purge();
        mapTimer = null;
    }

    private boolean isMapReady() {
        if (mapView != null) {
            return mapView.getMap() != null;
        } else if (mapFragment != null) {
            return mapFragment.getMap() != null;
        }
        return false;
    }
}
