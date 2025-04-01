package com.example.moodmasters.Events.FollowersListScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.CommentViewingScreen.CommentViewingScreenActivity;
import com.example.moodmasters.Views.FollowersListScreen.FollowersListScreenActivity;
/**
 * Class that is responsible for handling the UI interaction of going back/exiting the FollowersListScreen
 * */
public class FollowersListScreenBackEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of going back/exiting the FollowersListScreen
     * which just encompasses finishing the activity and updating the follower and following counters
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        FollowersListScreenActivity activity = (FollowersListScreenActivity) context;
        model.fetchDatabaseDataBackendObject(BackendObject.State.COUNTERS, (w, v) -> {
            model.notifyViews(BackendObject.State.COUNTERS);
            activity.finish();
        });
    }
}
