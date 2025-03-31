package com.example.moodmasters.Views.MoodEventMapScreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.moodmasters.Events.MoodEventMapScreen.MoodEventMapScreenFilterEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Objects.ObjectsMisc.FilterMoodEventMap;
import com.example.moodmasters.Objects.ObjectsBackend.MoodMap;
import com.example.moodmasters.R;
/**
 * Class that is responsible for displaying a dialog fragment that allows us to filter our
 * mood event map
 * */
public class MoodEventMapScreenFilterFragment extends DialogFragment implements MVCView {
    private FilterMoodEventMap filter;
    private boolean location_status;
    public MoodEventMapScreenFilterFragment(boolean init_location_status){
        super();
        location_status = init_location_status;
    }
    public void initialize(MVCModel model){
        filter = ((MoodMap) model.getBackendObject(BackendObject.State.MOODMAP)).getFilter();
    }
    public void update(MVCModel model){
        // Not needed
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = getLayoutInflater().inflate(R.layout.map_filter_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        controller.addBackendView(this, BackendObject.State.MOODMAP);
        CheckBox mood_history_checkbox = view.findViewById(R.id.mood_filter_mood_history_checkbox);
        CheckBox mood_following_checkbox = view.findViewById(R.id.mood_filter_mood_following_checkbox);
        CheckBox recency_location_checkbox = view.findViewById(R.id.mood_filter_location_recency_checkbox);

        mood_history_checkbox.setChecked(filter.isMoodHistoryFilter());
        mood_following_checkbox.setChecked(filter.isMoodFollowingFilter());
        if (location_status){
            recency_location_checkbox.setChecked(filter.isLocationRecencyFilter());
        }
        else{
            ((ViewGroup) view).removeView(recency_location_checkbox);
        }


        AlertDialog dialog = builder
                .setView(view)
                .setNegativeButton("Cancel", (dialogInterface, which) -> {
                    controller.removeBackendView(MoodEventMapScreenFilterFragment.this);
                })
                .setPositiveButton("Filter", (dialogInterface, which) -> {
                    controller.execute(new MoodEventMapScreenFilterEvent(view, MoodEventMapScreenFilterFragment.this), getContext());
                })
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

            // Change the button colors to @color/button_color
            int color = ContextCompat.getColor(getContext(), R.color.button_color);
            positive.setTextColor(color);
            negative.setTextColor(color);
        });

        return dialog;
    }
}
