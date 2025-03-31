package com.example.moodmasters.Events.CommentViewingScreen;

import android.content.Context;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.MoodFollowingListScreen.MoodFollowingListScreenActivity;

import java.security.InvalidParameterException;

/**
 * implement later if there is time
 * */
public class CommentViewingScreenRefreshEvent implements MVCController.MVCEvent{
    private Context context;
    private String mood_event_list_type;
    public CommentViewingScreenRefreshEvent(String init_mood_event_list_type){
        mood_event_list_type = init_mood_event_list_type;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        this.context = context;
        if (mood_event_list_type.equals("MoodHistoryList")){
            model.fetchDatabaseDataBackendObject(BackendObject.State.USER, (v, w) -> {
                if (w){
                    model.createBackendObject(BackendObject.State.MOODHISTORYLIST);
                    model.notifyViews(BackendObject.State.MOODHISTORYLIST);
                }
                else{
                    Toast.makeText(context, "There Was An Error Fetching From The Database", Toast.LENGTH_SHORT).show();
                }
                updateSwipeContainer();
            });
        }
        else if (mood_event_list_type.equals("MoodFollowingList")){
            model.fetchDatabaseDataBackendObject(BackendObject.State.USER, (v, w) -> {
                if (w){
                    model.createBackendObject(BackendObject.State.MOODHISTORYLIST);
                    model.notifyViews(BackendObject.State.MOODHISTORYLIST);
                }
                else{
                    Toast.makeText(context, "There Was An Error Fetching From The Database", Toast.LENGTH_SHORT).show();
                }
                updateSwipeContainer();
            });
        }
        else{
            throw new InvalidParameterException("CommentViewingScreenRefreshEvent Error");
        }

    }
    public void updateSwipeContainer(){
        MoodFollowingListScreenActivity mood_following_activity = (MoodFollowingListScreenActivity) context;
        SwipeRefreshLayout swipe_container = (SwipeRefreshLayout) mood_following_activity.findViewById(R.id.swipe_container);
        swipe_container.setRefreshing(false);
    }
}
