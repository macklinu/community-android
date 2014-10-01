package com.detroitlabs.community.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private int id;
    private int userId;
    private String message;
    private long time;
    private int eventId;

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

    public Comment() {
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
}
