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

    /**
     * Gets the comment's poster's name
     * @return
     *  Returns String for username
     * */
    public String getUsername() {
        return username;
    }
    /**
     * Gets the comments content
     * @return
     *  Returns String for the comments content
     * */
    public String getContent() {
        return content;
    }
    /**
     * Gets the comment's timestamp
     * @return
     *  Returns String for when the comment was posted
     * */
    public String getTimestamp() {
        return timestamp;
    }
    /**
     * Gets a full string representation of the comment
     * @return
     *  Returns String for the whole comment
     * */
    public String createString(){
        return username + "\n" + timestamp + "\n" + content;
    }
}