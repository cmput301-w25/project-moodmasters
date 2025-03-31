package com.example.moodmasters.Events.MoodHistoryListScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodHistoryListActivity;

public class MoodHistoryListScreenAddEvent implements MVCController.MVCEvent {
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        Intent i = new Intent((MoodHistoryListActivity) context, AlterMoodEventActivity.class);
        i.putExtra("Event", "AddMoodEvent");
        context.startActivity(i);
    }
}
