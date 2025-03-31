package com.example.moodmasters.Events.MoodFollowingListScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.UserSearchScreen.UserSearchScreenActivity;
/**
 * Class that is responsible for handling the UI interaction of bringing up the UserSearchScreen from the
 * MoodFollowingListScreen
 * */
public class MoodFollowingListScreenUserSearchEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of bringing up the UserSearchScreen from the
     * MoodFollowingListScreen, encompasses creating a backend object for our database queries and then
     * starting the activity
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.createBackendObject(BackendObject.State.USERSEARCH);
        Intent intent = new Intent(context, UserSearchScreenActivity.class);
        context.startActivity(intent);
    }
}
