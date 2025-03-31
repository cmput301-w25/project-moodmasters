package com.example.moodmasters.Events.AlterMoodEventScreen;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenActivity;

/**
 * Class that is responsible for handling the UI interaction of unsetting the location attached to
 * a mood event on the AlterMoodEventScreen, this event only happens when a location is already attached
 * to a mood event and the user wants to detach the location
 * */
public class AlterMoodEventScreenUnsetLocationEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of detaching your location from a mood
     * event that your previously attached your location to
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
        TextView location_text = alter_mood_event.findViewById(R.id.alter_mood_location_text);
        Button location_button = alter_mood_event.findViewById(R.id.alter_mood_location_button);
        location_text.setText("Not Specified");
        location_button.setText("Get Location");
    }
}
