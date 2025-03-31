package com.example.moodmasters.Events.FollowingListScreen;

import android.content.Context;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;

public class FollowingListScreenRemoveEvent implements MVCController.MVCEvent {
    private String followee;
    public FollowingListScreenRemoveEvent(String init_followee){
        followee = init_followee;
    }
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
