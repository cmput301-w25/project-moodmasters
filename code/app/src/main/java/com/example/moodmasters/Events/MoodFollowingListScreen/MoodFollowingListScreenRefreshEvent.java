package com.example.moodmasters.Events.MoodFollowingListScreen;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.MoodFollowingListScreen.MoodFollowingListScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MoodFollowingListScreenRefreshEvent implements MVCController.MVCEvent {
    private Context context;
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        this.context = context;
        FollowingList following_list = ((FollowingList) model.getBackendObject(BackendObject.State.FOLLOWINGLIST));
        following_list.fetchDatabaseData(model.getDatabase(), model, (v, w) -> {
            if (w){
                model.notifyViews(BackendObject.State.FOLLOWINGLIST);
                updateSwipeContainer();
            }
            else{
                Toast.makeText(context, "There Was An Error Fetching From Database", Toast.LENGTH_SHORT).show();
                updateSwipeContainer();
            }
        });
    }
    public void updateSwipeContainer(){
        MoodFollowingListScreenActivity mood_following_activity = (MoodFollowingListScreenActivity) context;
        SwipeRefreshLayout swipe_container = (SwipeRefreshLayout) mood_following_activity.findViewById(R.id.swipe_container);
        swipe_container.setRefreshing(false);
    }
}
