package com.example.moodmasters.Events;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;



import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;
import com.example.moodmasters.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import android.location.Location;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class AddMoodEventConfirmEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        AlterMoodEventActivity activity = (AlterMoodEventActivity) context;

        // Existing setup for other elements (spinner, edit texts)
        Spinner emotions_spinner = activity.findViewById(R.id.alter_mood_emotion_spinner);
        String emotion_string = emotions_spinner.getSelectedItem().toString().trim();
        Emotion.State emotion = Emotion.fromStringToEmotionState(emotion_string);
        MoodList mood_list = (MoodList) model.getBackendObject(BackendObject.State.MOODLIST);

        EditText trigger_text = activity.findViewById(R.id.alter_mood_enter_trigger);
        String trigger_string = trigger_text.getText().toString().trim();

        Spinner social_situations_spinner = activity.findViewById(R.id.alter_mood_situation_spinner);
        String social_situation_string = social_situations_spinner.getSelectedItem().toString();
        SocialSituation.State social_situation = SocialSituation.fromStringToSocialState(social_situation_string);

        EditText reason_text = activity.findViewById(R.id.alter_mood_enter_reason);
        String reason_string = reason_text.getText().toString().trim();

        long epoch_time = System.currentTimeMillis();
        Date date = new Date(epoch_time);
        DateFormat format = new SimpleDateFormat("MMM dd yyyy | HH:mm");
        TimeZone timezone = TimeZone.getDefault();
        format.setTimeZone(TimeZone.getTimeZone(timezone.getDisplayName(false, TimeZone.SHORT)));
        String datetime = format.format(date);

        Participant user = ((Participant) model.getBackendObject(BackendObject.State.USER));

        // Check if the reason is valid
        if (!activity.addDataVerification(reason_string)) {
            reason_text.setError("Must be less than 20 characters and only 3 words");
            return;
        }
        // Check for location permissions
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Initialize FusedLocationProviderClient
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        // Button to get the location and display it
        Button getLocationButton = activity.findViewById(R.id.alter_mood_location_button);
        TextView locationTextView = activity.findViewById(R.id.alter_mood_location_text);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            String locationString = "Lat: " + latitude + ", Lon: " + longitude;
                            locationTextView.setText(locationString);  // Update the TextView

                            // Create the MoodEvent with the location string
                            MoodEvent new_mood_event = new MoodEvent(
                                    datetime, epoch_time, mood_list.getMood(emotion),
                                    reason_string, trigger_string, social_situation, locationString
                            );

                            // Add the MoodEvent to the backend list
                            model.addToBackendList(BackendObject.State.MOODHISTORYLIST, new_mood_event);

                            // Finish the activity
                            ((AlterMoodEventActivity) context).finish();
                        } else {
                            // If getLastLocation() returns null, location updates will be used
                            locationTextView.setText("Location not available, waiting for updates...");
                        }
                    }
                });

        // Button click listener for fetching location and updating
        getLocationButton.setOnClickListener(v -> {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                String locationString = "Lat: " + latitude + ", Lon: " + longitude;
                                locationTextView.setText(locationString);
                            } else {
                                locationTextView.setText("Location unavailable.");
                            }
                        }
                    });
        });
    }

    private void requestLocationUpdates(FusedLocationProviderClient fusedLocationClient, Context context, TextView locationTextView) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);  // High accuracy for location

        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && !locationResult.getLocations().isEmpty()) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        // Update the UI with the location info
                        String locationString = "Lat: " + latitude + ", Lon: " + longitude;
                        locationTextView.setText(locationString);
                    }
                }
            }
        }, Looper.getMainLooper());
    }
}