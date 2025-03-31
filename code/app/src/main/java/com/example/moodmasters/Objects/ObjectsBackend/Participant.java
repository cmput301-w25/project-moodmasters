package com.example.moodmasters.Objects.ObjectsBackend;

import androidx.annotation.NonNull;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a class that represents any and all participants for the app. The user will be defined
 * as the currently logged in participant. Once username of user is determined the first backend
 * object that will be initialized is the user object, which will also fetch the mood_history_list
 * member by calling setDatabaseData. This member will also be stored in the model as the
 * MOODHISTORYLIST backend object.
 * */
public class Participant extends MVCBackend implements MVCDatabase.Fetch, MVCDatabase.Create{
    private String password;            /*could be a source of vulnerability but considering the scope of this app its fine*/
    private String username;
    private MoodHistoryList mood_history_list;
    private FollowingList following_list;

    /**
     * Participant constructor.
     * @param init_username
     *  This is the Participant's unique username.
     */
    public Participant(String init_username){
        username = init_username;
        mood_history_list = new MoodHistoryList(this);
        this.following_list = new FollowingList(init_username);
    }

    /*
    * just temporary for now while i add multithreading (if i have time)
    * */
    public void fetchDatabaseData(MVCDatabase database, MVCModel model, OnSuccessFetchListener listener){
        database.addCollection("participants");
        database.addDocument(username);
        DocumentReference doc_ref = database.getDocument(username);
        doc_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        mood_history_list.clearAllObjects();
                        ArrayList<MoodEvent> mood_array_list = new ArrayList<>();
                        password = (String) snapshot.get("password");
                        ArrayList list = (ArrayList) snapshot.get("list");
                        for (int i = 0; i < list.size(); i++) {
                            MoodEvent mood_event = new MoodEvent((HashMap) list.get(i));
                            mood_array_list.add(mood_event);
                        }
                        mood_history_list.addAllObjects(mood_array_list);
                        listener.onSuccess(Participant.this, true);
                    }
                    else{
                        listener.onSuccess(Participant.this, false);
                    }
                }
            }
        });
    }

    public void createDatabaseData(MVCDatabase database, MVCModel model, OnSuccessCreateListener listener){
        database.addCollection("participants");
        database.addDocument(username);
        DocumentReference doc_ref = database.getDocument(username);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", mood_history_list.getList());
        map.put("password", password);
        doc_ref.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess(Participant.this, true);
                }
                else{
                    listener.onSuccess(Participant.this, false);
                }
            }
        });
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
    public void setMoodHistoryList(MoodHistoryList new_mood_history_list){
        mood_history_list = new_mood_history_list;
    }
    public void addAllMoodHistoryList(List<MoodEvent> mood_event_list){
        mood_history_list.addAllObjects(mood_event_list);
    }

    public FollowingList getFollowingList() {
        return following_list;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String new_password){
        password = new_password;
    }
}
