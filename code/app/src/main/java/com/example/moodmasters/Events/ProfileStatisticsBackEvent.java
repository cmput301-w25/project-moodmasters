package com.example.moodmasters.Events;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodEventsMapActivity;
import com.example.moodmasters.Views.ProfileStatisticsActivity;

public class ProfileStatisticsBackEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ((ProfileStatisticsActivity) context).finish();
    }
}
