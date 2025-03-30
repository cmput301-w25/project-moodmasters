package com.example.moodmasters.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.moodmasters.Events.MoodFollowingListFilterEvent;
import com.example.moodmasters.Events.MoodHistoryListFilterEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsBackend.MoodFollowingList;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

import java.util.List;

public class FilterMoodFollowingListFragment extends DialogFragment implements MVCView {
    private List<String> reason_filter_applied;
    private List<Emotion.State> emotion_filter_applied;
    private boolean recency_filter_applied;
    public void initialize(MVCModel model){
        MoodFollowingList mood_following_list = (MoodFollowingList) model.getBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
        recency_filter_applied = mood_following_list.getFilter().getRecencyFilter();
        emotion_filter_applied = mood_following_list.getFilter().getEmotionFilter();
        reason_filter_applied = mood_following_list.getFilter().getReasonFilter();
    }
    public void update(MVCModel model){
        // Not needed
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = getLayoutInflater().inflate(R.layout.mood_filter_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        controller.addBackendView(this, BackendObject.State.MOODFOLLOWINGLIST);

        CheckBox recency_box = view.findViewById(R.id.mood_filter_location_recency_checkbox);
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

        int checkmarkColor = R.color.button_color;
        recency_box.setButtonTintList(ContextCompat.getColorStateList(getContext(), checkmarkColor));
        happy_box.setButtonTintList(ContextCompat.getColorStateList(getContext(), checkmarkColor));
        sad_box.setButtonTintList(ContextCompat.getColorStateList(getContext(), checkmarkColor));
        angry_box.setButtonTintList(ContextCompat.getColorStateList(getContext(), checkmarkColor));
        scared_box.setButtonTintList(ContextCompat.getColorStateList(getContext(), checkmarkColor));
        surprised_box.setButtonTintList(ContextCompat.getColorStateList(getContext(), checkmarkColor));
        disgusted_box.setButtonTintList(ContextCompat.getColorStateList(getContext(), checkmarkColor));
        confused_box.setButtonTintList(ContextCompat.getColorStateList(getContext(), checkmarkColor));
        ashamed_box.setButtonTintList(ContextCompat.getColorStateList(getContext(), checkmarkColor));

        AlertDialog dialog = builder.setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Filter", (dialogInterface, which) -> {
                    controller.execute(new MoodFollowingListFilterEvent(view), getContext());
                })
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

            positive.setTextColor(ContextCompat.getColor(getContext(), R.color.button_color));
            negative.setTextColor(ContextCompat.getColor(getContext(), R.color.button_color));
        });

        return dialog;
    }
}
