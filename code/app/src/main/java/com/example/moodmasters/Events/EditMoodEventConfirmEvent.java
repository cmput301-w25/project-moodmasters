package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.Views.MoodEventViewingActivity;

public class EditMoodEventConfirmEvent implements MVCController.MVCEvent {
    private MoodEvent mood_event;
    private int position;
    public EditMoodEventConfirmEvent(MoodEvent init_mood_event, int init_position){
        mood_event = init_mood_event;
        position = init_position;
    }
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

        Participant user = ((Participant) model.getBackendObject(BackendObject.State.USER));

        if (!activity.addDataVerification(reason_string)){
            reason_text.setError("Must be less than 20 characters and only 3 words");
            return;
        }
        MoodEvent new_mood_event = new MoodEvent(mood_event.getDatetime(), mood_event.getEpochTime(), mood_list.getMood(emotion),
                                                    reason_string, trigger_string, social_situation);
        model.replaceObjectBackendList(BackendObject.State.MOODHISTORYLIST, position, new_mood_event);
        ((AlterMoodEventActivity) context).finish();
    }
}
