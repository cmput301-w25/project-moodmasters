package com.example.moodmasters.Objects.ObjectsMisc;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;

import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FilterMoodEventMap{
    private boolean mood_history_filter;
    private boolean mood_following_filter;
    private boolean location_recency_filter;
    private List<MoodEvent> mood_history;
    private List<MoodEvent> mood_following;
    private List<MoodEvent> location_recency;
    public FilterMoodEventMap(List<MoodEvent> init_mood_history, List<MoodEvent> init_mood_following){
        mood_history = init_mood_history;
        mood_following = init_mood_following;
        location_recency = null;
        location_recency_filter = false;
        mood_history_filter = false;
        mood_following_filter = false;
    }
    public FilterMoodEventMap(){
        mood_history = null;
        mood_following = null;
        location_recency = null;
        location_recency_filter = false;
        mood_history_filter = false;
        mood_following_filter = false;
    }
    private List<MoodEvent> createFollowingListMostRecent(){
        Map<String, MoodEvent> user_most_recent = new HashMap<String, MoodEvent>();
        List<MoodEvent> following_list_most_recent;
        for (MoodEvent mood_event: mood_following){
            MoodEvent current_mood_event = user_most_recent.get(mood_event.getUsername());
            if (current_mood_event == null || current_mood_event.getEpochTime() < mood_event.getEpochTime()){
                user_most_recent.put(mood_event.getUsername(), mood_event);
            }
        }
        following_list_most_recent = new ArrayList<MoodEvent>(user_most_recent.values());
        return following_list_most_recent;
    }
    public void createLocationRecencyList(double user_longitude, double user_latitude){
        List<MoodEvent> following_list_most_recent = createFollowingListMostRecent();
        double earth_radius = 6378;
        Function<Double, Double> haversine = (val) -> {
            return Math.pow(Math.sin(val), 2);
        };

        location_recency = new ArrayList<MoodEvent>();
        // calculate using "Haversine Formula"
        for (MoodEvent mood_event: following_list_most_recent){
            double other_latitude = mood_event.getLocation().latitude;
            double other_longitude = mood_event.getLocation().longitude;

            double diff_latitude = Math.toRadians((other_latitude - user_latitude));
            double diff_longitude = Math.toRadians((other_longitude - user_longitude));

            double new_user_latitude = Math.toRadians(user_latitude);
            double new_other_latitude = Math.toRadians(other_latitude);

            double a = haversine.apply(diff_latitude) + Math.cos(new_user_latitude) * Math.cos(new_other_latitude) * haversine.apply(diff_longitude);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = c*earth_radius;
            if (distance <= 5){
                location_recency.add(mood_event);
            }
        }
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
    public void filterByLocationRecency(List<MoodEvent> mood_event_list){
        if (location_recency == null){
            throw new InvalidParameterException("Error: FilterMoodEventMap mood_history null");
        }
        if (location_recency_filter){
            return;
        }
        if (!mood_history_filter && !mood_following_filter){
            mood_event_list.clear();
            mood_event_list.addAll(location_recency);
        }
        else if (!mood_following_filter){
            mood_event_list.addAll(location_recency);
        }
        location_recency_filter = true;
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
        else if (location_recency_filter){
            List<MoodEvent> removables = new ArrayList<MoodEvent>();
            for (MoodEvent mood_event: mood_following){
                if (!location_recency.contains(mood_event)){
                    removables.add(mood_event);
                }
            }
            mood_event_list.removeAll(removables);
        }
        else{
            mood_event_list.removeAll(mood_following);
        }
        mood_following_filter = false;
    }
    public void revertFilterByLocationRecency(List<MoodEvent> mood_event_list){
        if (location_recency == null){
            throw new InvalidParameterException("Error: FilterMoodEventMap mood_history null");
        }
        if (!location_recency_filter){
            return;
        }
        if (!mood_history_filter && !mood_following_filter){
            // none of the other filters are active
            mood_event_list.clear();
            mood_event_list.addAll(mood_history);
            mood_event_list.addAll(mood_following);
        }
        else if (!mood_following_filter){
            mood_event_list.removeAll(location_recency);
        }
        location_recency_filter = false;
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
