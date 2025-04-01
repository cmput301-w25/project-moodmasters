package com.example.moodmasters.Events.MoodEventMapScreen;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodEventMapScreen.MoodEventMapScreenFilterFragment;
/**
 * Class that is responsible for handling the UI interaction of bringing up the filter dialog that will
 * allow us the filter mood events on the MoodEventMapScreen
 * */
public class MoodEventMapScreenShowFilterEvent implements MVCController.MVCEvent{
    private boolean location_status;
    private String fragment_tag = "Filter MoodEventMap";
    public MoodEventMapScreenShowFilterEvent(boolean init_location_status){
        location_status = init_location_status;
    }
    /**
     * Executes code that is necessary for the UI interaction of bringing up the filter dialog for filtering,
     * encompasses literally only just adding the fragment to the fragment manager
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */

    public void executeEvent(Context context, MVCModel model, MVCController controller){
        new MoodEventMapScreenFilterFragment(location_status).show(((AppCompatActivity) context).getSupportFragmentManager(), fragment_tag);
    }
}
