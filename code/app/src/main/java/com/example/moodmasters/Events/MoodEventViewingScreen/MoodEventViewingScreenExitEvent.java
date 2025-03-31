package com.example.moodmasters.Events.MoodEventViewingScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodEventViewingScreen.MoodEventViewingScreenActivity;

public class MoodEventViewingScreenExitEvent implements MVCController.MVCEvent {

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.removeView((MoodEventViewingScreenActivity) context);
        ((MoodEventViewingScreenActivity) context).finish();
    }
}
