package com.example.moodmasters.Views;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

public class MoodHistoryListView implements MVCView {
    private ListView mood_history_list;
    private MoodHistoryArrayAdapter mood_history_adapter;
    private Context context;
    public MoodHistoryListView(Context init_context){
        context = init_context;
        mood_history_list = ((MoodHistoryListActivity) context).findViewById(R.id.mood_history_list);
        controller.addBackendView(this, BackendObject.State.MOODHISTORYLIST);
    }
    public void setListElementClicker(){
        mood_history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Add code to transition to viewing mood event
            }
        });
    }

    public void initialize(MVCModel model){
        mood_history_adapter = new MoodHistoryArrayAdapter(context, model.getBackendList(BackendObject.State.MOODHISTORYLIST));
        mood_history_list.setAdapter(mood_history_adapter);
    }

    public void update(MVCModel model){
        return;
    }
}
