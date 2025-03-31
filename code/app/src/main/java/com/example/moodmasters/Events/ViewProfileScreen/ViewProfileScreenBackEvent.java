package com.example.moodmasters.Events.ViewProfileScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.ViewProfileScreen.ViewProfileScreenActivity;

public class ViewProfileScreenBackEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ViewProfileScreenActivity activity = (ViewProfileScreenActivity) context;
        model.removeBackendObject(BackendObject.State.FOLLOWREQUEST);
        model.removeView(activity);
        model.removeView(activity.getAdapter());
        activity.finish();
    }
}
