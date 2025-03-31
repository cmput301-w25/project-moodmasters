package com.example.moodmasters.Events.FollowingListScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.FollowersListScreen.FollowersListScreenActivity;
import com.example.moodmasters.Views.FollowingListScreen.FollowingListScreenActivity;

public class FollowingListScreenBackEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        FollowingListScreenActivity activity = (FollowingListScreenActivity) context;
        model.fetchDatabaseDataBackendObject(BackendObject.State.COUNTERS, (w, v) -> {
            model.notifyViews(BackendObject.State.COUNTERS);
            activity.finish();
        });
    }
}
