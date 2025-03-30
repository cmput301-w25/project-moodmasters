package com.example.moodmasters.Objects.ObjectsBackend;

import androidx.annotation.NonNull;

import com.example.moodmasters.Events.MoodFollowingListRefreshEvent;
import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
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
    private MoodFollowingList mood_following_list;
    public static Boolean[] temp_bool_list; // temporary to support this synchronous model to tell when the mood following list is ready


    public FollowingList(String userId) {
        this.db = FirebaseFirestore.getInstance();
        this.userId = userId;
        this.mood_following_list = new MoodFollowingList();
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
        followingRef.get().addOnCompleteListener(task -> {
            System.out.println("REFRESH ADD COMPLETE LISTENER");
            following_list = new ArrayList<>();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    following_list.add(new Participant(document.getId()));
                    System.out.println("username " + document.getId());
                }
                updateDatabaseData(database, model);
            }
        });
    }

    @Override
    public void updateDatabaseData(MVCDatabase database, MVCModel model) {
        if (following_list.size() == 0){
            model.createBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
            try{
                MoodFollowingListRefreshEvent last_event = (MoodFollowingListRefreshEvent) model.getLastEvent();
                model.notifyViews(BackendObject.State.MOODFOLLOWINGLIST);
                last_event.updateSwipeContainer();
            }
            catch (Exception ignore){

            }
            return;
        }
        temp_bool_list = new Boolean[following_list.size()];
        Arrays.fill(temp_bool_list, false);
        for (int i = 0; i < following_list.size(); i++){
            Participant followee = following_list.get(i);
            followee.setDatabaseData2(database, model, i);
        }

    }
    private void updateMoodFollowingList(){
        ArrayList<MoodEvent> followees_recent_mood_events = mood_following_list.getList();
        followees_recent_mood_events.clear();
        for (Participant participant: following_list){
            MoodHistoryList participant_history_list = participant.getMoodHistoryList();
            List<MoodEvent> followee_history_list = participant_history_list.getList();
            followee_history_list.sort((a, b) -> Long.compare(a.getEpochTime(), b.getEpochTime()));
            List<MoodEvent> most_recent_mood_events = new ArrayList<MoodEvent>();
            int i = 0;
            for (MoodEvent mood_event: followee_history_list){
                System.out.println(mood_event.getMoodEventString());
                if (mood_event.getIsPublic()){
                    most_recent_mood_events.add(mood_event);
                    i++;
                }
                if (i == MoodFollowingList.getMoodEventsPerParticipant()){
                    break;
                }
            }
            followees_recent_mood_events.addAll(most_recent_mood_events);
        }
    }
    public MoodFollowingList getMoodFollowingList(){
        System.out.println("GETMOODFOLLOWINGLIST STARTS");
        updateMoodFollowingList();
        return mood_following_list;
    }

}
