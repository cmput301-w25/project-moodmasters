package com.example.moodmasters.Events.MoodHistoryListScreen;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.AddMoodCommentScreen.AddMoodCommentScreenActivity;
import com.example.moodmasters.Views.FollowersListScreen.FollowersListScreenActivity;
import com.example.moodmasters.Views.FollowingListScreen.FollowingListScreenActivity;

public class MoodHistoryListScreenFollowersEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        model.fetchDatabaseDataBackendObject(BackendObject.State.FOLLOWERLIST, (w, v) ->{
            if (v){
                context.startActivity(new Intent(context, FollowersListScreenActivity.class));
            }
            else{
                Toast.makeText(context, "There Was An Error While Fetching The Follower List", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
