package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.MVC.MVCBackend;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Represents the currently logged in user
 *
 *
 * */
public class User implements MVCBackend {
    private String username;

    public User(String init_username){
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
}
