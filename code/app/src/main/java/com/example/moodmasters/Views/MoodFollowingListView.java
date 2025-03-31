package com.example.moodmasters.Views;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.moodmasters.Events.MoodFollowingListScreen.MoodFollowingListScreenClickMoodEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

import java.util.ArrayList;
import java.util.List;

public class MoodFollowingListView implements MVCView {
    private ListView mood_following_list;
    private MoodFollowingArrayAdapter mood_following_adapter;
    private Context context;
    private boolean isSorted = false; // Track if the list is sorted
    private List<MoodEvent> original_list; // Store original order
    public MoodFollowingListView(Context init_context){
        context = init_context;
        mood_following_list = ((MoodFollowingListActivity) context).findViewById(R.id.mood_following_list);
        controller.addBackendView(this, BackendObject.State.MOODFOLLOWINGLIST);
        setListElementClicker();
    }
    public void setListElementClicker(){
        mood_following_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.execute(new MoodFollowingListScreenClickMoodEvent(mood_following_adapter.getItem(position), position), context);
            }
        });
    }

    public void initialize(MVCModel model){
        List<MoodEvent> mood_list = model.getBackendList(BackendObject.State.MOODFOLLOWINGLIST);
        original_list = new ArrayList<>(mood_list);
        mood_following_adapter = new MoodFollowingArrayAdapter(context, model.getBackendList(BackendObject.State.MOODFOLLOWINGLIST));
        mood_following_list.setAdapter(mood_following_adapter);
    }

    public void update(MVCModel model){
        List<MoodEvent> mood_list = model.getBackendList(BackendObject.State.MOODFOLLOWINGLIST);
        original_list = new ArrayList<>(mood_list);
    }

    public void toggleSort() {
        List<MoodEvent> moodList = new ArrayList<>();
        for (int i = 0; i < mood_following_adapter.getCount(); i++) {
            moodList.add(mood_following_adapter.getItem(i));
        }

        if (isSorted) {
            // Sort by epoch time (most recent first)
            moodList.sort((a, b) -> Long.compare(a.getEpochTime(), b.getEpochTime()));
        } else {
            // Sort by epoch time (most recent first)
            moodList.sort((a, b) -> Long.compare(b.getEpochTime(), a.getEpochTime()));
        }

        isSorted = !isSorted; // Toggle sort state

        // Update adapter and refresh UI
        mood_following_adapter.clear();
        mood_following_adapter.addAll(moodList);
        mood_following_adapter.notifyDataSetChanged();
    }
    public MoodFollowingArrayAdapter getMoodFollowingArrayAdapter(){
        return mood_following_adapter;
    }
}
