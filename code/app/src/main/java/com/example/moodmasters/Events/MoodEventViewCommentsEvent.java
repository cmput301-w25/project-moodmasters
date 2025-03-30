package com.example.moodmasters.Events;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.CommentViewingActivity;
import com.example.moodmasters.Views.MoodEventViewingActivity;

public class MoodEventViewCommentsEvent implements MVCController.MVCEvent {
    private static MoodEvent mood_event;
    private static int position;
    private static String mood_event_list_type;
    public MoodEventViewCommentsEvent(MoodEvent init_mood_event, int init_position, String init_mood_event_list_type){
        mood_event = init_mood_event;
        position = init_position;
        mood_event_list_type = init_mood_event_list_type;
    }
    public static MoodEvent getMoodEvent(){
        return mood_event;
    }
    public static int getPosition(){
        return position;
    }
    public static String getMoodEventListType(){
        return mood_event_list_type;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        Intent intent = new Intent(context, CommentViewingActivity.class);
        context.startActivity(intent);
    }
}
