package com.example.moodmasters.Events;

import android.content.Context;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.MoodEventViewingActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteMoodEventConfirmEvent implements MVCController.MVCEvent {
    private final MoodEvent moodEvent;
    private int position;

    public DeleteMoodEventConfirmEvent(MoodEvent moodEvent, int init_position) {
        this.moodEvent = moodEvent;
        this.position = init_position;
    }

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ((MoodEventViewingActivity) context).finish();
        model.removeFromBackendList(BackendObject.State.MOODHISTORYLIST, position);
    }
}