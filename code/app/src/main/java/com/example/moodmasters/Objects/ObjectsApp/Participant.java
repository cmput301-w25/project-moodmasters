package com.example.moodmasters.Objects.ObjectsApp;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Represents any participants that is not the currently logged in one that the logged in user needs to refer to
 * (ex. because of their following list)
 *
 * */
// Not needed for halfway checkpoint
public class Participant{
    private String username;

    private MoodHistoryList mood_history_list;          /*needed for generating mood following list of the user*/

    public Participant(String init_username){
        username = init_username;
    }

    public void getMoodHistoryListDatabaseData(FirebaseFirestore db){
        // TODO: Implement getting MoodHistoryList from database
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
