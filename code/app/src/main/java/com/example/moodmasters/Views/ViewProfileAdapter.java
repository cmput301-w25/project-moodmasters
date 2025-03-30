package com.example.moodmasters.Views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewProfileAdapter extends ArrayAdapter<MoodEvent> {
    private Context context;
    private FirebaseFirestore db;
    private MVCDatabase database; // Assuming you need an MVCDatabase instance

    public ViewProfileAdapter(Context context) {
        super(context, 0, new ArrayList<>());
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
        this.database = new MVCDatabase(); // Initialize MVCDatabase if needed
    }

    public void fetchMoodEvents(String username) {
        db.collection("participants")
                .document(username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        System.out.println("GOT DOC SNAP " + username);

                        if (document != null && document.exists()) {
                            System.out.println("Data fetched for: " + username);
                            List<Map<String, Object>> rawMoodHistory = (List<Map<String, Object>>) document.get("list");

                            if (rawMoodHistory != null) {
                                List<HashMap<String, Object>> convertedList = new ArrayList<>();
                                for (Map<String, Object> map : rawMoodHistory) {
                                    convertedList.add(new HashMap<>(map));
                                }
                                updateMoodEvents(convertedList);
                            } else {
                                System.out.println("No mood events found for " + username);
                            }
                        } else {
                            System.out.println("No such document for " + username);
                        }
                    } else {
                        System.err.println("Error fetching document: " + task.getException());
                    }
                });
    }

    private void updateMoodEvents(List<HashMap<String, Object>> rawMoodHistory) {
        clear(); // Clears the existing mood events in the adapter
        List<MoodEvent> moodEvents = new ArrayList<>();
        for (HashMap<String, Object> rawMood : rawMoodHistory) {
            try {
                Object isPublicObj = rawMood.get("isPublic");
                boolean isPublic = isPublicObj instanceof Boolean && (Boolean) isPublicObj;

                if (isPublic) {
                    MoodEvent moodEvent = new MoodEvent(rawMood);
                    moodEvents.add(moodEvent);
                }
            } catch (Exception e) {
                System.err.println("Error parsing MoodEvent: " + e.getMessage());
            }
        }
        addAll(moodEvents);
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

