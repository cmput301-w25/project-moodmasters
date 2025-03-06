package com.example.moodmasters.Objects.ObjectsBackend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents any and all participants for the app, the user will be defined as the currently logged in user, once username of user
 * is determined the first backend object that will be initialized is the user object which will also fetch the mood_history_list
 * member by calling setDatabaseData, this member will also be stored in the model as the MOODHISTORYLIST backend object
 * */
public class Participant implements MVCBackend{
    private String username;

    private MoodHistoryList mood_history_list;          /*needed for generating mood following list of the user*/

    public Participant(String init_username){
        username = init_username;
    }

    public void setDatabaseData(DocumentReference doc_ref, DocumentSnapshot snapshot){
        if (snapshot.exists()) {
            ArrayList<MoodEvent> mood_array_list = new ArrayList<>();
            ArrayList list = (ArrayList) snapshot.get("list");
            for (int i = 0; i < list.size(); i++) {
                mood_array_list.add(new MoodEvent((HashMap) list.get(i)));
            }
            mood_history_list = new MoodHistoryList(mood_array_list, doc_ref, snapshot);
        } else {
            mood_history_list = new MoodHistoryList(doc_ref, snapshot);
            doc_ref.set(mood_history_list);
        }
    }

    public void updateDatabaseData(DocumentReference docRef){
        docRef.set(mood_history_list);
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String new_username){
        username = new_username;
    }

    public MoodHistoryList getMoodHistoryList(){
        return mood_history_list;
    }
}
