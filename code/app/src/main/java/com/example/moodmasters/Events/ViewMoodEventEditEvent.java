package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;

public class ViewMoodEventEditEvent implements MVCController.MVCEvent {
    public void executeEvent(Context context, MVCModel model, MVCController controller, Intent intent){
        context.startActivity(intent);
    }
}
