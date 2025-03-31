package com.example.moodmasters.Objects.ObjectsApp;

import com.example.moodmasters.Objects.ObjectsBackend.Participant;

import java.util.HashMap;
/**
 * Class that represents a comment a user can type on a mood event
 * */
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
    public Comment(HashMap<String,Object> map){
        this.username = (String) map.get("username");
        this.content = (String) map.get("content");
        this.timestamp = (String) map.get("timestamp");
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

    public String createString(){
        return username + "\n" + timestamp + "\n" + content;
    }
}