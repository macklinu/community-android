package com.detroitlabs.community.managers;

import com.detroitlabs.community.model.Problem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkerMaker{

    public interface OnProblemClickedListener{
        public void onProblemClicked(Problem problem);
    }

    private GoogleMap     map;
    private List<Problem> problems;
    private OnProblemClickedListener onProblemClickedListener;

    private Map<Marker, Problem> markerProblems = new HashMap<Marker, Problem>();

    public MarkerMaker(GoogleMap map, List<Problem> problems, OnProblemClickedListener onProblemClickedListener){
        this.map = map;
        this.problems = problems;
        this.onProblemClickedListener = onProblemClickedListener;
    }

    public void populate(){
        for (Problem p : problems){
            LatLng pos = new LatLng(p.getLat(), p.getLng());
            MarkerOptions options = markerForLocation(p, pos);
            Marker m = map.addMarker(options);
            markerProblems.put(m, p);
        }
        map.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
            @Override
            public void onInfoWindowClick(Marker marker){
                Problem clicked = markerProblems.get(marker);
                onProblemClickedListener.onProblemClicked(clicked);
            }
        });
    }

    private MarkerOptions markerForLocation(Problem p, LatLng location){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
        markerOptions.title(p.getDescription());
        return markerOptions;
    }
}
