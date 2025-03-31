package com.example.moodmasters.Events.AlterMoodEventScreen;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenActivity;
import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenLocationActivity;

public class AlterMoodEventScreenSetLocationEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        AlterMoodEventScreenActivity alter_mood_event = (AlterMoodEventScreenActivity) context;
        Intent intent = new Intent(alter_mood_event, AlterMoodEventScreenLocationActivity.class);
        alter_mood_event.startActivityForResult(intent, 1001); // 1001 is a request code for identification
    }
}
