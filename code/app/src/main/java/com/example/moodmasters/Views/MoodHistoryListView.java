package com.example.moodmasters.Views;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.moodmasters.Events.MoodHistoryListClickMoodEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

import java.util.ArrayList;
import java.util.List;

public class MoodHistoryListView implements MVCView {
    private ListView mood_history_list;
    private MoodHistoryArrayAdapter mood_history_adapter;
    private Context context;
    private boolean isSorted = false; // Track if the list is sorted
    private List<MoodEvent> originalList; // Store original order
    public MoodHistoryListView(Context init_context){
        context = init_context;
        mood_history_list = ((MoodHistoryListActivity) context).findViewById(R.id.mood_following_list);
        controller.addBackendView(this, BackendObject.State.MOODHISTORYLIST);
        setListElementClicker();
    }
    public void setListElementClicker(){
        mood_history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.execute(new MoodHistoryListClickMoodEvent(mood_history_adapter.getItem(position), position), context);
            }
        });
    }

    public void initialize(MVCModel model){
        List<MoodEvent> moodList = model.getBackendList(BackendObject.State.MOODHISTORYLIST);
        originalList = new ArrayList<>(moodList);
        mood_history_adapter = new MoodHistoryArrayAdapter(context, model.getBackendList(BackendObject.State.MOODHISTORYLIST));
        mood_history_list.setAdapter(mood_history_adapter);
    }

    public void update(MVCModel model){
        return;
    }

    public void toggleSort() {
        List<MoodEvent> moodList = new ArrayList<>();
        for (int i = 0; i < mood_history_adapter.getCount(); i++) {
            moodList.add(mood_history_adapter.getItem(i));
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
        mood_history_adapter.clear();
        mood_history_adapter.addAll(moodList);
        mood_history_adapter.notifyDataSetChanged();
    }

    public MoodHistoryArrayAdapter getMoodHistoryArrayAdapter(){
        return mood_history_adapter;
    }
}
