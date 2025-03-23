package com.example.moodmasters.Objects.ObjectsBackend;

import androidx.annotation.NonNull;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FollowingList extends MVCBackend implements MVCDatabase.Set, MVCDatabase.Update{
    private FirebaseFirestore db;
    private String userId;
    private CollectionReference followingRef;
    private CollectionReference followRequestsRef;
    private List<Participant> following_list;

    public FollowingList(String userId) {
        this.db = FirebaseFirestore.getInstance();
        this.userId = userId;
        this.followingRef = db.collection("participants").document(userId).collection("following");
        this.followRequestsRef = db.collection("participants").document(userId).collection("followRequests");
    }

    // Fetch list of following users
    public void fetchFollowing() {
        followingRef.get().addOnCompleteListener(task -> {
            following_list = new ArrayList<>();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    following_list.add(new Participant(document.getId()));
                }
            }
        });
    }

    @Override
    public void setDatabaseData(MVCDatabase database, MVCModel model){
        System.out.println("EXECUTED");
        followingRef.get().addOnCompleteListener(task -> {
            following_list = new ArrayList<>();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    following_list.add(new Participant(document.getId()));
                }
                updateDatabaseData(database, model);
            }
        });
    }

    @Override
    public void updateDatabaseData(MVCDatabase database, MVCModel model) {
        for (Participant followee: following_list){
            System.out.println("HELLO WORLD " + followee.getUsername());
            followee.setDatabaseData2(database, model);
        }
    }

    public MoodFollowingList getMoodFollowingList(){
        ArrayList<MoodEvent> followees_recent_mood_events = new ArrayList<MoodEvent>();
        for (Participant participant: following_list){
            MoodHistoryList participant_history_list = participant.getMoodHistoryList();
            List<MoodEvent> folowee_history_list = participant_history_list.getList();
            folowee_history_list.sort((a, b) -> Long.compare(a.getEpochTime(), b.getEpochTime()));
            int upper_bound = Math.min(3, folowee_history_list.size());         /*hard coded to 3 for now, need to update later*/
            followees_recent_mood_events.addAll(folowee_history_list.subList(0, upper_bound));
        }
        return new MoodFollowingList(followees_recent_mood_events);
    }

}
