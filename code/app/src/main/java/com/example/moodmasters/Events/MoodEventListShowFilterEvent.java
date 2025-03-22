package com.example.moodmasters.Events;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.FilterMoodEventFragment;
import com.example.moodmasters.Views.MenuScreenFragment;

public class MoodEventListShowFilterEvent implements MVCController.MVCEvent {
    private static String fragment_tag = "Filter MoodEvents";
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        new FilterMoodEventFragment().show(((AppCompatActivity) context).getSupportFragmentManager(), fragment_tag);
    }
}
