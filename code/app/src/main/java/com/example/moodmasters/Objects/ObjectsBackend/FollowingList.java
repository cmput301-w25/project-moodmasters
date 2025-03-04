package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.MVC.MVCBackendList;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

// Not needed for halfway checkpoint

//public class FollowingList extends MVCBackendList<Participant> {
//    private MoodFollowingList mood_following_list;          /*This will also be accessible from the model for easy access*/
//    public FollowingList(){
//        super();
//    }
//    public void setDatabaseData(DocumentReference docRef, DocumentSnapshot snapshot){
//        // TODO: Implement getting and possibly setting initial data from database
//    }
//    public void updateDatabaseData(DocumentReference docRef){
//        // TODO: Implement updating database with added/removed data
//    }
//
//    // called when model.createBackendObject(MOODFOLLOWINGLIST) is called
//    public MoodFollowingList createFollowingList(){
//        mood_following_list = new MoodFollowingList(FirebaseFirestore.getInstance());
//        // TODO: Create Mood Following List From participants
//        return mood_following_list;
//    }
//}
