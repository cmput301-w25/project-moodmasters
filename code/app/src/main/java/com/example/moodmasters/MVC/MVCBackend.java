package com.example.moodmasters.MVC;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Interface that is implemented by objects that exist in the backend (objects that are managed by the model) that is
 * used to give a common typing between them, backend objects will implement 2 methods that are defined for interaction with the database,
 * one for the initial interaction with the database (ex. think about getting all the mood events in a history list for a participant), and
 * the other for getting updating the database with new data that is given by the user over the course of the app (ex. think about a user
 * adding a mood event to their mood history list).
 *
 * */
public interface MVCBackend {
    /**
     * Setup the initial interaction with the database if it's necessary
     * @param snapshot
     *  Snapshot for fast interaction with document
     * @param docRef
     *  The database document needed for setup
     * */
    public void setDatabaseData(DocumentReference docRef, DocumentSnapshot snapshot); // necessary
    /**
     * Update database with gotten data
     * @param docRef
     *  The database document needed for updating
     * */
    public void updateDatabaseData(DocumentReference docRef);
}
