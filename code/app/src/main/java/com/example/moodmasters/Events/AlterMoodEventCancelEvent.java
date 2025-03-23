package com.example.moodmasters.Events;

import android.content.Context;

import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;

public class AlterMoodEventCancelEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ((AlterMoodEventActivity) context).finish();
    }
}
