package com.example.moodmasters.MVC;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public interface MVCBackend {
    public void setDatabaseData(DocumentReference docRef, DocumentSnapshot snapshot); // necessary
    public void updateDatabaseData(DocumentReference docRef);
}
