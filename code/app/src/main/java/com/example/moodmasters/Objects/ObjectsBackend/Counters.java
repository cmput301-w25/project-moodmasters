package com.example.moodmasters.Objects.ObjectsBackend;

import android.content.Intent;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodHistoryListScreen.MoodHistoryListScreenActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Arrays;
/**
 * Class that represents the counters for following and followers in the mood history list,
 * will be responsible for keeping track and updating the counters in accordance with the database
 * */
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

    /**
     * Fuction for fetching the followers and following count from the database
     * @param database
     *  Database object that stores documents and collections
     * @param model
     *  MVCModel that is needed to get other necessary data that might be needed in the function
     * @param listener
     *  Listener that will be executed after the query is done
     * */
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
    /**
     * Getter for returning the amount of followers
     * @return
     *  Returns amount of followers
     * */
    public String getFollowers(){
        return followers;
    }
    /**
     * Getter for returning the amount following
     * @return
     *  Returns the amount following
     * */
    public String getFollowing(){
        return following;
    }
}
