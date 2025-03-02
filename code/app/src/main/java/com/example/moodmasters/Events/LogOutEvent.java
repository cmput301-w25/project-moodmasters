package com.example.moodmasters.Events;

import static com.example.moodmasters.Events.MoodHistoryListMenuEvent.fragment_tag;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.MenuScreenFragment;
import com.example.moodmasters.MoodHistoryListActivity;
import com.example.moodmasters.R;

public class LogOutEvent implements MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel backend, MVCController controller) {
        FragmentManager fragment_manager = ((MoodHistoryListActivity) context).getSupportFragmentManager();
        MenuScreenFragment menu_fragment = (MenuScreenFragment) fragment_manager.findFragmentByTag(MoodHistoryListMenuEvent.fragment_tag);
        menu_fragment.dismiss();
        ((MoodHistoryListActivity) context).finish();
    }
}
