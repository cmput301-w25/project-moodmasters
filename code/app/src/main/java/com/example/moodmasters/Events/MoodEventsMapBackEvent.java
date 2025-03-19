package com.example.moodmasters.Events;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodEventsMapActivity;

public class MoodEventsMapBackEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ((MoodEventsMapActivity) context).finish();
    }
}
