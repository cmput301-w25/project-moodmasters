package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.MoodEventsMapActivity;
import com.example.moodmasters.Views.MoodHistoryListActivity;

import java.util.ArrayList;

public class MoodHistoryScreenShowMapEvent implements MVCController.MVCEvent {
    private static ArrayList<MoodEvent> mood_events;
    public MoodHistoryScreenShowMapEvent(ArrayList<MoodEvent> init_events) {
        mood_events = init_events;
    }
    public static ArrayList<MoodEvent> getMoodEvents() {
        return mood_events;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        context.startActivity(new Intent(context, MoodEventsMapActivity.class));
    }
}
