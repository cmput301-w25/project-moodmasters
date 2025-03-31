package com.example.moodmasters.Events.MoodFollowingListScreen;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Objects.ObjectsMisc.FilterMoodEventList;
import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.example.moodmasters.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Class that is responsible for handling the UI interaction of filtering the mood following list in
 * the MoodFollowingListScreen
 * */
public class MoodFollowingListScreenFilterEvent implements MVCController.MVCEvent{
    private View fragment_view;
    public MoodFollowingListScreenFilterEvent(View init_fragment_view){
        fragment_view = init_fragment_view;
    }
    /**
     * Executes code that is necessary for the UI interaction of filtering the mood following list,
     * encompasses checking what filters were checked, then querying the backend to apply each of the
     * filters checked
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        MoodEventList mood_event_list = (MoodEventList) model.getBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
        FilterMoodEventList filter = mood_event_list.getFilter();
        CheckBox recency_box = fragment_view.findViewById(R.id.mood_filter_location_recency_checkbox);
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
        if (recency_box.isChecked()){
            mood_event_list.recencyFilterMoodEventList();
        }
        else{
            mood_event_list.revertRecencyFilterMoodEventList();
        }

        for (Emotion.State emotion: Emotion.State.values()){
            CheckBox emotion_checkbox = emotion_to_checkbox.get(emotion);
            if (emotion_checkbox.isChecked()) {
                mood_event_list.emotionFilterMoodEventList(emotion);
            }
            else if (!emotion_checkbox.isChecked()){
                mood_event_list.revertEmotionFilterMoodEventList(emotion);
            }
        }

        String reason_text = reason_edit_text.getText().toString().toLowerCase().trim();
        String regex = "\\s+";
        List<String> current_reasons = new ArrayList<String>(Arrays.asList(reason_text.split(regex)));
        current_reasons.remove("");         /*possibility on empty edit text field*/
        List<String> reasons_filtered = new ArrayList<String>(filter.getReasonFilter());

        for (String word: reasons_filtered){
            if (!current_reasons.contains(word)){
                mood_event_list.revertWordFilterMoodEventList(word);
            }
        }
        for (String word: current_reasons){
            mood_event_list.wordFilterMoodEventList(word);
        }

        model.notifyViews(BackendObject.State.MOODFOLLOWINGLIST);         /*have to do this manually here but its fine*/

    }
}
