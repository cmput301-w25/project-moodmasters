package com.example.moodmasters.Events;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

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
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
public class AddMoodEventConfirmEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        AlterMoodEventActivity activity = (AlterMoodEventActivity) context;

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

        CheckBox check_public = activity.findViewById(R.id.alter_mood_public_checkbox);
        boolean is_public = check_public.isChecked();

        long epoch_time = System.currentTimeMillis();           /* Epoch time will be the time stored on the database for easy conversion to different time zones */
        Date date = new Date(epoch_time);
        DateFormat format = new SimpleDateFormat("MMM dd yyyy | HH:mm");
        TimeZone timezone = TimeZone.getDefault();
        format.setTimeZone(TimeZone.getTimeZone(timezone.getDisplayName(false, TimeZone.SHORT)));
        String datetime = format.format(date);

        Participant user = ((Participant) model.getBackendObject(BackendObject.State.USER));

        if (!activity.addDataVerification(reason_string)){
            reason_text.setError("Must be less than 20 characters and only 3 words");
            return;
        }

        // mock location for testing
        LatLng location = new LatLng(0, 0);

        MoodEvent new_mood_event = new MoodEvent(datetime, epoch_time, mood_list.getMood(emotion),
                is_public, reason_string, trigger_string, social_situation, location,
                ((Participant) model.getBackendObject(BackendObject.State.USER)).getUsername());
        model.addToBackendList(BackendObject.State.MOODHISTORYLIST, new_mood_event);
        ((AlterMoodEventActivity) context).finish();

    }
}
