package com.example.moodmasters.Events.MoodHistoryListScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.MoodHistoryListScreen.MoodHistoryListScreenActivity;
import com.example.moodmasters.Views.MoodEventViewingScreen.MoodEventViewingScreenActivity;

public class MoodHistoryListScreenClickMoodEvent implements MVCController.MVCEvent{
    private static MoodEvent mood_event;
    private static int position;
    public MoodHistoryListScreenClickMoodEvent(MoodEvent init_mood_event, int init_position){
        mood_event = init_mood_event;
        position = init_position;
    }
    public static MoodEvent getMoodEvent(){
        return mood_event;
    }
    public static int getPosition(){
        return position;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        Intent i = new Intent((MoodHistoryListScreenActivity) context, MoodEventViewingScreenActivity.class);
        i.putExtra("MoodEventList", "MoodHistoryList");
        context.startActivity(i);
    }
}
