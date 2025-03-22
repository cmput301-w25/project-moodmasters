package com.example.moodmasters.Objects.ObjectsMisc;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;

import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This class will be responsible for the filtering of the MoodEventList, will also save the words that were
// filtered out for easy reversion of filtering, i recommend using map for keeping track of the moodevents
// filtered out and using a string as a key to specify what filter, your free to come up with a format for the key
public class FilterMoodEventList{
    private final Map<MoodEvent, List<String>> mood_events_applied_filters;         /*keeps track of currently applied filters to each moodevent*/
    private final Map<String, List<MoodEvent>> filtered_mood_events;           /*can replace MoodEvent with Pair to put back in former position*/
    private final String reason_filter_label = "Reason:";
    private final String recent_filter_label = "Recency";
    private final List<Emotion.State> emotion_filter;
    private final List<String> reason_filter;
    private boolean recency_filter;
    public FilterMoodEventList(){
        filtered_mood_events = new HashMap<String, List<MoodEvent>>();
        emotion_filter = new ArrayList<Emotion.State>();
        reason_filter = new ArrayList<String>();
        mood_events_applied_filters = new HashMap<MoodEvent, List<String>>();
        recency_filter = false;
    }
    private long getRecencyWeekStartTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.setTimeZone(TimeZone.getDefault());
        return cal.getTimeInMillis();
    }
    public void filterByRecency(List<MoodEvent> object_list){
        long week_start_time = getRecencyWeekStartTime();
        List<MoodEvent> filtered_out = new ArrayList<MoodEvent>();

        /*
        * iterate over the currently filtered out mood events first and add the recency filter
        * to those mood events
        * */
        for (Map.Entry<MoodEvent, List<String>> mood_event_filter: mood_events_applied_filters.entrySet()){
            MoodEvent mood_event = mood_event_filter.getKey();
            long mood_event_time = mood_event.getEpochTime();
            if (week_start_time > mood_event_time){
                List<String> current_filters = mood_event_filter.getValue();
                current_filters.add(recent_filter_label);
                filtered_out.add(mood_event);
            }
        }


        /*
         * iterate over the currently non-filtered out mood events and remove them from the list
         * and give them a list of filters that are applied to them
         * */
        for (int i = 0; i < object_list.size(); i++){
            MoodEvent mood_event = object_list.get(i);
            long mood_event_time = mood_event.getEpochTime();
            if (week_start_time > mood_event_time){
                filtered_out.add(mood_event);
                mood_events_applied_filters.putIfAbsent(mood_event, new ArrayList<String>());
                mood_events_applied_filters.get(mood_event).add(recent_filter_label);
                object_list.remove(i);
            }
        }
        filtered_mood_events.put(recent_filter_label, filtered_out);
        recency_filter = true;
    }
    public void filterByEmotion(List<MoodEvent> object_list, Emotion.State emotion_state){
        List<MoodEvent> filtered_out = new ArrayList<MoodEvent>();

        for (Map.Entry<MoodEvent, List<String>> mood_event_filter: mood_events_applied_filters.entrySet()){
            MoodEvent mood_event = mood_event_filter.getKey();
            Emotion.State mood_event_emotion = mood_event.getMood().getEmotion();
            if (mood_event_emotion == emotion_state){
                List<String> current_filters = mood_event_filter.getValue();
                current_filters.add(Emotion.getString(emotion_state));
                filtered_out.add(mood_event);
            }
        }

        for (int i = 0; i < object_list.size(); i++){
            MoodEvent mood_event = object_list.get(i);
            Emotion.State mood_event_emotion = mood_event.getMood().getEmotion();
            if (mood_event_emotion == emotion_state){
                filtered_out.add(mood_event);
                mood_events_applied_filters.putIfAbsent(mood_event, new ArrayList<String>());
                mood_events_applied_filters.get(mood_event).add(Emotion.getString(emotion_state));
                object_list.remove(i);
            }
        }
        filtered_mood_events.put(Emotion.getString(emotion_state), filtered_out);
        emotion_filter.add(emotion_state);
    }
    public void filterByWords(List<MoodEvent> object_list, String word){
        List<MoodEvent> filtered_out = new ArrayList<MoodEvent>();
        Pattern pattern = Pattern.compile("[\\s\\p{Punct}]" + word + "[\\s\\p{Punct}]", Pattern.CASE_INSENSITIVE);

        for (Map.Entry<MoodEvent, List<String>> mood_event_filter: mood_events_applied_filters.entrySet()){
            MoodEvent mood_event = mood_event_filter.getKey();
            String mood_event_reason = mood_event.getReason();
            Matcher matcher = pattern.matcher(mood_event_reason);
            if (matcher.find()){
                List<String> current_filters = mood_event_filter.getValue();
                current_filters.add(reason_filter_label + word);
                filtered_out.add(mood_event);
            }
        }

        for (int i = 0; i < object_list.size(); i++){
            MoodEvent mood_event = object_list.get(i);
            String mood_event_reason = mood_event.getReason();
            Matcher matcher = pattern.matcher(mood_event_reason);
            if (matcher.find()){
                filtered_out.add(mood_event);
                mood_events_applied_filters.putIfAbsent(mood_event, new ArrayList<String>());
                mood_events_applied_filters.get(mood_event).add(reason_filter_label + word);
                object_list.remove(i);
            }
        }
        filtered_mood_events.put(reason_filter_label + word, filtered_out);
        reason_filter.add(reason_filter_label + word);
    }
    public void revertFilterByRecency(List<MoodEvent> object_list){
        if (!recency_filter){
            throw new IllegalArgumentException("Error: Not Filtered By Recency");
        }
        List<MoodEvent> recency_mood_events = filtered_mood_events.get(recent_filter_label);
        filtered_mood_events.remove(recent_filter_label);
        for (MoodEvent mood_event: recency_mood_events){
            List<String> mood_event_applied_filters = mood_events_applied_filters.get(mood_event);
            mood_event_applied_filters.remove(recent_filter_label);
            if (mood_event_applied_filters.isEmpty()){
                mood_events_applied_filters.remove(mood_event);
                object_list.add(mood_event);
            }
        }
        recency_filter = false;
    }
    public void revertFilterByEmotion(List<MoodEvent> object_list, Emotion.State emotion_state){
        if (!emotion_filter.contains(emotion_state)){
            throw new IllegalArgumentException("Error: Not Filtered By Emotion " + Emotion.getString(emotion_state));
        }
        List<MoodEvent> emotion_mood_events = filtered_mood_events.get(Emotion.getString(emotion_state));
        filtered_mood_events.remove(Emotion.getString(emotion_state));
        for (MoodEvent mood_event: emotion_mood_events){
            List<String> mood_event_applied_filters = mood_events_applied_filters.get(mood_event);
            mood_event_applied_filters.remove(Emotion.getString(emotion_state));
            if (mood_event_applied_filters.isEmpty()){
                mood_events_applied_filters.remove(mood_event);
                object_list.add(mood_event);
            }
        }
        emotion_filter.remove(emotion_state);
    }
    public void revertFilterByWords(List<MoodEvent> object_list, String word){
        if (!reason_filter.contains(reason_filter_label + word)){
            throw new IllegalArgumentException("Error: Not Filtered By word " + word);
        }
        List<MoodEvent> reason_mood_events = filtered_mood_events.get(reason_filter_label + word);
        filtered_mood_events.remove(reason_filter_label + word);
        for (MoodEvent mood_event: reason_mood_events){
            List<String> mood_event_applied_filters = mood_events_applied_filters.get(mood_event);
            mood_event_applied_filters.remove(reason_filter_label + word);
            if (mood_event_applied_filters.isEmpty()){
                mood_events_applied_filters.remove(mood_event);
                object_list.add(mood_event);
            }
        }
        reason_filter.remove(reason_filter_label + word);
    }
    public void applyExistingFilters(List<MoodEvent> object_list, MoodEvent mood_event){
        List<String> mood_event_applied_filters = new ArrayList<String>();
        long week_start_time = getRecencyWeekStartTime();
        for (String filter_key: filtered_mood_events.keySet()){
            List<MoodEvent> filter_key_mood_events = filtered_mood_events.get(filter_key);
            if ((filter_key.equals(recent_filter_label) && week_start_time > mood_event.getEpochTime()) ||
                    (filter_key.equals(Emotion.getString(Emotion.State.HAPPY)) && mood_event.getMood().getEmotion() == Emotion.State.HAPPY) ||
                    (filter_key.equals(Emotion.getString(Emotion.State.SAD)) && mood_event.getMood().getEmotion() == Emotion.State.SAD) ||
                    (filter_key.equals(Emotion.getString(Emotion.State.ANGRY)) && mood_event.getMood().getEmotion() == Emotion.State.ANGRY) ||
                    (filter_key.equals(Emotion.getString(Emotion.State.SCARED)) && mood_event.getMood().getEmotion() == Emotion.State.SCARED) ||
                    (filter_key.equals(Emotion.getString(Emotion.State.DISGUSTED)) && mood_event.getMood().getEmotion() == Emotion.State.DISGUSTED) ||
                    (filter_key.equals(Emotion.getString(Emotion.State.CONFUSED)) && mood_event.getMood().getEmotion() == Emotion.State.CONFUSED) ||
                    (filter_key.equals(Emotion.getString(Emotion.State.ASHAMED)) && mood_event.getMood().getEmotion() == Emotion.State.ASHAMED) ||
                    (filter_key.equals(Emotion.getString(Emotion.State.SURPRISED)) && mood_event.getMood().getEmotion() == Emotion.State.SURPRISED) ||
                    (mood_event.getReason().contains(filter_key.replaceFirst("^" + reason_filter_label, "")))
                ){
                filter_key_mood_events.add(mood_event);
                mood_event_applied_filters.add(filter_key);
            }
        }
        if (mood_event_applied_filters.isEmpty()){
            object_list.add(mood_event);
        }
        else{
            mood_events_applied_filters.put(mood_event, mood_event_applied_filters);
        }
    }
    public boolean getRecencyFilter(){
        return recency_filter;
    }
    public List<Emotion.State> getEmotionFilter(){
        return emotion_filter;
    }
    public List<String> getEditedReasonFilter(){
        List<String> edited_reason_filter = new ArrayList<String>();
        for (String word: reason_filter){
            edited_reason_filter.add(word.replaceFirst("^" + reason_filter_label, ""));
        }
        return edited_reason_filter;
    }
}


/*
if (filter_key.equals(Emotion.getString(Emotion.State.HAPPY)) && mood_event.getMood().getEmotion() == Emotion.State.HAPPY){
    filter_key_mood_events.add(mood_event);
    mood_event_applied_filters.add(recent_filter_label);
}
if (filter_key.equals(Emotion.getString(Emotion.State.SAD)) && mood_event.getMood().getEmotion() == Emotion.State.SAD){
    filter_key_mood_events.add(mood_event);

}
if (filter_key.equals(Emotion.getString(Emotion.State.ANGRY)) && mood_event.getMood().getEmotion() == Emotion.State.ANGRY){
    filter_key_mood_events.add(mood_event);
}
if (filter_key.equals(Emotion.getString(Emotion.State.SCARED)) && mood_event.getMood().getEmotion() == Emotion.State.SCARED){
    filter_key_mood_events.add(mood_event);
}
if (filter_key.equals(Emotion.getString(Emotion.State.DISGUSTED)) && mood_event.getMood().getEmotion() == Emotion.State.DISGUSTED){
    filter_key_mood_events.add(mood_event);
}
if (filter_key.equals(Emotion.getString(Emotion.State.CONFUSED)) && mood_event.getMood().getEmotion() == Emotion.State.CONFUSED){
    filter_key_mood_events.add(mood_event);
}
if (filter_key.equals(Emotion.getString(Emotion.State.ASHAMED)) && mood_event.getMood().getEmotion() == Emotion.State.ASHAMED){
    filter_key_mood_events.add(mood_event);
}
if (filter_key.equals(Emotion.getString(Emotion.State.SURPRISED)) && mood_event.getMood().getEmotion() == Emotion.State.SURPRISED){
    filter_key_mood_events.add(mood_event);
}
            */
