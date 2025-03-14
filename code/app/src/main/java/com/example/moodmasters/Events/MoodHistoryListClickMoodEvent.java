package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.MoodHistoryListActivity;
import com.example.moodmasters.Views.MoodEventViewingActivity;

public class MoodHistoryListClickMoodEvent implements MVCController.MVCEvent{
    private static MoodEvent mood_event;
    public MoodHistoryListClickMoodEvent(MoodEvent init_mood_event){
        mood_event = init_mood_event;
    }
    public static MoodEvent getMoodEvent(){
        return mood_event;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        context.startActivity(new Intent((MoodHistoryListActivity) context, MoodEventViewingActivity.class));
    }
}
