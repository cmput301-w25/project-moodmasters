package com.example.moodmasters.Objects.ObjectsBackend;

import android.widget.Toast;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

/**
 * Class that represents a follow request a user can make or has made, contains code for getting the follow request, creating one,
 * accepting one, and ignoring one (fetch, create, add, remove respectively)
 * */
public class FollowRequest extends MVCBackend implements MVCDatabase.Fetch, MVCDatabase.Create, MVCDatabase.Add, MVCDatabase.Remove{
    String status;
    String username;
    String target_username;
    public FollowRequest(String init_username){
        status = null;
        username = init_username;
    }
    public FollowRequest(String init_username, String init_target_username){
        status = null;
        username = init_username;
        target_username = init_target_username;
    }
    /**
     * Function to fetch a follow request already existing on database
     * @param database
     *  Database object that stores documents and collections
     * @param model
     *  MVCModel that is needed to get other necessary data that might be needed in the function
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    @Override
    public void fetchDatabaseData(MVCDatabase database, MVCModel model, OnSuccessFetchListener listener) {
        DocumentReference user_ref = database.getDocument(username);
        System.out.println(target_username);
        database.addDocument(target_username);
        DocumentReference target_ref = database.getDocument(target_username);
        user_ref.collection("following").document(target_username).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                status = "Following";
                listener.onSuccess(FollowRequest.this, true);
            }
            else {
                // 2. If not following, check if follow request was already sent
                target_ref.collection("followRequests")
                        .document(username)
                        .get()
                        .addOnSuccessListener(requestSnap -> {
                            if (requestSnap.exists()) {
                                status = "Requested";
                                listener.onSuccess(FollowRequest.this, true);
                            }
                            else {
                                status = "Follow";
                                listener.onSuccess(FollowRequest.this, true);
                            }
                        });
            }
        }).addOnFailureListener(e -> {
            status = null;
            listener.onSuccess(FollowRequest.this, false);
        });
    }
    /**
     * function for creating a new follow request on the database
     * @param database
     *  Database object that stores documents and collections
     * @param model
     *  MVCModel that is needed to get other necessary data that might be needed in the function
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    @Override
    public void createDatabaseData(MVCDatabase database, MVCModel model, OnSuccessCreateListener listener) {
        DocumentReference target_ref = database.getDocument(target_username);
        HashMap<String, Object> field = new HashMap<String, Object>();
        field.put("requestedUsername", username);
        target_ref.collection("followRequests").document(username).set(field)
            .addOnSuccessListener(aVoid -> {
                listener.onSuccess(FollowRequest.this, true);
                //Toast.makeText(this, "Follow request sent!", Toast.LENGTH_SHORT).show();
                //follow_button.setText("Requested");
            })
            .addOnFailureListener(e -> {
                listener.onSuccess(FollowRequest.this, false);
                //Toast.makeText(this, "Error sending request", Toast.LENGTH_SHORT).show();
            });
    }
    /**
     * function for adding accepting a follow request and adding it on the database
     * @param database
     *  Database object that stores documents and collections
     * @param object
     *  object to add to the database
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    @Override
    public <T> void addDatabaseData(MVCDatabase database, T object, OnSuccessAddListener listener) {
        String following_user = (String) object;
        DocumentReference user_ref = database.getDocument(username);
        database.addDocument(following_user);
        DocumentReference following_ref = database.getDocument(following_user);
        user_ref.collection("followers").document(following_user)
                .set(new HashMap<>()) // Ensures Firestore initializes the collection

                .addOnSuccessListener(aVoid -> {
                    // Add the current user to the requester's "following" list
                    following_ref.collection("following").document(username)
                            .set(new HashMap<>()) // Ensures Firestore initializes the collection

                            .addOnSuccessListener(aVoid2 -> {
                                // Remove from follow requests after both operations succeed
                                user_ref.collection("followRequests").document(following_user)
                                        .delete()
                                        .addOnSuccessListener(aVoid3 -> {
                                            listener.onSuccess(FollowRequest.this, true);
                                        })
                                        .addOnFailureListener(e -> listener.onSuccess(FollowRequest.this, false));
                            })
                            .addOnFailureListener(e -> listener.onSuccess(FollowRequest.this, false));
                })
                .addOnFailureListener(e -> listener.onSuccess(FollowRequest.this, false));
    }

    /**
     * Function for ignoring a follow request and putting it on the database
     * @param database
     *  Database object that stores documents and collections
     * @param object
     *  object to remove to the database
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    @Override
    public <T> void removeDatabaseData(MVCDatabase database, T object, OnSuccessRemoveListener listener) {
        String following_user = (String) object;
        DocumentReference user_ref = database.getDocument(username);
        database.addDocument(following_user);
        user_ref.collection("followRequests").document(following_user).delete().addOnSuccessListener(aVoid -> {
            listener.onSuccess(FollowRequest.this, true);
        }).addOnFailureListener(e -> {
            listener.onSuccess(FollowRequest.this, false);
        });
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String new_status){
        status = new_status;
    }
    public String getTargetUsername(){
        return target_username;
    }

    public void setTargetUsername(String new_target_username){
        target_username = new_target_username;
    }
}
