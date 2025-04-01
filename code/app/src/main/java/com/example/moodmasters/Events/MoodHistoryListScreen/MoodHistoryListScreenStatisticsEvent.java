package com.example.moodmasters.Events.MoodHistoryListScreen;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.ProfileStatisticsScreen.ProfileStatisticsScreenActivity;

import java.util.List;
/**
 * Class that is responsible for handling the UI interaction of bringing up the ProfileStatisticsScreen
 * from the MoodHistoryList
 * */
public class MoodHistoryListScreenStatisticsEvent implements MVCController.MVCEvent {
    private static List<MoodEvent> mood_events;
    private static String username;

    public MoodHistoryListScreenStatisticsEvent(List<MoodEvent> init_events, String init_username) {
        mood_events = init_events;
        username = init_username;
    }

    public static List<MoodEvent> getMoodEvents() {
        return mood_events;
    }

    public static String getUsername() {
        return username;
    }
    /**
     * Executes code that is necessary for the UI interaction of bringing up the ProfileStatisticsScreen
     * from the MoodHistoryList, just checks if there are any mood events and
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        if (mood_events.isEmpty()) {
            Toast.makeText(context, "No moods to show statistics on!", Toast.LENGTH_SHORT).show();
            return;
        }
        context.startActivity(new Intent(context, ProfileStatisticsScreenActivity.class));
    }
}
