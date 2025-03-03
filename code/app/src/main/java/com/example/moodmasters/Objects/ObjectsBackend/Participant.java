package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.MVC.MVCBackend;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Represents any and all participants for the app, the user will be defined as the currently logged in user, once username of user
 * is determined the first backend object that will be initialized is the user object which will also fetch the mood_history_list
 * member by calling setDatabaseData, this member will also be stored in the model as the MOODHISTORYLIST backend object
 * */
public class Participant implements MVCBackend{
    private String username;

    private MoodHistoryList mood_history_list;          /*needed for generating mood following list of the user*/

    public Participant(String init_username){
        username = init_username;
    }

    public void setDatabaseData(FirebaseFirestore db){
        // TODO: Implement getting and possibly setting initial data from database
    }
    public void updateDatabaseData(FirebaseFirestore db){
        // TODO: Implement updating database with added/removed data
    }
    public String getUsername(){
        return username;
    }

    public void setUsername(String new_username){
        username = new_username;
    }
    public MoodHistoryList getMoodHistoryList(){
        return mood_history_list;
    }
}
