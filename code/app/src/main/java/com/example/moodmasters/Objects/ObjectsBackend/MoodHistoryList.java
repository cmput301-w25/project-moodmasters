package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

/**
 * This is a class that keeps track of MoodEvents in the current user's history.
 */
public class MoodHistoryList extends MoodEventList {

    public MoodHistoryList(ArrayList<MoodEvent> list, DocumentReference docRef, DocumentSnapshot snapshot) {
        super(list, docRef, snapshot);
    }

    public MoodHistoryList(DocumentReference docRef, DocumentSnapshot snapshot) {
        super(docRef, snapshot);
    }

    public void setDatabaseData(DocumentReference docRef, DocumentSnapshot snapshot){
        // this is done by the Participant class
    }
    public void updateDatabaseData(DocumentReference docRef){
        docRef.set(this);
    }
}
