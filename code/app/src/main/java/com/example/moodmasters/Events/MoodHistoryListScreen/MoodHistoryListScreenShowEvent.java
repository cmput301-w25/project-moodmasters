package com.example.moodmasters.Events.MoodHistoryListScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.Counters;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.CommentViewingScreen.CommentViewingScreenActivity;
import com.example.moodmasters.Views.MoodHistoryListScreen.MoodHistoryListScreenActivity;
import com.google.firebase.firestore.CollectionReference;

public class MoodHistoryListScreenShowEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        Counters counters = (Counters) model.getBackendObject(BackendObject.State.COUNTERS);
        counters.fetchDatabaseData(model.getDatabase(), model, (backend_object, result) -> {
            Intent intent = new Intent(context, MoodHistoryListScreenActivity.class);
            context.startActivity(intent);
        });
    }
}
