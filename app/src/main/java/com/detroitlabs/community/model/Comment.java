package com.detroitlabs.community.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private int id;
    private int userId;
    private String message;
    private long time;
    private int eventId;

    private Comment(Builder builder) {
        id = builder.id;
        userId = builder.userId;
        message = builder.message;
        time = builder.time;
        eventId = builder.eventId;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    public int getEventId() {
        return eventId;
    }

    @Override
    public String toString() {
        return message == null ? "null" : message;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.userId);
        dest.writeString(this.message);
        dest.writeLong(this.time);
        dest.writeInt(this.eventId);
    }

    private Comment(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readInt();
        this.message = in.readString();
        this.time = in.readLong();
        this.eventId = in.readInt();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public static final class Builder {
        private int id;
        private int userId;
        private String message;
        private long time;
        private int eventId;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder time(long time) {
            this.time = time;
            return this;
        }

        public Builder eventId(int eventId) {
            this.eventId = eventId;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
