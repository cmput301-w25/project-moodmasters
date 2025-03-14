package com.example.moodmasters.Events;

import android.content.Context;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteMoodEventConfirmEvent implements MVCController.MVCEvent {
    private final MoodEvent moodEvent;

    public DeleteMoodEventConfirmEvent(MoodEvent moodEvent) {
        this.moodEvent = moodEvent;
    }

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        // Get the user's MoodHistoryList
        MoodHistoryList moodHistoryList = (MoodHistoryList) model.getBackendObject(BackendObject.State.MOODHISTORYLIST);

        if (moodHistoryList != null) {
            // Remove the mood event from local list
            moodHistoryList.removeObject(moodEvent);

            // Get Firestore instance
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Delete from Firestore
            db.collection("mood_events")
                    .document(moodEvent.getEpochTime() + "")
                    .delete()
                    .addOnSuccessListener(aVoid -> model.notifyViews(BackendObject.State.MOODHISTORYLIST))
                    .addOnFailureListener(e -> System.err.println("Error deleting mood event: " + e.getMessage()));
        }
    }
}