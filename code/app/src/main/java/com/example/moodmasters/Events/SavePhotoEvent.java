package com.example.moodmasters.Events;

import android.net.Uri;
import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class SavePhotoEvent {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void savePhoto(Uri imageUri) {
        Map<String, Object> photoData = new HashMap<>();
        photoData.put("photoUri", imageUri.toString());

        // Optionally, add metadata (e.g., upload time, user ID) here

        db.collection("mood_event_photos")
                .add(photoData)
                .addOnSuccessListener(documentReference -> {
                    Log.d("PhotoUpload", "Photo successfully uploaded with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w("PhotoUpload", "Error uploading photo", e);
                });
    }
}
