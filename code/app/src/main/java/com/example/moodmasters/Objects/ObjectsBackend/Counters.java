package com.example.moodmasters.Objects.ObjectsBackend;

import android.content.Intent;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodHistoryListScreen.MoodHistoryListScreenActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Arrays;

public class Counters extends MVCBackend implements MVCDatabase.Fetch{
    private String username;
    private String followers;
    private String following;
    public Counters(String init_followers, String init_following, String init_username){
        followers = init_followers;
        following = init_following;
        username = init_username;
    }
    public Counters(String init_username){
        followers = "0";
        following = "0";
        username = init_username;
    }
    public Counters(){
        followers = "0";
        following = "0";
        username = null;
    }

    @Override
    public void fetchDatabaseData(MVCDatabase database, MVCModel model, OnSuccessFetchListener listener){
        database.addCollection("participants");
        database.addDocument(username);
        CollectionReference follower_ref = database.getDocument(username).collection("followers");
        CollectionReference following_ref = database.getDocument(username).collection("following");
        follower_ref.get().addOnSuccessListener(snapshot -> {
            followers = String.valueOf(snapshot.size());
            following_ref.get().addOnSuccessListener(snapshot2 -> {
                following = String.valueOf(snapshot2.size());
                listener.onSuccess(this, true);
            });
        });
    }
    public String getFollowers(){
        return followers;
    }
    public String getFollowing(){
        return following;
    }
}
