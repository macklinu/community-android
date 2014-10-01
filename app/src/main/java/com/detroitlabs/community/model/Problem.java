package com.detroitlabs.community.model;

public class Problem {
    private int id;
    private double lat;
    private double lng;
    private String description;
    private String imageUrl;
    private String userId;

    private Problem(Builder builder) {
        id = builder.id;
        lat = builder.lat;
        lng = builder.lng;
        description = builder.description;
        imageUrl = builder.imageUrl;
        userId = builder.userId;
    }

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

    public static final class Builder {
        private int id;
        private double lat;
        private double lng;
        private String description;
        private String imageUrl;
        private String userId;

        public Builder(int id) {
            this.id = id;
        }

        public Builder lat(double lat) {
            this.lat = lat;
            return this;
        }

        public Builder lng(double lng) {
            this.lng = lng;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Problem build() {
            return new Problem(this);
        }
    }
}
