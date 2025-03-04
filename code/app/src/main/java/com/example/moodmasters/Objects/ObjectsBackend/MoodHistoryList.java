package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MoodHistoryList extends MoodEventList {
    public MoodHistoryList(ArrayList<Mood> list, FirebaseFirestore db) {
        super(list, db);
    }

    public MoodHistoryList(FirebaseFirestore db) {
        super(db);
    }

    public void setDatabaseData(FirebaseFirestore db){
        // TODO: Implement getting and possibly setting initial data from database

    }
    public void updateDatabaseData(FirebaseFirestore db){
        // TODO: Implement updating database with added/removed data
    }
}
