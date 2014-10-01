package com.detroitlabs.community.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Problem implements Parcelable {
    private int id;
    private double lat;
    private double lng;
    private String description;
    private String imageUrl;
    private String userId;
    private double distance;

    private Problem(Builder builder) {
        id = builder.id;
        lat = builder.lat;
        lng = builder.lng;
        description = builder.description;
        imageUrl = builder.imageUrl;
        userId = builder.userId;
    }

    public static LatLng latLngFrom(Problem problem) {
        return new LatLng(problem.getLat(), problem.getLng());
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

    public double getDistance() {
        return distance;
    }

    public static final class Builder {
        private int id;
        private double lat;
        private double lng;
        private String description;
        private String imageUrl;
        private String userId;

        public Builder() {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeString(this.description);
        dest.writeString(this.imageUrl);
        dest.writeString(this.userId);
        dest.writeDouble(this.distance);
    }

    private Problem(Parcel in) {
        this.id = in.readInt();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.userId = in.readString();
        this.distance = in.readDouble();
    }

    public static final Parcelable.Creator<Problem> CREATOR = new Parcelable.Creator<Problem>() {
        public Problem createFromParcel(Parcel source) {
            return new Problem(source);
        }

        public Problem[] newArray(int size) {
            return new Problem[size];
        }
    };
}
