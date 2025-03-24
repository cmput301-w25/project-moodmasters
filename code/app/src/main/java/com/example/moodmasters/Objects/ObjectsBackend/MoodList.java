package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.MVC.MVCBackendList;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This is a class that contains a list of all the 8 Moods possible that will be of use
 * during the program.
 * */
public class MoodList extends MVCBackendList<Mood> {

    /**
     * MoodList constructor.
     * @param init_list
     *  This is a list of all possible Moods.
     */
    public MoodList(List<Mood> init_list){
        super(init_list);
    }

    public void addDatabaseData(MVCDatabase database, Object object){
        // doesn't need database connectivity
    }

    public void removeDatabaseData(MVCDatabase database, Object object){
        // doesn't need database connectivity
    }

    /**
     * Returns the passed emotion state as a Mood.
     * @param emotion
     *  This is the emotion State to be returned as a Mood.
     * @return
     *  Return the passed emotion State as a Mood.
     */
    public Mood getMood(Emotion.State emotion){
        for (Mood mood: object_list){
            if (mood.getEmotion() == emotion){
                return mood;
            }
        }
        throw new NoSuchElementException("Error: MoodList in backend has no mood with emotion " + Emotion.getString(emotion));
    }

    /**
     * Returns a list of all emotions as Strings.
     * @return
     *  Return a list of all emotions as Strings.
     */
    public List<String> getEmotionNames(){
        List<String> emotion_names = new ArrayList<String>(object_list.size());
        for (int i = 0; i < object_list.size(); i++){
            emotion_names.add(i, object_list.get(i).getEmotionString());
        }
        return emotion_names;
    }

}
