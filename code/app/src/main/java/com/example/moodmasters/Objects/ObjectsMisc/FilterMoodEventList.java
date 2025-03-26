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
    private final List<MoodEvent> filtered_out;
    private final List<Emotion.State> emotion_filter;
    private final List<String> reason_filter;
    private boolean recency_filter;
    private final String reason_filter_label = "Reason:";
    public FilterMoodEventList(){
        emotion_filter = new ArrayList<Emotion.State>();
        reason_filter = new ArrayList<String>();
        recency_filter = false;
        filtered_out = new ArrayList<MoodEvent>();
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
    public boolean reasonContainsWord(String reason, String word){
        String regex = "("+ "[\\s\\p{Punct}]" + word + "[\\s\\p{Punct}]" + ")|" +
                "(" + "^" + word + "[\\s\\p{Punct}]" + ")|" +
                "(" + "[\\s\\p{Punct}]" + word + "$" + ")|" +
                "(" + "^" + word + "$" + ")";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(reason);
        return matcher.find();
    }
    public boolean doesMoodEventSatisfyFilters(MoodEvent mood_event){
        long week_start_time = getRecencyWeekStartTime();
        long mood_event_time = mood_event.getEpochTime();
        Emotion.State emotion = mood_event.getMood().getEmotion();
        if (recency_filter && week_start_time > mood_event_time){
            return false;
        }
        if (!emotion_filter.contains(emotion) && !emotion_filter.isEmpty()){
            return false;
        }
        for (String reason_word: reason_filter){
            if (reasonContainsWord(mood_event.getReason(), reason_word)){
                return true;
            }
        }
        return reason_filter.isEmpty();
    }
    public void filterByRecency(List<MoodEvent> object_list){
        if (recency_filter){
            return;         /*filter already applied, don't need to do anything*/
        }
        recency_filter = true;

        List<MoodEvent> removables = new ArrayList<MoodEvent>();
        for (MoodEvent mood_event: object_list){            /*you are only filtering out on recency filters and not bringing back in*/
            if (!doesMoodEventSatisfyFilters(mood_event)){
                filtered_out.add(mood_event);
                removables.add(mood_event);
            }
        }
        object_list.removeAll(removables);
    }
    public void filterByEmotion(List<MoodEvent> object_list, Emotion.State emotion_state){
        if (emotion_filter.contains(emotion_state)){
            return;         /*filter already applied, don't need to do anything*/
        }
        emotion_filter.add(emotion_state);

        List<MoodEvent> removables;
        // only need to apply object_list filtering on first time applying filter, for adding more emotion filters you are
        // just bringing back previously filtered out moodevents
        if (emotion_filter.size() == 1){
            removables = new ArrayList<MoodEvent>();
            for (MoodEvent mood_event: object_list){
                if (!doesMoodEventSatisfyFilters(mood_event)){
                    filtered_out.add(mood_event);
                    removables.add(mood_event);
                }
            }
            object_list.removeAll(removables);
            return;
        }

        removables = new ArrayList<MoodEvent>();
        for (MoodEvent filtered_mood_event: filtered_out){
            if (doesMoodEventSatisfyFilters(filtered_mood_event)){
                removables.add(filtered_mood_event);
                object_list.add(filtered_mood_event);
            }
        }
        filtered_out.removeAll(removables);

    }
    public void filterByWords(List<MoodEvent> object_list, String word){
        if (reason_filter.contains(word)){
            return;         /*filter already applied, don't need to do anything*/
        }
        reason_filter.add(word);

        List<MoodEvent> removables;
        // only need to apply object_list filtering on first time applying filter, for adding more reason filters you are
        // just bringing back previously filtered out moodevents
        if (reason_filter.size() == 1){
            removables = new ArrayList<MoodEvent>();
            for (MoodEvent mood_event: object_list){
                if (!doesMoodEventSatisfyFilters(mood_event)){
                    filtered_out.add(mood_event);
                    removables.add(mood_event);
                }
            }
            object_list.removeAll(removables);
            return;
        }

        removables = new ArrayList<MoodEvent>();
        for (MoodEvent filtered_mood_event: filtered_out){
            if (doesMoodEventSatisfyFilters(filtered_mood_event)){
                removables.add(filtered_mood_event);
                object_list.add(filtered_mood_event);
            }
        }
        filtered_out.removeAll(removables);

    }
    public void revertFilterByRecency(List<MoodEvent> object_list){
        if (!recency_filter){
            return;
        }
        recency_filter = false;
        System.out.println("EXECUTE REMOVE");
        List<MoodEvent> removables = new ArrayList<MoodEvent>();
        for (MoodEvent filtered_mood_event: filtered_out){            /*you are only filtering out on recency filters and not bringing back in*/
            if (doesMoodEventSatisfyFilters(filtered_mood_event)){
                object_list.add(filtered_mood_event);
                removables.add(filtered_mood_event);
            }
        }
        filtered_out.removeAll(removables);
    }
    public void revertFilterByEmotion(List<MoodEvent> object_list, Emotion.State emotion_state){
        if (!emotion_filter.contains(emotion_state)){
            return;
        }
        emotion_filter.remove(emotion_state);

        List<MoodEvent> removables;
        if (emotion_filter.isEmpty()){
            removables = new ArrayList<MoodEvent>();
            for (MoodEvent filtered_mood_event: filtered_out){
                if (doesMoodEventSatisfyFilters(filtered_mood_event)){
                    object_list.add(filtered_mood_event);
                    removables.add(filtered_mood_event);
                }
            }
            filtered_out.removeAll(removables);
            return;
        }

        removables = new ArrayList<MoodEvent>();
        for (MoodEvent mood_event: object_list){
            if (!doesMoodEventSatisfyFilters(mood_event)){
                filtered_out.add(mood_event);
                removables.add(mood_event);
            }
        }
        object_list.removeAll(removables);

    }
    public void revertFilterByWords(List<MoodEvent> object_list, String word){
        if (!reason_filter.contains(word)){
            return;
        }
        reason_filter.remove(word);

        List<MoodEvent> removables;
        if (reason_filter.isEmpty()){
            removables = new ArrayList<MoodEvent>();
            for (MoodEvent filtered_mood_event: filtered_out){
                if (doesMoodEventSatisfyFilters(filtered_mood_event)){
                    object_list.add(filtered_mood_event);
                    removables.add(filtered_mood_event);
                }
            }
            filtered_out.removeAll(removables);
            return;
        }

        removables = new ArrayList<MoodEvent>();
        for (MoodEvent mood_event: object_list){
            if (!doesMoodEventSatisfyFilters(mood_event)){
                filtered_out.add(mood_event);
                removables.add(mood_event);
            }
        }
        object_list.removeAll(removables);

    }
    public void applyExistingFilters(List<MoodEvent> object_list, MoodEvent mood_event){
        if (doesMoodEventSatisfyFilters(mood_event)){
            object_list.add(mood_event);
        }
        else{
            filtered_out.add(mood_event);
        }
    }
    public boolean getRecencyFilter(){
        return recency_filter;
    }
    public List<Emotion.State> getEmotionFilter(){
        return emotion_filter;
    }
    public List<String> getReasonFilter(){
        return reason_filter;
    }
}
