package com.example.moodmasters.Events.MoodEventViewingScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenActivity;
import com.example.moodmasters.Views.MoodEventViewingScreen.MoodEventViewingScreenActivity;
/**
 * Class that is responsible for handling the UI interaction of clicking the edit button in the
 * MoodEventViewingScreen in an attempt to edit the mood event
 * */
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
    /**
     * Executes code that is necessary for the UI interaction of clicking the edit button in the
     * MoodEventViewingScreen, just encompasses starting the AlterMoodEventScreenActivity and passing
     * to it an argument which states that we are editing a mood event and not adding
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        Intent i = new Intent((MoodEventViewingScreenActivity) context, AlterMoodEventScreenActivity.class);
        i.putExtra("Event", "EditMoodEvent");
        context.startActivity(i);
    }

}
