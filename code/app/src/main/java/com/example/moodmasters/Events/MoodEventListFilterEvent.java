package com.example.moodmasters.Events;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Objects.ObjectsMisc.FilterMoodEventList;
import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.FilterMoodEventFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoodEventListFilterEvent implements MVCController.MVCEvent{
    private View fragment_view;
    public MoodEventListFilterEvent(View init_fragment_view){
        fragment_view = init_fragment_view;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        MoodEventList mood_event_list = (MoodEventList) model.getBackendObject(BackendObject.State.MOODHISTORYLIST);
        FilterMoodEventList filter = mood_event_list.getFilter();
        CheckBox recency_box = fragment_view.findViewById(R.id.mood_filter_week_checkbox);
        CheckBox happy_box = fragment_view.findViewById(R.id.mood_filter_happy_checkbox);
        CheckBox sad_box = fragment_view.findViewById(R.id.mood_filter_sad_checkbox);
        CheckBox angry_box = fragment_view.findViewById(R.id.mood_filter_angry_checkbox);
        CheckBox scared_box = fragment_view.findViewById(R.id.mood_filter_scared_checkbox);
        CheckBox surprised_box = fragment_view.findViewById(R.id.mood_filter_surprised_checkbox);
        CheckBox disgusted_box = fragment_view.findViewById(R.id.mood_filter_disgusted_checkbox);
        CheckBox confused_box = fragment_view.findViewById(R.id.mood_filter_confused_checkbox);
        CheckBox ashamed_box = fragment_view.findViewById(R.id.mood_filter_ashamed_checkbox);
        EditText reason_edit_text = fragment_view.findViewById(R.id.mood_filter_reason_text);
        Map<Emotion.State, CheckBox> emotion_to_checkbox = new HashMap<Emotion.State, CheckBox>(Map.of(
                Emotion.State.HAPPY, happy_box,
                Emotion.State.SAD, sad_box,
                Emotion.State.ANGRY, angry_box,
                Emotion.State.SCARED, scared_box,
                Emotion.State.SURPRISED, surprised_box,
                Emotion.State.DISGUSTED, disgusted_box,
                Emotion.State.CONFUSED, confused_box,
                Emotion.State.ASHAMED, ashamed_box
        ));
        if (recency_box.isChecked() && !filter.getRecencyFilter()){         /*checkbox checked and recency filter not applied*/
            mood_event_list.recencyFilterMoodEventList();
        }
        else if (!recency_box.isChecked() && filter.getRecencyFilter()){         /*checkbox not checked and recency filter applied*/
            mood_event_list.revertRecencyFilterMoodEventList();
        }

        List<Emotion.State> emotions_filtered = filter.getEmotionFilter();
        for (Emotion.State emotion: Emotion.State.values()){
            CheckBox emotion_checkbox = emotion_to_checkbox.get(emotion);
            if (emotion_checkbox.isChecked() && !emotions_filtered.contains(emotion)){
                mood_event_list.emotionFilterMoodEventList(emotion);
            }
            else if (!emotion_checkbox.isChecked() && emotions_filtered.contains(emotion)){
                mood_event_list.revertEmotionFilterMoodEventList(emotion);
            }
        }

        String reason_text = reason_edit_text.getText().toString().toLowerCase().trim();
        String regex = "\\s+";
        List<String> current_reasons = new ArrayList<String>(Arrays.asList(reason_text.split(regex)));
        current_reasons.remove("");         /*possibility on empty edit text field*/
        List<String> reasons_filtered = filter.getEditedReasonFilter();
        for (String word: reasons_filtered){
            if (!current_reasons.contains(word)){
                mood_event_list.revertWordFilterMoodEventList(word);
            }
        }
        for (String word: current_reasons){
            if (!reasons_filtered.contains(word)){
                mood_event_list.wordFilterMoodEventList(word);
            }
        }

        model.notifyViews(BackendObject.State.MOODHISTORYLIST);         /*have to do this manually here but its fine*/

    }
}
