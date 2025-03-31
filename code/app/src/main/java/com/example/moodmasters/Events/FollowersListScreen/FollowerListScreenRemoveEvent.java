package com.example.moodmasters.Events.FollowersListScreen;

import android.content.Context;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.FollowerList;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;

public class FollowerListScreenRemoveEvent implements MVCController.MVCEvent {
    private String follower;
    public FollowerListScreenRemoveEvent(String init_follower){
        follower = init_follower;
    }
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
