package com.example.moodmasters.Events.AlterMoodEventScreen;

import android.content.Context;

import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenActivity;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;

/**
 * Class that is responsible for handling the cancel UI interaction on the AlterMoodEventScreen
 * */
public class AlterMoodEventScreenCancelEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of canceling the altering of a UI event
     * on the AlterMoodEventScreen, which would correspond to just exiting the activity
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ((AlterMoodEventScreenActivity) context).finish();
    }
}
