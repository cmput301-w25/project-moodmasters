package com.example.moodmasters.Events.CommentViewingScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.CommentViewingScreen.CommentViewingScreenActivity;
/**
 * Class that is responsible for handling the UI interaction of canceling or exiting the CommentViewingScreen
 * and going back to the MoodEventViewingScreen
 * */
public class CommentViewingScreenCancelEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction exiting the CommentViewingScreen, so just
     * closes the activity
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        CommentViewingScreenActivity comment_viewing_activity = (CommentViewingScreenActivity) context;
        model.removeView(comment_viewing_activity);
        model.removeView(comment_viewing_activity.getArrayAdapter());
        comment_viewing_activity.finish();
    }
}
