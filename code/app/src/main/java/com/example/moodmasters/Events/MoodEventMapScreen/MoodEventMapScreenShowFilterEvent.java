package com.example.moodmasters.Events.MoodEventMapScreen;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodEventMapScreen.MoodEventMapScreenFilterFragment;

public class MoodEventMapScreenShowFilterEvent implements MVCController.MVCEvent{
    private boolean location_status;
    public MoodEventMapScreenShowFilterEvent(boolean init_location_status){
        location_status = init_location_status;
    }
    private String fragment_tag = "Filter MoodEventMap";
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        new MoodEventMapScreenFilterFragment(location_status).show(((AppCompatActivity) context).getSupportFragmentManager(), fragment_tag);
    }
}
