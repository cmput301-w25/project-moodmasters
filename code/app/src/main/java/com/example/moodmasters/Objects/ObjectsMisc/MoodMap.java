package com.example.moodmasters.Objects.ObjectsMisc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MoodMap extends MVCBackend {
    private GoogleMap google_map;
    private List<MoodEvent> mood_events;
    private FilterMoodEventMap filter_mood_map;

    public MoodMap(GoogleMap init_map, List<MoodEvent> init_events, List<MoodEvent> init_mood_history, List<MoodEvent> init_mood_following) {
        google_map = init_map;
        mood_events = init_events;
        filter_mood_map = new FilterMoodEventMap(init_mood_history, init_mood_following);
    }
    public MoodMap(GoogleMap init_map, List<MoodEvent> init_events) {
        google_map = init_map;
        mood_events = init_events;
        filter_mood_map = new FilterMoodEventMap();
    }
    public MoodMap() {
        google_map = null;
        mood_events = new ArrayList<MoodEvent>();
        filter_mood_map = new FilterMoodEventMap();
    }
    public void clearMap() {
        google_map.clear();
    }
    public void displayMoodEvents(Context context) {
        /*
        Bitmap bitmap = Bitmap.createBitmap(24, 24, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(LINE_WIDTH);
        paint.setColor(Color.YELLOW);
        canvas.drawText("😐", 0, 0, paint);
         */
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
}
