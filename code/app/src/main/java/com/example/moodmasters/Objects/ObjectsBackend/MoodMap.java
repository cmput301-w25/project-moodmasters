package com.example.moodmasters.Objects.ObjectsBackend;

import android.content.Context;
import android.location.Location;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.FilterMoodEventMap;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Class that stores all the map objects and methods for the MoodEventMap, responsible for displaying
 * mood events on the map, deleting them, etc
 * */
public class MoodMap extends MVCBackend {
    private String username;
    private Double user_longitude;
    private Double user_latitude;
    private GoogleMap google_map;
    private List<MoodEvent> mood_events;
    private FilterMoodEventMap filter_mood_map;

    public MoodMap(List<MoodEvent> init_events, List<MoodEvent> init_mood_history,
                   List<MoodEvent> init_mood_following, String init_username) {
        mood_events = init_events;
        filter_mood_map = new FilterMoodEventMap(init_mood_history, init_mood_following);
        username = init_username;
        google_map = null;
        user_latitude = null;
        user_longitude = null;
    }
    public void clearMap() {
        google_map.clear();
    }
    public void displayMoodEvents(Context context) {
        for (int i = 0; i < mood_events.size(); i++) {
            MoodEvent event = mood_events.get(i);
            String emoji = context.getResources().getString(event.getMood().getEmoticon());
            google_map.addMarker(new MarkerOptions()
                    .position(event.getLocation())
                    .title(event.getUsername() + emoji));

        }
    }
    public void moodHistoryFilterMoodEventList(){
        filter_mood_map.filterByMoodHistory(mood_events);
    }
    public void moodFollowingFilterMoodEventList(){
        filter_mood_map.filterByMoodFollowing(mood_events);
    }
    public void recencyLocationFilterMoodEventList(){
        filter_mood_map.filterByLocationRecency(mood_events);
    }
    public void revertMoodHistoryFilterMoodEventList(){
        filter_mood_map.revertFilterByMoodHistory(mood_events);
    }
    public void revertMoodFollowingFilterMoodEventList(){
        filter_mood_map.revertFilterByMoodFollowing(mood_events);
    }
    public void revertRecencyLocationFilterMoodEventList(){
        filter_mood_map.revertFilterByLocationRecency(mood_events);
    }
    public void filterCreateLocationRecencyList(){
        if (user_longitude == null || user_latitude == null){
            throw new InvalidParameterException("Error: trying to create location recency list when longitude and latitude are null");
        }
        filter_mood_map.createLocationRecencyList(user_longitude, user_latitude);
    }
    public FilterMoodEventMap getFilter(){
        return filter_mood_map;
    }
    public GoogleMap getGoogleMap() {
        return google_map;
    }

    public void setGoogleMap(GoogleMap google_map) {
        this.google_map = google_map;
    }

    public List<MoodEvent> getMoodEvents() {
        return mood_events;
    }
    public void setMoodEvents(List<MoodEvent> mood_events) {
        this.mood_events = mood_events;
    }
    public void setUserLocation(Location location){
        user_latitude = location.getLatitude();
        user_longitude = location.getLongitude();
    }
}
