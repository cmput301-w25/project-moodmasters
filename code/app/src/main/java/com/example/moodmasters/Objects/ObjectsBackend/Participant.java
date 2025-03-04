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
    private DocumentReference docRef;

    private MoodHistoryList mood_history_list;          /*needed for generating mood following list of the user*/

    public Participant(String init_username){
        username = init_username;
        //mood_history_list = new MoodHistoryList();
    }

    public void setDatabaseData(DocumentReference docRef, DocumentSnapshot snapshot){
        // TODO: Implement getting and possibly setting initial data from database
        if (snapshot.exists()) {
            ArrayList<MoodEvent> mood_array_list = new ArrayList<>();
            ArrayList list = (ArrayList) snapshot.get("list");
            for (int i = 0; i < list.size(); i++) {
                Object a = list.get(i);
                int b = 1+1;
            }
            //mood_history_list = new MoodHistoryList(mood_array_list, docRef, snapshot);
            int a = 1 + 1;
        } else {
            mood_history_list = new MoodHistoryList(docRef, snapshot);
            docRef.set(mood_history_list);
        }
    }

    public void updateDatabaseData(DocumentReference docRef){
        // TODO: Implement updating database with added/removed data
        docRef.set(mood_history_list);
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String new_username){
        username = new_username;
    }

    public DocumentReference getDocRef() {
        return docRef;
    }

    public void setDocRef(DocumentReference docRef) {
        this.docRef = docRef;
    }

    public MoodHistoryList getMoodHistoryList(){
        return mood_history_list;
    }
}
