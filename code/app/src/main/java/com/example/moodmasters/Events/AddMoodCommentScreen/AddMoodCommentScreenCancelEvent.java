package com.example.moodmasters.Events.AddMoodCommentScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.AddMoodCommentScreen.AddMoodCommentScreenActivity;

/**
 * MVCEvent implementer responsible for handling the cancel event on the AddMoodCommentScreen
 * */
public class AddMoodCommentScreenCancelEvent implements MVCController.MVCEvent {
    /**
     * This function contains all the code that will be executed once the cancel button is hit (so just
     * closing of the activity)
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        AddMoodCommentScreenActivity add_comment_activity = (AddMoodCommentScreenActivity) context;
        add_comment_activity.finish();
    }
}
