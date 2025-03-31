package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
/**
 * Class that is responsible for groups of follow requests, more specifically for the FollowRequests activity and retrieving
 * all the follow requests for the user
 * */
public class FollowRequestList extends MVCBackend implements MVCDatabase.Fetch, MVCDatabase.Add, MVCDatabase.Remove {
    private ArrayList<FollowRequest> follow_requests;
    private ArrayList<String> follow_requests_string;
    private String username;
    public FollowRequestList(String init_username){
        follow_requests = new ArrayList<FollowRequest>();
        follow_requests_string = new ArrayList<String>();
        username = init_username;

    }
    @Override
    public void fetchDatabaseData(MVCDatabase database, MVCModel model, OnSuccessFetchListener listener) {
        DocumentReference user_ref = database.getDocument(username);
        user_ref.collection("followRequests").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                follow_requests.clear();
                follow_requests_string.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    follow_requests.add(new FollowRequest(username, document.getId()));
                    follow_requests_string.add(document.getId());
                }
                listener.onSuccess(FollowRequestList.this, true);
            }
            else{
                listener.onSuccess(FollowRequestList.this, false);
            }
        });
    }
    @Override
    public <T> void addDatabaseData(MVCDatabase database, T object, OnSuccessAddListener listener) {
        FollowRequest follow_request = (FollowRequest) object;
        follow_request.addDatabaseData(database, follow_request.getTargetUsername(), (w, v) -> {
            if (v){
                int position = follow_requests.indexOf(follow_request);
                follow_requests.remove(position);
                follow_requests_string.remove(position);
                listener.onSuccess(FollowRequestList.this, true);
            }
            else{
                listener.onSuccess(FollowRequestList.this, false);
            }
        });
    }

    @Override
    public <T> void removeDatabaseData(MVCDatabase database, T object, OnSuccessRemoveListener listener) {
        FollowRequest follow_request = (FollowRequest) object;
        follow_request.removeDatabaseData(database, follow_request.getTargetUsername(), (w, v) -> {
            if (v){
                int position = follow_requests.indexOf(follow_request);
                follow_requests.remove(position);
                follow_requests_string.remove(position);
                listener.onSuccess(FollowRequestList.this, true);
            }
            else{
                listener.onSuccess(FollowRequestList.this, false);
            }
        });
    }
    public ArrayList<String> getFollowRequestsString(){
        return follow_requests_string;
    }

    public ArrayList<FollowRequest> getFollowRequests(){
        return follow_requests;
    }
    public FollowRequest getFollowRequestsPosition(int position){
        return follow_requests.get(position);
    }
}
