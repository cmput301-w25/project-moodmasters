package com.example.moodmasters.Events.MoodFollowingListScreen;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodFollowingListScreen.MoodFollowingListScreenFilterFragment;

public class MoodFollowingListScreenShowFilterEvent implements MVCController.MVCEvent {
    private String fragment_tag = "Filter MoodFollowingList";
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        new MoodFollowingListScreenFilterFragment().show(((AppCompatActivity) context).getSupportFragmentManager(), fragment_tag);
    }
}
