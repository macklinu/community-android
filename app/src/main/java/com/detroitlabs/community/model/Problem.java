package com.detroitlabs.community.model;

public class Problem {
    private int id;
    private double lat;
    private double lng;
    private String description;
    private String imageUrl;
    private String userId;

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserId() {
        return userId;
    }
}
