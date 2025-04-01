package com.example.moodmasters.Events.MoodFollowingListScreen;

import static android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.MoodFollowingListScreen.MoodFollowingListScreenActivity;

import java.util.Objects;

/**
 * Class that is responsible for handling the UI interaction of bringing up the MoodFollowingListScreen
 * */
public class MoodFollowingListScreenShowEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of bringing up the MoodFollowingListScreen,
     * encompasses just checking internet connectivity and starting the activity
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ConnectivityManager connectivityManager = getSystemService(context, ConnectivityManager.class);
        Network currentNetwork = connectivityManager.getActiveNetwork();
        if (currentNetwork == null || !Objects.requireNonNull(connectivityManager.getNetworkCapabilities(currentNetwork)).hasCapability(NET_CAPABILITY_VALIDATED)) {
            Toast.makeText(context, "You're offline! Please connect to the internet", Toast.LENGTH_SHORT).show();
            return;
        }
        context.startActivity(new Intent(context, MoodFollowingListScreenActivity.class));
    }
}
