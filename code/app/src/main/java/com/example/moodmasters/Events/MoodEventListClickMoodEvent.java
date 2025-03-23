package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.MoodHistoryListActivity;
import com.example.moodmasters.Views.MoodEventViewingActivity;

public class MoodEventListClickMoodEvent implements MVCController.MVCEvent{
    public enum MoodList{
        MOODFOLLOWINGLIST,
        MOODHISTORYLIST
    }
    private static MoodEvent mood_event;
    private static int position;
    private static MoodList mood_list_type;
    public MoodEventListClickMoodEvent(MoodEvent init_mood_event, int init_position, MoodList init_mood_list_type){
        mood_event = init_mood_event;
        position = init_position;
        mood_list_type = init_mood_list_type;
    }
    public static MoodEvent getMoodEvent(){
        return mood_event;
    }
    public static int getPosition(){
        return position;
    }
    public static MoodList getMoodListType(){
        return mood_list_type;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        context.startActivity(new Intent((MoodHistoryListActivity) context, MoodEventViewingActivity.class));
    }
}
