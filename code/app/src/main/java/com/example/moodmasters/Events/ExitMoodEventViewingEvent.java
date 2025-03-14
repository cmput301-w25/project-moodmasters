package com.example.moodmasters.Events;

import static com.example.moodmasters.MVC.MVCView.controller;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.MenuScreenFragment;
import com.example.moodmasters.Views.MoodEventViewingActivity;
import com.example.moodmasters.Views.MoodHistoryListActivity;

public class ExitMoodEventViewingEvent implements MVCController.MVCEvent {

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ((MoodEventViewingActivity) context).finish();
    }
}
