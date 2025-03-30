package com.example.moodmasters.Objects.ObjectsApp;


public class FollowRequest {
    private String requesterUsername;

    public FollowRequest() { } // Required for Firestore

    public FollowRequest(String requesterUsername) {
        this.requesterUsername = requesterUsername;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }
}