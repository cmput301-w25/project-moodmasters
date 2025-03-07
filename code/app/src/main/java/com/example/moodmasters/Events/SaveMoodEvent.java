package com.example.moodmasters.Events;

import android.util.Log;

import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SaveMoodEvent {
    // Ensure the photo URI is saved along with other mood event details

    // For example, saving to Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    void saveMoodEvent(MoodEvent moodEvent) {
        DocumentReference docRef = db.collection("mood_events").document();
        docRef.set(moodEvent)
                .addOnSuccessListener(aVoid -> {
                    Log.d("MoodEvent", "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e -> {
                    Log.w("MoodEvent", "Error writing document", e);
                });
    }
}
