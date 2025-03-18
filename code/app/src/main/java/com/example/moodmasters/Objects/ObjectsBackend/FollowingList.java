package com.example.moodmasters.Objects.ObjectsBackend;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FollowingList {
    private FirebaseFirestore db;
    private String userId;
    private CollectionReference followingRef;
    private CollectionReference followRequestsRef;

    public FollowingList(String userId) {
        this.db = FirebaseFirestore.getInstance();
        this.userId = userId;
        this.followingRef = db.collection("participants").document(userId).collection("following");
        this.followRequestsRef = db.collection("participants").document(userId).collection("followRequests");
    }

    // Fetch list of following users
    public void fetchFollowing(Consumer<List<String>> callback) {
        followingRef.get().addOnCompleteListener(task -> {
            List<String> following = new ArrayList<>();
            if (task.isSuccessful()) {
                for (var document : task.getResult()) {
                    following.add(document.getId());
                }
            }
            callback.accept(following);
        });
    }

    // Send follow request
    public void sendFollowRequest(String targetUserId) {
        DocumentReference targetRef = db.collection("participants").document(targetUserId)
                .collection("followRequests").document(userId);
        targetRef.set(new Object()); // Add requester to follow requests
    }

    // Accept follow request
    public void acceptFollowRequest(String requesterId) {
        DocumentReference requesterRef = followingRef.document(requesterId);
        requesterRef.set(new Object()); // Move to following list
        followRequestsRef.document(requesterId).delete(); // Remove from requests
    }

    // Reject follow request
    public void rejectFollowRequest(String requesterId) {
        followRequestsRef.document(requesterId).delete();
    }

    // Fetch follow requests
    public void fetchFollowRequests(Consumer<List<String>> callback) {
        followRequestsRef.get().addOnCompleteListener(task -> {
            List<String> requesters = new ArrayList<>();
            if (task.isSuccessful()) {
                for (var document : task.getResult()) {
                    requesters.add(document.getId());
                }
            }
            callback.accept(requesters);
        });
    }
}
