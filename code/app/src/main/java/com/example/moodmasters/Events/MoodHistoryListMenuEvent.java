package com.example.moodmasters.Events;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MenuScreenFragment;

public class MoodHistoryListMenuEvent implements MVCController.MVCEvent {
    public static String fragment_tag = "Menu Options";
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        new MenuScreenFragment().show(((AppCompatActivity) context).getSupportFragmentManager(), fragment_tag);
    }
}
