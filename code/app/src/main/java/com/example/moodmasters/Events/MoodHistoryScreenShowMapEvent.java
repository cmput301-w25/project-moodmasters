package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodEventsMapActivity;
import com.example.moodmasters.Views.MoodHistoryListActivity;

public class MoodHistoryScreenShowMapEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        context.startActivity(new Intent((MoodHistoryListActivity) context, MoodEventsMapActivity.class));
    }
}
