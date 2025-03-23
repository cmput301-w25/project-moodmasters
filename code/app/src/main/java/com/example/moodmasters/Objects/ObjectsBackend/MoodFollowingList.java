package com.example.moodmasters.Objects.ObjectsBackend;

// Not needed for halfway checkpoint

import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MoodFollowingList extends MoodEventList {
    public MoodFollowingList(ArrayList<MoodEvent> list) {
        super(list);
    }
    public MoodFollowingList() {
        super();
    }
    public void removeDatabaseData(MVCDatabase database, Object object){
        // not needed for mood following list
    }
    public void addDatabaseData(MVCDatabase database, Object object){
        // not needed for mood following list
    }
}
