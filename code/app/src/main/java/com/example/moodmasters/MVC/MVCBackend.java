package com.example.moodmasters.MVC;

import com.google.firebase.firestore.FirebaseFirestore;

public interface MVCBackend {
    public void setDatabaseData(FirebaseFirestore db); // necessary
    public void updateDatabaseData(FirebaseFirestore db);
}
