package com.example.moodmasters.Views;

import static com.example.moodmasters.MVC.MVCView.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.moodmasters.Events.MoodEventListFilterEvent;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.R;

public class FilterMoodEventFragment extends DialogFragment implements MVCView {
    public void initialize(MVCModel model){
        // Not needed
    }
    public void update(MVCModel model){
        // Not needed
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = getLayoutInflater().inflate(R.layout.mood_filter_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder.setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    controller.execute(new MoodEventListFilterEvent(), getContext());
                })
                .create();
    }
}
