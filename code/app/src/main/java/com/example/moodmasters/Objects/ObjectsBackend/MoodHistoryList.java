package com.example.moodmasters.Objects.ObjectsBackend;

import androidx.annotation.NonNull;

import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MoodHistoryList extends MoodEventList {

    public MoodHistoryList() {}

    public MoodHistoryList(ArrayList<MoodEvent> list, DocumentReference docRef, DocumentSnapshot snapshot) {
        super(list, docRef, snapshot);
    }

    public MoodHistoryList(DocumentReference docRef, DocumentSnapshot snapshot) {
        super(docRef, snapshot);
    }

    public void setDatabaseData(DocumentReference docRef, DocumentSnapshot snapshot){
        // TODO: Implement getting and possibly setting initial data from database

    }
    public void updateDatabaseData(DocumentReference docRef){
        // TODO: Implement updating database with added/removed data
        docRef.set(this);
    }
}
