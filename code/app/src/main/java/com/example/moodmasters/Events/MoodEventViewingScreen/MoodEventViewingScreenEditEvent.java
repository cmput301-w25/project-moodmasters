package com.example.moodmasters.Events.MoodEventViewingScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.Views.MoodEventViewingActivity;

public class MoodEventViewingScreenEditEvent implements MVCController.MVCEvent {
    private static MoodEvent mood_event;
    private static int position;
    public MoodEventViewingScreenEditEvent(MoodEvent init_mood_event, int init_position){
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
        System.out.println("AT START MOODEVENTVIEWINGEVENT");
        Intent i = new Intent((MoodEventViewingActivity) context, AlterMoodEventActivity.class);
        i.putExtra("Event", "EditMoodEvent");
        System.out.println("AT END MOODEVENTVIEWINGEVENT");
        context.startActivity(i);
    }

}
