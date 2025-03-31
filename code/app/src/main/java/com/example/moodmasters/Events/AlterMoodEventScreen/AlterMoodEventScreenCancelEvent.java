package com.example.moodmasters.Events.AlterMoodEventScreen;

import android.content.Context;

import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenActivity;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;

public class AlterMoodEventScreenCancelEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ((AlterMoodEventScreenActivity) context).finish();
    }
}
