package com.example.moodmasters.Events;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.FilterMoodFollowingListFragment;
import com.example.moodmasters.Views.FilterMoodHistoryListFragment;

public class MoodFollowingListShowFilterEvent implements MVCController.MVCEvent {
    private String fragment_tag = "Filter MoodFollowingList";
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        new FilterMoodFollowingListFragment().show(((AppCompatActivity) context).getSupportFragmentManager(), fragment_tag);
    }
}
