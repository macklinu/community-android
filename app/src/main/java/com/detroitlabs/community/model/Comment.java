package com.detroitlabs.community.model;

public class Comment {
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
}
