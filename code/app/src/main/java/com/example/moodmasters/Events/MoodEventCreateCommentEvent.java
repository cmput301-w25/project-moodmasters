package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.AddCommentActivity;

public class MoodEventCreateCommentEvent implements MVCController.MVCEvent {
    private static MoodEvent mood_event;
    private static int position;
    public static MoodEvent getMoodEvent(){
        return mood_event;
    }
    public static int getPosition(){
        return position;
    }
    public MoodEventCreateCommentEvent(MoodEvent init_mood_event, int init_position){
        mood_event = init_mood_event;
        position = init_position;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        Intent intent = new Intent(context, AddCommentActivity.class);
        context.startActivity(intent);
    }
}
