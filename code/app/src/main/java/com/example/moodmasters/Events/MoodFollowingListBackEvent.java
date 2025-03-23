package com.example.moodmasters.Events;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.MoodFollowingListActivity;

public class MoodFollowingListBackEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.removeBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
        MoodFollowingListActivity mood_following_list = (MoodFollowingListActivity) context;
        model.removeView(mood_following_list);
        model.removeView(mood_following_list.getMoodFollowingListView());
        model.removeView(mood_following_list.getMoodFollowingListView().getMoodFollowingArrayAdapter());
        ((MoodFollowingListActivity) context).finish();
    }
}
