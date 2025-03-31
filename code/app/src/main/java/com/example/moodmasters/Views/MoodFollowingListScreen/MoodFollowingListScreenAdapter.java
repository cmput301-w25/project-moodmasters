package com.example.moodmasters.Views.MoodFollowingListScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

import java.util.ArrayList;
/**
 * Adapter class that will control what is displayed on the MoodFollowingListScreen listview
 * */
public class MoodFollowingListScreenAdapter extends ArrayAdapter<MoodEvent> implements MVCView {
    private Context context;
    public MoodFollowingListScreenAdapter(Context context, ArrayList<MoodEvent> mood_events) {
        super(context, 0, mood_events);
        this.context = context;
        controller.addBackendView(this, BackendObject.State.MOODFOLLOWINGLIST);
    }
    public void initialize(MVCModel model){
        return;
    }

    public void update(MVCModel model){
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.mood_list_layout, parent, false);
        }
        else {
            view = convertView;
        }

        MoodEvent mood_event = getItem(position);
        Mood mood = mood_event.getMood();
        TextView emoticon_view = view.findViewById(R.id.list_layout_emoji_label);
        TextView mood_view = view.findViewById(R.id.list_layout_mood_label);
        TextView datetime_view = view.findViewById(R.id.list_layout_date_time_label);

        datetime_view.setText(mood_event.getDatetime());
        mood_view.setText(mood.getEmotionString());
        emoticon_view.setText(mood.getEmoticon());
        int background_color = ContextCompat.getColor(context, mood.getColor());
        view.setBackgroundColor(background_color);

        return view;
    }
}
