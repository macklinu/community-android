package com.detroitlabs.community.managers;

import com.detroitlabs.community.model.Problem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MarkerMaker{

    private GoogleMap map;
    private List<Problem> problems;

    public MarkerMaker(GoogleMap map, List<Problem> problems){
        this.map = map;
        this.problems = problems;
    }

    public void populate(){
        for (Problem p : problems){
            LatLng pos = new LatLng(p.getLat(), p.getLng());
            map.addMarker(markerForLocation(p, pos));
        }
    }

    private MarkerOptions markerForLocation(Problem p, LatLng location){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        return markerOptions;
    }
}
