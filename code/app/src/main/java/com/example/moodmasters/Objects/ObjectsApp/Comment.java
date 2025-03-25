package com.example.moodmasters.Objects.ObjectsApp;

import com.example.moodmasters.Objects.ObjectsBackend.Participant;

public class Comment {
    private String username;
    private String content;
    private String timestamp;

    // Constructor
    public Comment(String username, String content, String timestamp) {
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

    public String getTimestamp() {
        return timestamp;
    }
}