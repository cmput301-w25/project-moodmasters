package com.example.moodmasters.Events;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.FilterMoodEventMapFragment;
import com.example.moodmasters.Views.FilterMoodFollowingListFragment;

public class MoodEventMapShowFilterEvent implements MVCController.MVCEvent{
    private String fragment_tag = "Filter MoodEventMap";
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        new FilterMoodEventMapFragment().show(((AppCompatActivity) context).getSupportFragmentManager(), fragment_tag);
    }
}
