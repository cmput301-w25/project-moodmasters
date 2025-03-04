package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.google.firebase.firestore.FirebaseFirestore;

// Not needed for halfway checkpoint
public class MoodFollowingList extends MoodEventList {
    public MoodFollowingList(FirebaseFirestore db) {
        super(db);
    }

    public void setDatabaseData(FirebaseFirestore db){
        // TODO: Implement getting and possibly setting initial data from database
    }
    public void updateDatabaseData(FirebaseFirestore db){
        // TODO: Implement updating database with added/removed data
    }
}
