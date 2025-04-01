package com.example.moodmasters.Events.MoodEventMapScreen;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Objects.ObjectsBackend.MoodMap;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.MoodEventMapScreen.MoodEventMapScreenFilterFragment;
/**
 * Class that is responsible for handling the UI interaction of filtering the mood events currently
 * on the MoodEventMapScreen
 * */
public class MoodEventMapScreenFilterEvent implements MVCController.MVCEvent{
    private final View fragment_view;
    private final MoodEventMapScreenFilterFragment dialog_fragment;
    public MoodEventMapScreenFilterEvent(View init_fragment_view, MoodEventMapScreenFilterFragment init_dialog_fragment){
        fragment_view = init_fragment_view;
        dialog_fragment = init_dialog_fragment;
    }
    /**
     * Executes code that is necessary for the UI interaction for applying filters to the current mood
     * events on the MoodEventMap, encompasses just checking whether each filter checkbox is checked,
     * if it is apply the filter if not revert the filter
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        MoodMap mood_map = (MoodMap) model.getBackendObject(BackendObject.State.MOODMAP);
        CheckBox recency_location_box = fragment_view.findViewById(R.id.mood_filter_location_recency_checkbox);
        CheckBox mood_history_box = fragment_view.findViewById(R.id.mood_filter_mood_history_checkbox);
        CheckBox mood_following_box = fragment_view.findViewById(R.id.mood_filter_mood_following_checkbox);
        if (recency_location_box.isChecked()){
            mood_map.recencyLocationFilterMoodEventList();
        }
        else{
            mood_map.revertRecencyLocationFilterMoodEventList();
        }

        if (mood_history_box.isChecked()){
            mood_map.moodHistoryFilterMoodEventList();
        }
        else{
            mood_map.revertMoodHistoryFilterMoodEventList();
        }

        if (mood_following_box.isChecked()){
            mood_map.moodFollowingFilterMoodEventList();
        }
        else{
            mood_map.revertMoodFollowingFilterMoodEventList();
        }

        model.notifyViews(BackendObject.State.MOODMAP);         /*have to do this manually here but its fine*/
        model.removeView(dialog_fragment);

    }
}
