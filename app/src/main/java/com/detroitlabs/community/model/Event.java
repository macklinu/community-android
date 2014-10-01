package com.detroitlabs.community.model;

public class Event {
    private int id;
    private int problemId;
    private long startTime;
    private long endTime;
    private int userId;
    private String description;

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
}
