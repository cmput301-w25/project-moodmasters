package com.example.moodmasters.Objects.ObjectsApp;

public class Comment {
    private String username;
    private String content;
    private long timestamp;

    // Constructor
    public Comment(String username, String content, long timestamp) {
        this.username = username;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
