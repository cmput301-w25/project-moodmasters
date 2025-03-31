package com.example.moodmasters.Events.MoodFollowingListScreen;

import android.content.Context;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.MoodFollowingListActivity;

public class MoodFollowingListScreenRefreshEvent implements MVCController.MVCEvent {
    private Context context;
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        this.context = context;
        System.out.println("REFRESH EXECUTE EVENT");
        model.createBackendObject(BackendObject.State.FOLLOWINGLIST);           /*refresh following list and mood following list*/
    }

    public void updateSwipeContainer(){
        MoodFollowingListActivity mood_following_activity = (MoodFollowingListActivity) context;
        SwipeRefreshLayout swipe_container = (SwipeRefreshLayout) mood_following_activity.findViewById(R.id.swipe_container);
        swipe_container.setRefreshing(false);
    }
}
