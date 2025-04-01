package com.example.moodmasters.Events.MoodFollowingListScreen;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodFollowingListScreen.MoodFollowingListScreenFilterFragment;

/**
 * Class that is responsible for handling the UI interaction of bringing up the filter dialog for the
 * MoodFollowingListScreen
 * */
public class MoodFollowingListScreenShowFilterEvent implements MVCController.MVCEvent {
    private String fragment_tag = "Filter MoodFollowingList";
    /**
     * Executes code that is necessary for the UI interaction of bringing up the filter dialog for the
     * MoodFollowingListScreen, encompasses just bringing up the fragment and that's it
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        new MoodFollowingListScreenFilterFragment().show(((AppCompatActivity) context).getSupportFragmentManager(), fragment_tag);
    }
}
