package com.example.moodmasters.Objects.ObjectsBackend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
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

/**
 * Represents any and all participants for the app, the user will be defined as the currently logged in user, once username of user
 * is determined the first backend object that will be initialized is the user object which will also fetch the mood_history_list
 * member by calling setDatabaseData, this member will also be stored in the model as the MOODHISTORYLIST backend object
 * */
public class Participant implements MVCBackend{
    private String username;
    private FirebaseFirestore db;
    private CollectionReference participantsRef;
    private DocumentReference docRef;

    private MoodHistoryList mood_history_list;          /*needed for generating mood following list of the user*/

    public Participant(String init_username){
        username = init_username;
        db = FirebaseFirestore.getInstance();
        participantsRef = db.collection("participants");
        docRef = participantsRef.document(this.username);
        mood_history_list = new MoodHistoryList(db);
        setDatabaseData(db);
    }

    public void setDatabaseData(FirebaseFirestore db){
        // TODO: Implement getting and possibly setting initial data from database
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<Mood> list = (ArrayList<Mood>) document.getData().get("list");
                        mood_history_list = new MoodHistoryList(list, db);
                        int a = 1 + 1;
                    } else {
                        mood_history_list = new MoodHistoryList(FirebaseFirestore.getInstance());
                        docRef.set(mood_history_list);
                    }
                }
            }
        });
    }

    public void updateDatabaseData(FirebaseFirestore db){
        // TODO: Implement updating database with added/removed data
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
