package com.example.moodmasters.Objects.ObjectsBackend;

import android.widget.Toast;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

public class FollowRequest extends MVCBackend implements MVCDatabase.Fetch, MVCDatabase.Create{
    String status;
    String username;
    String target_username;
    public FollowRequest(String init_username){
        status = null;
        username = init_username;
    }
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
