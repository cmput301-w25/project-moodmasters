package com.example.moodmasters.Events.MoodEventViewingScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.CommentViewingScreen.CommentViewingScreenActivity;

/**
 * Class that is responsible for handling the UI interaction of bringing up the Comment list for a
 * mood event from the MoodEventViewingScreen
 * */
public class MoodEventViewingScreenCommentsEvent implements MVCController.MVCEvent {
    private static MoodEvent mood_event;
    private static int position;
    private static String mood_event_list_type;
    public MoodEventViewingScreenCommentsEvent(MoodEvent init_mood_event, int init_position, String init_mood_event_list_type){
        mood_event = init_mood_event;
        position = init_position;
        mood_event_list_type = init_mood_event_list_type;
    }
    /**
     * Returns the mood event for the comments
     * @return
     *  MoodEvent which corresponds to the comments
     * */
    public static MoodEvent getMoodEvent(){
        return mood_event;
    }
    /**
     * Returns position of the mood event in the mood history list or mood following list
     * @return
     *  int which corresponds to the position of the mood event
     * */
    public static int getPosition(){
        return position;
    }
    /**
     * Returns string stating the type of mood event list (either history of following)
     * @return
     *  String being either "MoodHistoryList" or "MoodFollowingList"
     * */
    public static String getMoodEventListType(){
        return mood_event_list_type;
    }
    /**
     * Executes code that is necessary for the UI interaction of bringing up the CommentViewingScreen
     * from the MoodEventViewingScreen, only encompasses just starting the activity
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        Intent intent = new Intent(context, CommentViewingScreenActivity.class);
        context.startActivity(intent);
    }
}
