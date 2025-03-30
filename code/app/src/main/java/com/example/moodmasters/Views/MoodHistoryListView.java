package com.example.moodmasters.Views;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.example.moodmasters.Events.MoodHistoryListClickMoodEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.firebase.firestore.FirebaseFirestore;

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
        //  FOLLOWERS / FOLLOWING SETUP
        LinearLayout followersContainer = ((MoodHistoryListActivity) context).findViewById(R.id.followers_container);
        LinearLayout followingContainer = ((MoodHistoryListActivity) context).findViewById(R.id.following_container);
        TextView followersNumber = ((MoodHistoryListActivity) context).findViewById(R.id.followers_number);
        TextView followingNumber = ((MoodHistoryListActivity) context).findViewById(R.id.following_number);
        TextView usernameLabel = ((MoodHistoryListActivity) context).findViewById(R.id.user_mood_history_label);

        String username = LoginScreenOkEvent.getUsername();
        usernameLabel.setText(username);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("participants").document(username).collection("followers")
                .get().addOnSuccessListener(snapshot -> followersNumber.setText(String.valueOf(snapshot.size())));

        db.collection("participants").document(username).collection("following")
                .get().addOnSuccessListener(snapshot -> followingNumber.setText(String.valueOf(snapshot.size())));

        followersContainer.setOnClickListener(v -> openFollowList("followers", username));
        followingContainer.setOnClickListener(v -> openFollowList("following", username));
        controller.addBackendView(this, BackendObject.State.MOODHISTORYLIST);
        setListElementClicker();
    }

    public void setMoodEvents(List<MoodEvent> moodEvents) {
        originalList = new ArrayList<>(moodEvents); // Update the original list
        mood_history_adapter.clear();
        mood_history_adapter.addAll(moodEvents);
        mood_history_adapter.notifyDataSetChanged();
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

    private void openFollowList(String listType, String username) {
        Intent intent;

        if (listType.equals("followers")) {
            intent = new Intent(context, FollowersListActivity.class);
        } else {
            intent = new Intent(context, FollowListActivity.class);
        }

        intent.putExtra("username", username);
        context.startActivity(intent);
    }

    public void refreshFollowerCounts() {
        String username = LoginScreenOkEvent.getUsername();
        TextView followersNumber = ((MoodHistoryListActivity) context).findViewById(R.id.followers_number);
        TextView followingNumber = ((MoodHistoryListActivity) context).findViewById(R.id.following_number);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("participants").document(username).collection("followers")
                .get().addOnSuccessListener(snapshot -> followersNumber.setText(String.valueOf(snapshot.size())));

        db.collection("participants").document(username).collection("following")
                .get().addOnSuccessListener(snapshot -> followingNumber.setText(String.valueOf(snapshot.size())));
    }
}
