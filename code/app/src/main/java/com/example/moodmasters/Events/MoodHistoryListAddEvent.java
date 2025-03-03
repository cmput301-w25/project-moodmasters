package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodHistoryListActivity;
import com.example.moodmasters.Views.SignupLoginScreenActivity;

public class MoodHistoryListAddEvent implements MVCEvent {
    public void executeEvent(Context context, MVCModel backend, MVCController controller){
        Intent i = new Intent((MoodHistoryListActivity) context, AlterMoodEventActivity.class);
        i.putExtra("Event", "AddMoodEvent");
        context.startActivity(i);
    }
}
