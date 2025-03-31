package com.example.moodmasters.Views.ViewProfileScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewProfileScreenAdapter extends ArrayAdapter<MoodEvent> implements MVCView {
    private Context context;
    private List<MoodEvent> target_mood_events;

    public ViewProfileScreenAdapter(Context context, List<MoodEvent> init_target_mood_events) {
        super(context, 0, init_target_mood_events);
        this.target_mood_events = init_target_mood_events;
        this.context = context;
        controller.addBackendView(this, BackendObject.State.USERSEARCH);
    }
    @Override
    public void update(MVCModel model) {
        target_mood_events.sort((a, b) -> Long.compare(a.getEpochTime(), b.getEpochTime()));
        ArrayList<MoodEvent> removables = new ArrayList<MoodEvent>();
        int amount = 0;
        for (MoodEvent mood_event: target_mood_events){
            if (mood_event.getIsPublic() && amount < 3){
                amount++;
                continue;
            }
            removables.add(mood_event);
        }
        target_mood_events.removeAll(removables);
        notifyDataSetChanged();
    }

    @Override
    public void initialize(MVCModel model) {

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

