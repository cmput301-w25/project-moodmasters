package com.example.moodmasters.Objects.ObjectsBackend;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.MoodHistoryListActivity;
import com.example.moodmasters.Views.SignupLoginScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * This is a class that represents any and all participants for the app. The user will be defined
 * as the currently logged in participant. Once username of user is determined the first backend
 * object that will be initialized is the user object, which will also fetch the mood_history_list
 * member by calling setDatabaseData. This member will also be stored in the model as the
 * MOODHISTORYLIST backend object.
 * */
public class Participant extends MVCBackend implements MVCDatabase.Set{
    private String username;
    private MoodHistoryList mood_history_list;
    private FollowingList followingList;
    /**
     * Participant constructor.
     * @param init_username
     *  This is the Participant's unique username.
     */
    public Participant(String init_username){
        username = init_username;
        this.followingList = new FollowingList(init_username);
    }


    public void setDatabaseData(MVCDatabase database, MVCModel model){
        LoginScreenOkEvent last_event = (LoginScreenOkEvent) model.getLastEvent();
        database.addCollection("participants");
        database.addDocument(username);
        DocumentReference doc_ref = database.getDocument(username);
        doc_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();

                    if (last_event.getSignUp()) {
                        // Sign Up: Check if the username already exists
                        if (snapshot.exists()) {
                            last_event.setAction("SignupError");
                        }
                        else {
                            last_event.setAction("GoMoodHistoryActivity");
                            mood_history_list = new MoodHistoryList();
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("list", mood_history_list.getList());
                            doc_ref.set(map);
                        }
                    }
                    else {
                        // Login: Check if the username exists
                        if (snapshot.exists()) {
                            last_event.setAction("GoMoodHistoryActivity");
                            ArrayList<MoodEvent> mood_array_list = new ArrayList<>();
                            ArrayList list = (ArrayList) snapshot.get("list");
                            for (int i = 0; i < list.size(); i++) {
                                mood_array_list.add(new MoodEvent((HashMap) list.get(i)));
                            }
                            mood_history_list = new MoodHistoryList(mood_array_list, Participant.this);
                        }
                        else {
                            last_event.setAction("LoginError");
                        }
                    }
                    // not ideal but will work for now, adding multithreading would be better
                    ((LoginScreenOkEvent) model.getLastEvent()).afterDatabaseQuery();
                }
            }
        });
    }

    /*
    * just temporary for now while i add multithreading
    * */
    public void setDatabaseData2(MVCDatabase database, MVCModel model){
        database.addCollection("participants");
        database.addDocument(username);
        DocumentReference doc_ref = database.getDocument(username);
        doc_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        ArrayList<MoodEvent> mood_array_list = new ArrayList<>();
                        ArrayList list = (ArrayList) snapshot.get("list");
                        for (int i = 0; i < list.size(); i++) {
                            mood_array_list.add(new MoodEvent((HashMap) list.get(i)));
                        }
                        mood_history_list = new MoodHistoryList(mood_array_list, Participant.this);
                    }
                    else{
                        mood_history_list = new MoodHistoryList();
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("list", mood_history_list.getList());
                        doc_ref.set(map);
                    }
                }
            }
        });
    }

    /**
     * This handles the updating of MoodHistoryList data into the database.
     * @param doc_ref
     *  This is the DocumentReference which references the document where the user's
     *  data is stored.
     */
    public void updateDatabaseData(DocumentReference doc_ref){
        doc_ref.set(mood_history_list);
    }

    /**
     * username getter.
     */
    public String getUsername(){
        return username;
    }

    /**
     * mood_history_list getter.
     */
    public MoodHistoryList getMoodHistoryList(){
        return this.mood_history_list;
    }

    public FollowingList getFollowingList() {
        return followingList;
    }
}
