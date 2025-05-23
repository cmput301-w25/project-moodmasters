package com.example.moodmasters.Views.MoodHistoryListScreen;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moodmasters.Events.LoginSignupScreen.LoginSignupScreenOkEvent;
import com.example.moodmasters.Events.MoodHistoryListScreen.MoodHistoryListScreenClickMoodEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.FollowersListScreen.FollowersListScreenActivity;
import com.example.moodmasters.Views.FollowingListScreen.FollowingListScreenActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MoodHistoryListScreenListView implements MVCView {
    private ListView mood_history_list;
    private MoodHistoryListScreenAdapter mood_history_adapter;
    private Context context;
    private boolean isSorted = false; // Track if the list is sorted
    public MoodHistoryListScreenListView(Context init_context){
        context = init_context;
        mood_history_list = ((MoodHistoryListScreenActivity) context).findViewById(R.id.mood_following_list);

        TextView usernameLabel = ((MoodHistoryListScreenActivity) context).findViewById(R.id.user_mood_history_label);

        String username = LoginSignupScreenOkEvent.getUsername();
        usernameLabel.setText(username);

        controller.addBackendView(this, BackendObject.State.MOODHISTORYLIST);
        setListElementClicker();
    }

    public void initialize(MVCModel model){
        List<MoodEvent> moodList = model.getBackendList(BackendObject.State.MOODHISTORYLIST);
        mood_history_adapter = new MoodHistoryListScreenAdapter(context, model.getBackendList(BackendObject.State.MOODHISTORYLIST));
        mood_history_list.setAdapter(mood_history_adapter);
    }

    public void update(MVCModel model){
        return;
    }

    public void setListElementClicker(){
        mood_history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.execute(new MoodHistoryListScreenClickMoodEvent(mood_history_adapter.getItem(position), position), context);
            }
        });
    }

    public void toggleSort() {
        List<MoodEvent> moodList = new ArrayList<>();
        for (int i = 0; i < mood_history_adapter.getCount(); i++) {
            moodList.add(mood_history_adapter.getItem(i));
        }

        if (isSorted) {
            // Sort by epoch time (most recent first)
            moodList.sort((a, b) -> Long.compare(a.getEpochTime(), b.getEpochTime()));
        }
        else {
            // Sort by epoch time (most recent first)
            moodList.sort((a, b) -> Long.compare(b.getEpochTime(), a.getEpochTime()));
        }

        isSorted = !isSorted; // Toggle sort state

        // Update adapter and refresh UI
        mood_history_adapter.clear();
        mood_history_adapter.addAll(moodList);
        mood_history_adapter.notifyDataSetChanged();
    }

    public MoodHistoryListScreenAdapter getMoodHistoryArrayAdapter(){
        return mood_history_adapter;
    }

}
