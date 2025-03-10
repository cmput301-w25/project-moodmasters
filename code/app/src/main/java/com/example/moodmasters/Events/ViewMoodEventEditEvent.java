package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.Views.ViewMoodEventActivity;

public class ViewMoodEventEditEvent implements MVCController.MVCEvent {
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        Intent i = new Intent((ViewMoodEventActivity) context, AlterMoodEventActivity.class);
        i.putExtra("Event", "EditMoodEvent");
        context.startActivity(i);
    }
}
