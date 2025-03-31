package com.example.moodmasters.Events.FollowersListScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.CommentViewingScreen.CommentViewingScreenActivity;
import com.example.moodmasters.Views.FollowersListScreen.FollowersListScreenActivity;

public class FollowersListScreenBackEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        FollowersListScreenActivity activity = (FollowersListScreenActivity) context;
        model.fetchDatabaseDataBackendObject(BackendObject.State.COUNTERS, (w, v) -> {
            model.notifyViews(BackendObject.State.COUNTERS);
            activity.finish();
        });
    }
}
