package com.example.moodmasters.Objects.ObjectsMisc;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;

import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterMoodEventMap{
    private boolean mood_history_filter;
    private boolean mood_following_filter;
    private boolean location_recency_filter;
    private List<MoodEvent> mood_history;
    private List<MoodEvent> mood_following;
    public FilterMoodEventMap(List<MoodEvent> init_mood_history, List<MoodEvent> init_mood_following){
        mood_history = init_mood_history;
        mood_following = init_mood_following;
        location_recency_filter = false;
        mood_history_filter = false;
        mood_following_filter = false;
    }
    public FilterMoodEventMap(){
        mood_history = null;
        mood_following = null;
        location_recency_filter = false;
        mood_history_filter = false;
        mood_following_filter = false;
    }
    public void filterByMoodHistory(List<MoodEvent> mood_event_list){
        if (mood_history == null){
            throw new InvalidParameterException("Error: FilterMoodEventMap mood_history null");
        }
        if (mood_history_filter){
            return;
        }
        if (!mood_following_filter && !location_recency_filter){
            mood_event_list.removeAll(mood_following);
        }
        else{
            mood_event_list.addAll(mood_history);
        }
        mood_history_filter = true;
    }
    public void filterByMoodFollowing(List<MoodEvent> mood_event_list){
        if (mood_following == null){
            throw new InvalidParameterException("Error: FilterMoodEventMap mood_history null");
        }
        if (mood_following_filter){
            return;
        }
        if (!mood_history_filter && !location_recency_filter){
            // none of the other filters are active
            mood_event_list.removeAll(mood_history);
        }
        else if (!location_recency_filter){
            // only mood history filter is active
            mood_event_list.addAll(mood_following);
        }
        else{
            // only location recency filter is active
            for (MoodEvent mood_event: mood_following){
                // to make sure there are no duplicates added
                if (!mood_event_list.contains(mood_event)){
                    mood_event_list.add(mood_event);
                }
            }
        }
        mood_following_filter = true;
    }
    public List<MoodEvent> createLocationRecencyList(){
        List<MoodEvent> location_recency = new ArrayList<MoodEvent>();
        //FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        for (MoodEvent mood_event: mood_following){

        }
        return location_recency;
    }
    public void filterByLocationRecency(List<MoodEvent> mood_event_list){
        // TODO: implement this
    }
    public void revertFilterByMoodHistory(List<MoodEvent> mood_event_list){
        if (mood_history == null){
            throw new InvalidParameterException("Error: FilterMoodEventMap mood_history null");
        }
        if (!mood_history_filter){
            return;
        }
        if (!mood_following_filter && !location_recency_filter){
            // none of the other filters are active
            mood_event_list.addAll(mood_following);
        }
        else{
            mood_event_list.removeAll(mood_history);
        }
        mood_history_filter = false;
    }
    // TODO: worry about conflicts with recency_location and mood_following later
    public void revertFilterByMoodFollowing(List<MoodEvent> mood_event_list){
        if (mood_following == null){
            throw new InvalidParameterException("Error: FilterMoodEventMap mood_history null");
        }
        if (!mood_following_filter){
            return;
        }
        if (!mood_history_filter && !location_recency_filter){
            // none of the other filters are active
            mood_event_list.addAll(mood_history);
        }
        else{
            mood_event_list.removeAll(mood_following);
        }
        mood_following_filter = false;
    }
    public void revertFilterByLocationRecency(List<MoodEvent> mood_event_list){
        // TODO: implement this
    }
    public boolean isMoodHistoryFilter(){
        return mood_history_filter;
    }
    public boolean isMoodFollowingFilter(){
        return mood_following_filter;
    }
    public boolean isLocationRecencyFilter(){
        return location_recency_filter;
    }
}
