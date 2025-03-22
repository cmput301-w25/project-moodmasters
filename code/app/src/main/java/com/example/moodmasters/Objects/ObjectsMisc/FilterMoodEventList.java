package com.example.moodmasters.Objects.ObjectsMisc;

import android.icu.util.Calendar;

import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// This class will be responsible for the filtering of the MoodEventList, will also save the words that were
// filtered out for easy reversion of filtering, i recommend using map for keeping track of the moodevents
// filtered out and using a string as a key to specify what filter, your free to come up with a format for the key
public class FilterMoodEventList{
    private Map<String, List<MoodEvent>> filtered_moodevents;

    public FilterMoodEventList(){
        filtered_moodevents = new HashMap<String, List<MoodEvent>>();
    }
    public void filterByRecency(List<MoodEvent> object_list){
        // TODO: implement filter by recency
    }
    public void filterByEmotion(List<MoodEvent> object_list, Emotion.State emotion_state){
        // TODO: implement filter by emotion
    }
    public void filterByWords(List<MoodEvent> object_list, String word){
        // TODO: implement filter by word
    }
    public void revertFilterByRecency(List<MoodEvent> object_list){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        long week_start_time = cal.getTimeInMillis()*1000;
        List<MoodEvent> filtered_out = new ArrayList<MoodEvent>();
        for (int i = 0; i < object_list.size(); i++){
            MoodEvent mood_event = object_list.get(i);
            long mood_event_time = mood_event.getEpochTime();
            if (week_start_time > mood_event_time){
                filtered_out.add(mood_event);
                object_list.remove(i);
            }
        }
    }
    public void revertFilterByEmotion(List<MoodEvent> object_list, Emotion.State emotion_state){
        // TODO: implement reverting of filter by emotion
    }
    public void revertFilterByWords(List<MoodEvent> object_list, String emotion){
        // TODO: implement reverting of filter by word
    }
}
