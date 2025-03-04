package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.MVC.MVCBackendList;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Just a list that will contain all the 8 moods possible that will be of use during the program
 * */
public class MoodList extends MVCBackendList<Mood> {

    public MoodList(){
        super();
    }
    public MoodList(List<Mood> init_list){
        super(init_list);
    }
    public void setDatabaseData(DocumentReference docRef, DocumentSnapshot snapshot){
        // doesn't need database connectivity
    }
    public void updateDatabaseData(DocumentReference docRef){
        // doesn't need database connectivity
    }

    public Mood getMood(Emotion.State emotion){
        for (Mood mood: object_list){
            if (mood.getEmotion() == emotion){
                return mood;
            }
        }
        throw new NoSuchElementException("Error: MoodList in backend has no mood with emotion " + Emotion.getString(emotion));
    }
    public List<String> getEmotionNames(){
        List<String> emotion_names = new ArrayList<String>(object_list.size());
        for (int i = 0; i < object_list.size(); i++){
            emotion_names.add(i, object_list.get(i).getEmotionString());
        }
        return emotion_names;
    }
}
