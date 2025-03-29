package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.MoodEventsMapActivity;
import com.example.moodmasters.Views.ProfileStatisticsActivity;

import java.util.ArrayList;
import java.util.List;

public class ShowProfileStatisticsEvent implements MVCController.MVCEvent {
    private static List<MoodEvent> mood_events;
    private static String username;

    public ShowProfileStatisticsEvent(List<MoodEvent> init_events, String init_username) {
        mood_events = init_events;
        username = init_username;
    }

    public static List<MoodEvent> getMoodEvents() {
        return mood_events;
    }

    public static String getUsername() {
        return username;
    }

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        context.startActivity(new Intent(context, ProfileStatisticsActivity.class));
    }
}
