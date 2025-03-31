package com.example.moodmasters.Events.LoginSignupScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.LoginSignupScreen.LoginSignupScreenActivity;

/**
 * Class that is responsible for handling the UI interaction of logging out of the application via
 * the navigation bar and going back to the LoginSignupScreen
 * */
public class LoginSignupScreenLogOutEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of logging out and going back to the
     * LoginSignupScreen, this encompasses destroying some backend objects that were dependent on the
     * user and starting the new activity
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.removeBackendObject(BackendObject.State.USER);
        model.removeBackendObject(BackendObject.State.MOODHISTORYLIST);
        model.removeBackendObject(BackendObject.State.FOLLOWINGLIST);
        model.removeBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
        model.removeBackendObject(BackendObject.State.COUNTERS);
        Intent intent = new Intent(context, LoginSignupScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
