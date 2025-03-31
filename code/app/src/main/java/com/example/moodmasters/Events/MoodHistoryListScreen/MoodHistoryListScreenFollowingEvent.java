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

public class MoodHistoryListScreenFollowingEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        // this will also fetch the fields for each follower so it might be useful to do some decoupling though it is
        // not really necessary
        model.fetchDatabaseDataBackendObject(BackendObject.State.FOLLOWINGLIST, (w, v) ->{
            if (v){
                context.startActivity(new Intent(context, FollowingListScreenActivity.class));
            }
            else{
                Toast.makeText(context, "There Was An Error While Fetching The Following List", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
