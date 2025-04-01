package com.example.moodmasters.Events.MoodFollowingListScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.MoodEventViewingScreen.MoodEventViewingScreenActivity;
import com.example.moodmasters.Views.MoodFollowingListScreen.MoodFollowingListScreenActivity;
/**
 * Class that is responsible for handling the UI interaction of clicking a mood event in the MoodFollowingListScreen
 * */
public class MoodFollowingListScreenClickMoodEvent implements MVCController.MVCEvent{
    private static MoodEvent mood_event;
    private static int position;
    public MoodFollowingListScreenClickMoodEvent(MoodEvent init_mood_event, int init_position){
        mood_event = init_mood_event;
        position = init_position;
    }
    /**
     * Gets the mood event that the user clicked on
     * @return
     *  Returns the mood event that the user clicked on
     * */
    public static MoodEvent getMoodEvent(){
        return mood_event;
    }
    /**
     * Gets the position of the mood event that the user clicked on
     * @return
     *  Returns the position of the mood event in the mood following list that the user clicked on
     * */
    public static int getPosition(){
        return position;
    }

    /**
     * Executes code that is necessary for the UI interaction of clicking a mood event in the MoodFollowingListScreen,
     * encompasses just starting the activity and stating the mood event list type is following list
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        Intent i = new Intent((MoodFollowingListScreenActivity) context, MoodEventViewingScreenActivity.class);
        i.putExtra("MoodEventList", "MoodFollowingList");
        context.startActivity(i);
    }
}
