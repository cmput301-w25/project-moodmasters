package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a class that represents any and all participants for the app. The user will be defined
 * as the currently logged in participant. Once username of user is determined the first backend
 * object that will be initialized is the user object, which will also fetch the mood_history_list
 * member by calling setDatabaseData. This member will also be stored in the model as the
 * MOODHISTORYLIST backend object.
 * */
public class Participant implements MVCBackend{
    private String username;
    private MoodHistoryList mood_history_list;

    /**
     * Participant constructor.
     * @param init_username
     *  This is the Participant's unique username.
     */
    public Participant(String init_username){
        username = init_username;
    }

    /**
     * This handles the reading of MoodHistoryList data from the database.
     * If there is an existing MoodHistoryList at doc_ref in snapshot, the data is parsed
     * into this.mood_history_list.
     * Else, a new MoodHistoryList is created and stored in this.mood_history_list and
     * uploaded to the database at doc_ref.
     * @param doc_ref
     *  This is the DocumentReference which references the document where the Participant's
     *  data is stored.
     * @param snapshot
     *  This is the DocumentSnapshot of the document where the Participant's data is stored.
     */
    public void setDatabaseData(DocumentReference doc_ref, DocumentSnapshot snapshot){
        if (snapshot.exists()) {   /* Parse existing MoodEvent list */
            ArrayList<MoodEvent> mood_array_list = new ArrayList<>();
            ArrayList list = (ArrayList) snapshot.get("list");
            for (int i = 0; i < list.size(); i++) {
                mood_array_list.add(new MoodEvent((HashMap) list.get(i)));
            }
            this.mood_history_list = new MoodHistoryList(mood_array_list, doc_ref, snapshot);
        } else {  /* Create new MoodEvent list */
            this.mood_history_list = new MoodHistoryList(doc_ref, snapshot);
            doc_ref.set(mood_history_list);
        }
    }

    /**
     * This handles the updating of MoodHistoryList data into the database.
     * @param docRef
     *  This is the DocumentReference which references the document where the Participant's
     *  data is stored.
     */
    public void updateDatabaseData(DocumentReference docRef){
        docRef.set(mood_history_list);
    }

    /**
     * This returns the Participant's username.
     * @return
     *  Return username.
     */
    public String getUsername(){
        return username;
    }

    /**
     * This returns the Participant's mood_history_list.
     * @return
     *  Return mood_history_list.
     */
    public MoodHistoryList getMoodHistoryList(){
        return this.mood_history_list;
    }
}
