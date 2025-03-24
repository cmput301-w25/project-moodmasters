package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.FragmentManager;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Objects.ObjectsMisc.FilterMoodEventList;
import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.MenuScreenFragment;
import com.example.moodmasters.Views.MoodFollowingListActivity;
import com.example.moodmasters.Views.MoodHistoryListActivity;
import com.example.moodmasters.Views.UserSearchActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoodFollowingListEvent implements MVCController.MVCEvent{
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        FragmentManager fragment_manager = ((MoodHistoryListActivity) context).getSupportFragmentManager();
        MenuScreenFragment menu_fragment = (MenuScreenFragment) fragment_manager.findFragmentByTag(MoodHistoryListMenuEvent.getFragmentTag());
        menu_fragment.dismiss();
        Intent intent = new Intent((MoodHistoryListActivity) context, MoodFollowingListActivity.class);
        context.startActivity(intent);
    }
}
