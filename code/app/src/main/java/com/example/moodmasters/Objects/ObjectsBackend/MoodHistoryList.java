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

    /**
     * MoodHistoryList constructor.
     * @param list
     *  This is a list of the current user's MoodEvents.
     * @param doc_ref
     *  This is the DocumentReference which references the document where the user's
     *  data is stored.
     * @param snapshot
     *  This is the DocumentSnapshot of the document where the user's data is stored.
     */
    public MoodHistoryList(ArrayList<MoodEvent> list, DocumentReference doc_ref, DocumentSnapshot snapshot) {
        super(list, doc_ref, snapshot);
    }

    /**
     * MoodHistoryList constructor.
     * @param doc_ref
     *  This is the DocumentReference which references the document where the user's
     *  data is stored.
     * @param snapshot
     *  This is the DocumentSnapshot of the document where the user's data is stored.
     */
    public MoodHistoryList(DocumentReference doc_ref, DocumentSnapshot snapshot) {
        super(doc_ref, snapshot);
    }

    public void setDatabaseData(DocumentReference doc_ref, DocumentSnapshot snapshot){
        // this is done by the Participant class
    }

    /**
     * This handles the updating of MoodHistoryList data into the database.
     * @param doc_ref
     *  This is the DocumentReference which references the document where the user's
     *  data is stored.
     */
    public void updateDatabaseData(DocumentReference doc_ref){
        doc_ref.set(this);
    }
}
