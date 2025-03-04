package com.example.moodmasters.Events;

import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCEvent;
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
public class AddMoodEventConfirmEvent implements MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel backend, MVCController controller) {
        AlterMoodEventActivity activity = (AlterMoodEventActivity) context;

        Spinner emotions_spinner = activity.findViewById(R.id.alter_mood_emotion_spinner);
        String emotion_string = emotions_spinner.getSelectedItem().toString().trim();
        Emotion.State emotion = Emotion.fromStringToEmotionState(emotion_string);
        MoodList mood_list = (MoodList) backend.getBackendObject(BackendObject.State.MOODLIST);

        EditText trigger_text = activity.findViewById(R.id.alter_mood_enter_trigger);
        String trigger_string = trigger_text.getText().toString().trim();

        Spinner social_situations_spinner = activity.findViewById(R.id.alter_mood_situation_spinner);
        String social_situation_string = social_situations_spinner.getSelectedItem().toString();
        SocialSituation.State social_situation = SocialSituation.fromStringToSocialState(social_situation_string);

        EditText reason_text = activity.findViewById(R.id.alter_mood_enter_reason);
        String reason_string = reason_text.getText().toString().trim();

        long epoch_time = System.currentTimeMillis();           /* Epoch time will be the time stored on the database for easy conversion to different time zones */
        Date date = new Date(epoch_time);
        DateFormat format = new SimpleDateFormat("MMM dd yyyy | HH:mm");
        TimeZone timezone = TimeZone.getDefault();
        format.setTimeZone(TimeZone.getTimeZone(timezone.getDisplayName(false, TimeZone.SHORT)));
        String datetime = format.format(date);

        Participant user = ((Participant) backend.getBackendObject(BackendObject.State.USER));

        if (!activity.addDataVerification(reason_string)){
            reason_text.setError("Must be less than 20 characters and only 3 words");
            return;
        }

        MoodEvent new_mood_event = new MoodEvent(datetime, epoch_time, mood_list.getMood(emotion), user, reason_string, trigger_string, social_situation);
        backend.addToBackendList(BackendObject.State.MOODHISTORYLIST, new_mood_event);
        ((AlterMoodEventActivity) context).finish();

    }
}
