package com.example.moodmasters.Events.MoodEventViewingScreen;

import android.content.Context;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.MoodEventViewingScreen.MoodEventViewingScreenActivity;
/**
 * Class that is responsible for handling the UI interaction of deleting a mood event from the user's
 * mood history list in the MoodEventViewingScreen
 * */
public class MoodEventViewingScreenDeleteEvent implements MVCController.MVCEvent {
    private final MoodEvent moodEvent;
    private int position;

    public MoodEventViewingScreenDeleteEvent(MoodEvent moodEvent, int init_position) {
        this.moodEvent = moodEvent;
        this.position = init_position;
    }
    /**
     * Executes code that is necessary for the UI interaction of deleting a mood event from the user's
     * mood history list, encompasses just querying the backend to delete the mood event, then
     * closing the activity
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.removeFromBackendList(BackendObject.State.MOODHISTORYLIST, position, (w, v) -> {});
        model.removeView((MoodEventViewingScreenActivity) context);
        ((MoodEventViewingScreenActivity) context).finish();
    }
}