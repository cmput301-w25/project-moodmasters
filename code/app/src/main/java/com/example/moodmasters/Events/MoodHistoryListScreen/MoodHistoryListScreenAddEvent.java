package com.example.moodmasters.Events.MoodHistoryListScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenActivity;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodHistoryListScreen.MoodHistoryListScreenActivity;
/**
 * Class that is responsible for handling the UI interaction of bringing up the AlterMoodEventScreen for
 * adding a new mood event
 * */
public class MoodHistoryListScreenAddEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of bringing up the AlterMoodEventScreen
     * to add a new mood event, encompasses just starting the activity and giving as intent argument stating
     * that we are adding a mood event
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        Intent i = new Intent((MoodHistoryListScreenActivity) context, AlterMoodEventScreenActivity.class);
        i.putExtra("Event", "AddMoodEvent");
        context.startActivity(i);
    }
}
