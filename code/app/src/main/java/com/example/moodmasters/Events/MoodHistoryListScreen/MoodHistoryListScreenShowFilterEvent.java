package com.example.moodmasters.Events.MoodHistoryListScreen;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodHistoryListScreen.MoodHistoryListScreenFilterFragment;

public class MoodHistoryListScreenShowFilterEvent implements MVCController.MVCEvent {
    private String fragment_tag = "Filter MoodHistoryList";
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        new MoodHistoryListScreenFilterFragment().show(((AppCompatActivity) context).getSupportFragmentManager(), fragment_tag);
    }
}
