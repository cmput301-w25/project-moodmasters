package com.example.moodmasters.Events.FollowingListScreen;

import android.content.Context;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
/**
 * Class that is responsible for handling the UI interaction of unfollowing a person from the user's following
 * list in the FollowingListScreen
 * */
public class FollowingListScreenRemoveEvent implements MVCController.MVCEvent {
    private String followee;
    public FollowingListScreenRemoveEvent(String init_followee){
        followee = init_followee;
    }
    /**
     * Executes code that is necessary for the UI interaction unfollowing a person in the FollowingListScreen
     * which just encompasses deleting them from the following list locally and deleting the from the following
     * list on the database
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.removeDatabaseDataBackendObject(BackendObject.State.FOLLOWINGLIST, followee, (w, v) -> {
            if (v){
                FollowingList following_list = (FollowingList) w;
                following_list.removeFollowingListElement(followee);
                controller.createBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
                model.notifyViews(BackendObject.State.FOLLOWINGLIST);
                Toast.makeText(context, followee + " removed from your following", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "Failed to remove " + followee, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
