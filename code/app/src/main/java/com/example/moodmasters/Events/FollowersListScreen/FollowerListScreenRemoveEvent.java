package com.example.moodmasters.Events.FollowersListScreen;

import android.content.Context;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.FollowerList;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
/**
 * Class that is responsible for handling the UI interaction of removing a follower from a user's followers
 * list in the FollowerListScreen
 * */
public class FollowerListScreenRemoveEvent implements MVCController.MVCEvent {
    private String follower;
    public FollowerListScreenRemoveEvent(String init_follower){
        follower = init_follower;
    }
    /**
     * Executes code that is necessary for the UI interaction of deleting a follower from a user's follower
     * list, deletes the follower locally from the backend and deletes them from the database
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.removeDatabaseDataBackendObject(BackendObject.State.FOLLOWERLIST, follower, (w, v) -> {

            if (v){
                FollowerList follower_list = (FollowerList) w;
                follower_list.removeFollowerListElement(follower);
                model.notifyViews(BackendObject.State.FOLLOWERLIST);
                Toast.makeText(context, follower + " removed from your followers", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "Failed to remove " + follower, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
