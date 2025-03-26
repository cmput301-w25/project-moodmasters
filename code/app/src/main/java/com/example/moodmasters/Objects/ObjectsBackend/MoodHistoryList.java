package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;

/**
 * This is a class that keeps track of MoodEvents in the current user's history.
 */
public class MoodHistoryList extends MoodEventList {
    private Participant participant;            /*wtf is vero talking about circular references are completely fine in java*/
    public MoodHistoryList(ArrayList<MoodEvent> list, Participant init_participant) {
        super(list);
        participant = init_participant;
    }
    public MoodHistoryList(Participant init_participant) {
        super();
        participant = init_participant;
    }
    public MoodHistoryList() {
        super();
    }
    public void removeDatabaseData(MVCDatabase database, Object object){
        DocumentReference doc_ref = database.getDocument(participant.getUsername());
        doc_ref.update("list", FieldValue.arrayRemove(object));
    }
    public void addDatabaseData(MVCDatabase database, Object object){
        DocumentReference doc_ref = database.getDocument(participant.getUsername());
        doc_ref.update("list", FieldValue.arrayUnion(object));
    }
}
