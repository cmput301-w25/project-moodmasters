package com.example.moodmasters.Events.MoodEventViewingScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodEventViewingScreen.MoodEventViewingScreenActivity;
/**
 * Class that is responsible for handling the UI interaction for exiting the MoodEventViewingScreen
 * */
public class MoodEventViewingScreenExitEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of exiting the MoodEventViewingScreen,
     * just encompasses closing the activity
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.removeView((MoodEventViewingScreenActivity) context);
        ((MoodEventViewingScreenActivity) context).finish();
    }
}
