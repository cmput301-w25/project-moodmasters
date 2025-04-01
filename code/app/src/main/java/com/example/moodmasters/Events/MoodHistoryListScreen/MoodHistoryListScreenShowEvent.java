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
/**
 * Class that is responsible for handling the UI interaction of bringing up the MoodHistoryListScreen
 * */
public class MoodHistoryListScreenShowEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of bringing up the MoodHistoryListScreen,
     * encompasses fetching the database for the counters for following and follower and starting
     * the activity
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        Counters counters = (Counters) model.getBackendObject(BackendObject.State.COUNTERS);
        counters.fetchDatabaseData(model.getDatabase(), model, (backend_object, result) -> {
            Intent intent = new Intent(context, MoodHistoryListScreenActivity.class);
            context.startActivity(intent);
        });
    }
}
