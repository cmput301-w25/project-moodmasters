package com.example.moodmasters.Events;

import android.content.Context;

import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCEvent;
import com.example.moodmasters.MVC.MVCModel;

public class AlterMoodEventCancelEvent implements MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel backend, MVCController controller) {
        ((AlterMoodEventActivity) context).finish();
    }
}
