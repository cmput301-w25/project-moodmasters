package com.example.moodmasters.Events.AlterMoodEventScreen;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenActivity;
import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenLocationActivity;

/**
 * Class that is responsible for handling the UI interaction of attaching your location to a mood
 * event that you are currently altering
 * */
public class AlterMoodEventScreenSetLocationEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of attaching your location to a mood
     * event that you are currently altering
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        AlterMoodEventScreenActivity alter_mood_event = (AlterMoodEventScreenActivity) context;
        Intent intent = new Intent(alter_mood_event, AlterMoodEventScreenLocationActivity.class);
        alter_mood_event.startActivityForResult(intent, 1001); // 1001 is a request code for identification
    }
}
