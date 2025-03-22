package com.example.moodmasters.Objects.ObjectsMisc;

import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.MVC.MVCBackendList;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public abstract class MoodEventList extends MVCBackendList<MoodEvent> {
    private final FilterMoodEventList filter;
    
    public MoodEventList() {
        filter = new FilterMoodEventList();
    }
    public MoodEventList(ArrayList<MoodEvent> list) {
        super(list);
        filter = new FilterMoodEventList();
    }
    public void recentFilterMoodEventList(){
        filter.filterByRecency(object_list);
    }
    public void emotionFilterMoodEventList(Emotion.State emotion_state){
        filter.filterByEmotion(object_list, emotion_state);
    }
    public void wordFilterMoodEventList(String word){
        filter.filterByWords(object_list, word);
    }
    public void revertRecentFilterMoodEventList(){
        filter.revertFilterByRecency(object_list);
    }
    public void revertEmotionFilterMoodEventList(Emotion.State emotion_state){
        filter.revertFilterByEmotion(object_list, emotion_state);
    }
    public void revertWordFilterMoodEventList(String word){
        filter.revertFilterByWords(object_list, word);
    }
}
