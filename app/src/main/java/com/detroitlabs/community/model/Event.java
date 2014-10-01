package com.detroitlabs.community.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Event implements Parcelable {
    private int id;
    private int problemId;
    private long startTime;
    private long endTime;
    private int userId;
    private String description;
    private List<Comment> comments;

    public int getId() {
        return id;
    }

    public int getProblemId() {
        return problemId;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.problemId);
        dest.writeLong(this.startTime);
        dest.writeLong(this.endTime);
        dest.writeInt(this.userId);
        dest.writeString(this.description);
        dest.writeList(this.comments);
    }

    public Event() {
    }

    private Event(Parcel in) {
        this.id = in.readInt();
        this.problemId = in.readInt();
        this.startTime = in.readLong();
        this.endTime = in.readLong();
        this.userId = in.readInt();
        this.description = in.readString();
        this.comments = new ArrayList<Comment>();
        in.readList(this.comments, Comment.class.getClassLoader());
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
