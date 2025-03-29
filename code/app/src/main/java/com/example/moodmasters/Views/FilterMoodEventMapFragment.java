package com.example.moodmasters.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.moodmasters.Events.MoodEventMapFilterEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Objects.ObjectsMisc.FilterMoodEventMap;
import com.example.moodmasters.Objects.ObjectsMisc.MoodMap;
import com.example.moodmasters.R;

public class FilterMoodEventMapFragment extends DialogFragment implements MVCView {
    private FilterMoodEventMap filter;
    private boolean location_status;
    public FilterMoodEventMapFragment(boolean init_location_status){
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

        return builder.setView(view)
                .setNegativeButton("Cancel",  (dialog, which) -> {
                    controller.removeBackendView(FilterMoodEventMapFragment.this);          /*TODO: MVCEvent for this*/
                })
                .setPositiveButton("Add", (dialog, which) -> {
                    controller.execute(new MoodEventMapFilterEvent(view, FilterMoodEventMapFragment.this), getContext());
                })
                .create();
    }
}
