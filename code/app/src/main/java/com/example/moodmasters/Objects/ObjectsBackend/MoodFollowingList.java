package com.example.moodmasters.Objects.ObjectsBackend;

// Not needed for halfway checkpoint

import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
/**
 * Class that represents the mood following list for a user and will store the mood events of the
 * participants a user is following
 *
 * */
public class MoodFollowingList extends MoodEventList {
    private static final int mood_events_per_participant = 3;
    public MoodFollowingList(ArrayList<MoodEvent> list) {
        super(list);
    }
    public MoodFollowingList() {
        super();
    }
    public void removeDatabaseData(MVCDatabase database, Object object, OnSuccessRemoveListener listener){
        // not needed for mood following list
    }
    public void addDatabaseData(MVCDatabase database, Object object, OnSuccessAddListener listener){
        // not needed for mood following list
    }
    public static int getMoodEventsPerParticipant(){
        return mood_events_per_participant;
    }
}
