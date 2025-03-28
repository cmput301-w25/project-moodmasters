package com.example.moodmasters.Events;

import static android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.MoodEventsMapActivity;
import com.example.moodmasters.Views.MoodHistoryListActivity;

import java.util.ArrayList;
import java.util.Objects;

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
        ConnectivityManager connectivityManager = getSystemService(context, ConnectivityManager.class);
        Network currentNetwork = connectivityManager.getActiveNetwork();
        if (currentNetwork == null || !Objects.requireNonNull(connectivityManager.getNetworkCapabilities(currentNetwork)).hasCapability(NET_CAPABILITY_VALIDATED)) {
            Toast.makeText(context, "You're offline! Please connect to the internet", Toast.LENGTH_SHORT).show();
            return;
        }

        context.startActivity(new Intent(context, MoodEventsMapActivity.class));
    }
}
