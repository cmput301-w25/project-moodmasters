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
/**
 * Class that is responsible for handling the UI interaction of clicking on the following count in the MoodHistoryListScreen
 * which will bring up the FollowingListScreen and show your current followees
 * */
public class MoodHistoryListScreenFollowingEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of clicking on the following count in the MoodHistoryListScreen
     * which will bring up the FollowingListScreen, only encompasses querying the database to see if there
     * were any updates to the following list, then starting the activity
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
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
