package com.example.moodmasters.Events.UserSearchScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.UserSearchScreen.UserSearchScreenActivity;

public class UserSearchScreenCancelEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        UserSearchScreenActivity activity = (UserSearchScreenActivity) context;
        model.removeBackendObject(BackendObject.State.USERSEARCH);
        model.removeView(activity);
        activity.finish();
    }
}
