package com.example.moodmasters.Events.LoginSignupScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.SignupLoginScreenActivity;

public class LoginSignupScreenLogOutEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.removeBackendObject(BackendObject.State.USER);
        model.removeBackendObject(BackendObject.State.MOODHISTORYLIST);
        model.removeBackendObject(BackendObject.State.FOLLOWINGLIST);
        model.removeBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
        //MoodHistoryListActivity mood_history_list = (MoodHistoryListActivity) context;
        //model.removeView(mood_history_list);
        //model.removeView(mood_history_list.getMoodHistoryListView());
        //model.removeView(mood_history_list.getMoodHistoryListView().getMoodHistoryArrayAdapter());
        Intent intent = new Intent(context, SignupLoginScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
