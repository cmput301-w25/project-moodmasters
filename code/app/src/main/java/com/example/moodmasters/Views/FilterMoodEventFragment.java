package com.example.moodmasters.Views;

import static com.example.moodmasters.MVC.MVCView.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.moodmasters.Events.MoodEventListFilterEvent;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

import java.util.ArrayList;
import java.util.List;

public class FilterMoodEventFragment extends DialogFragment implements MVCView {
    private List<String> reason_filter_applied;
    private List<Emotion.State> emotion_filter_applied;
    private boolean recency_filter_applied;
    public void initialize(MVCModel model){
        MoodHistoryList mood_history_list = (MoodHistoryList) model.getBackendObject(BackendObject.State.MOODHISTORYLIST);
        recency_filter_applied = mood_history_list.getFilter().getRecencyFilter();
        emotion_filter_applied = mood_history_list.getFilter().getEmotionFilter();
        reason_filter_applied = mood_history_list.getFilter().getEditedReasonFilter();
    }
    public void update(MVCModel model){
        // Not needed
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = getLayoutInflater().inflate(R.layout.mood_filter_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        controller.addBackendView(this, BackendObject.State.MOODHISTORYLIST);

        CheckBox recency_box = view.findViewById(R.id.mood_filter_week_checkbox);
        CheckBox happy_box = view.findViewById(R.id.mood_filter_happy_checkbox);
        CheckBox sad_box = view.findViewById(R.id.mood_filter_sad_checkbox);
        CheckBox angry_box = view.findViewById(R.id.mood_filter_angry_checkbox);
        CheckBox scared_box = view.findViewById(R.id.mood_filter_scared_checkbox);
        CheckBox surprised_box = view.findViewById(R.id.mood_filter_surprised_checkbox);
        CheckBox disgusted_box = view.findViewById(R.id.mood_filter_disgusted_checkbox);
        CheckBox confused_box = view.findViewById(R.id.mood_filter_confused_checkbox);
        CheckBox ashamed_box = view.findViewById(R.id.mood_filter_ashamed_checkbox);
        EditText reason_edit_text = view.findViewById(R.id.mood_filter_reason_text);

        recency_box.setChecked(recency_filter_applied);
        happy_box.setChecked(emotion_filter_applied.contains(Emotion.State.HAPPY));
        sad_box.setChecked(emotion_filter_applied.contains(Emotion.State.SAD));
        angry_box.setChecked(emotion_filter_applied.contains(Emotion.State.ANGRY));
        scared_box.setChecked(emotion_filter_applied.contains(Emotion.State.SCARED));
        surprised_box.setChecked(emotion_filter_applied.contains(Emotion.State.SURPRISED));
        disgusted_box.setChecked(emotion_filter_applied.contains(Emotion.State.DISGUSTED));
        confused_box.setChecked(emotion_filter_applied.contains(Emotion.State.CONFUSED));
        ashamed_box.setChecked(emotion_filter_applied.contains(Emotion.State.ASHAMED));
        reason_edit_text.setText(String.join(" ", reason_filter_applied));

        return builder.setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    controller.execute(new MoodEventListFilterEvent(view), getContext());
                })
                .create();
    }
}
