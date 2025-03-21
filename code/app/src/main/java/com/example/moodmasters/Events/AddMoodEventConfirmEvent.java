package com.example.moodmasters.Events;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.Views.MoodHistoryListActivity;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AddMoodEventConfirmEvent implements MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel backend, MVCController controller) {
        AlterMoodEventActivity activity = (AlterMoodEventActivity) context;

        // Retrieve and check the emotion spinner.
        Spinner emotions_spinner = activity.findViewById(R.id.alter_mood_emotion_spinner);
        if (emotions_spinner == null) {
            // Log an error if needed.
            return;
        }
        String emotion_string = emotions_spinner.getSelectedItem().toString().trim();
        Emotion.State emotion = Emotion.fromStringToEmotionState(emotion_string);

        // Retrieve the MoodList from the backend.
        MoodList mood_list = (MoodList) backend.getBackendObject(BackendObject.State.MOODLIST);
        if (mood_list == null) {
            // Log error: MoodList backend object is missing.
            return;
        }

        // Get trigger input.
        EditText trigger_text = activity.findViewById(R.id.alter_mood_enter_trigger);
        String trigger_string = trigger_text.getText().toString().trim();

        // Retrieve and check the social situation spinner.
        Spinner social_situations_spinner = activity.findViewById(R.id.alter_mood_situation_spinner);
        if (social_situations_spinner == null) {
            return;
        }
        String social_situation_string = social_situations_spinner.getSelectedItem().toString();
        SocialSituation.State social_situation = SocialSituation.fromStringToSocialState(social_situation_string);

        // Get reason input.
        EditText reason_text = activity.findViewById(R.id.alter_mood_enter_reason);
        String reason_string = reason_text.getText().toString().trim();

        // Create a formatted date/time string.
        long epoch_time = System.currentTimeMillis();
        Date date = new Date(epoch_time);
        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat("MMM dd yyyy | HH:mm");
        TimeZone timezone = TimeZone.getDefault();
        format.setTimeZone(TimeZone.getTimeZone(timezone.getDisplayName(false, TimeZone.SHORT)));
        String datetime = format.format(date);

        // Retrieve the current user from the backend.
        Participant user = (Participant) backend.getBackendObject(BackendObject.State.USER);
        if (user == null) {
            // Log error: User is null.
            return;
        }

        // Validate reason input.
        if (!activity.addDataVerification(reason_string)) {
            reason_text.setError("Must be less than 20 characters and only 3 words");
            return;
        }

        // Create a new mood event.
        MoodEvent new_mood_event = new MoodEvent(datetime, epoch_time, mood_list.getMood(emotion), user, reason_string, trigger_string, social_situation);

        // Add the new mood event to the MOODHISTORYLIST backend object.
        backend.addToBackendList(BackendObject.State.MOODHISTORYLIST, new_mood_event);

        // Navigate explicitly to MoodHistoryListActivity and finish the current activity.
        Intent intent = new Intent(context, MoodHistoryListActivity.class);
        context.startActivity(intent);
        activity.finish();
    }
}
