package com.example.moodmasters.Objects.ObjectsApp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a class that represents a single mood event created by a user.
 */
public class MoodEvent{
    private String datetime;
    private long epoch_time;
    private Mood mood;
    private String reason;
    private SocialSituation.State situation;
    private boolean is_public;
    private LatLng location;
    private String username;
    private String photo_string;
    private ArrayList<Comment> comments;

    /**
     * MoodEvent constructor.
     * @param init_datetime
     *  This is the MoodEvent's datetime.
     * @param init_epoch_time
     *  This is the MoodEvent's epoch time.
     * @param init_mood
     *  This is the MoodEvent's Mood.
     * @param init_reason
     *  (optional) This is the MoodEvent's reason.
     * @param init_situation
     *  (optional) This is the MoodEvent's social situation.
     * @param init_is_public
     *  This is the MoodEvent's publicity.
     * @param init_location
     *  (optional) This is the MoodEvent's location
     */
    public MoodEvent(String init_datetime, long init_epoch_time, Mood init_mood, boolean init_is_public,
                     @Nullable String init_reason, @Nullable SocialSituation.State init_situation,
                     @Nullable LatLng init_location, String init_username, String init_photo_string,
                     ArrayList<Comment> init_comments){
        datetime = init_datetime;
        mood = init_mood;
        epoch_time = init_epoch_time;
        reason = init_reason;
        situation = init_situation;
        is_public = init_is_public;
        location = init_location;
        username = init_username;
        photo_string = init_photo_string;
        comments = init_comments;
    }

    /**
     * MoodEvent constructor.
     * @param map
     *  This is a HashMap retrieved from the Firebase database containing mood event information.
     */
    public MoodEvent(HashMap map) {
        // map.forEach((key, value) -> System.out.println(key + ":" + value));
        mood = new Mood((HashMap) map.get("mood"));

        epoch_time = (long) map.get("epochTime");

        // add support for phones with possibly different timezones than the one of the mood event poster
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(epoch_time);
        DateFormat format = new SimpleDateFormat("MMM dd yyyy | HH:mm");
        datetime = format.format(calendar.getTime());

        reason = (String) map.get("reason");

        situation = SocialSituation.fromStringToSocialState((String) map.get("situation"));

        is_public = (boolean) map.get("isPublic");

        HashMap location_map = (HashMap) map.get("location");

        if (location_map != null){
            location = new LatLng((double) location_map.get("latitude"), (double) location_map.get("longitude"));
        }
        else {
            location = null;
        }

        username = (String) map.get("username");

        photo_string =  (String) map.get("photoString");

        ArrayList<HashMap<String,Object>> comment_maps = (ArrayList<HashMap<String,Object>>) map.get("comments");
        comments = new ArrayList<Comment>();
        for (HashMap<String,Object> comment_map: comment_maps){
            comments.add(new Comment(comment_map));
        }
        for (Comment comment: comments){
            System.out.println(comment.createString());
        }
    }

    /**
     * Gets a String representation of a mood event for debugging purposes
     * @return
     *  The String for the mood event
     */
    public String getMoodEventString(){
        String public_string;
        if (is_public){
            public_string = "public";
        }
        else{
            public_string = "private";
        }
        String mood_event_string = datetime + "\n" + mood.getEmotionString() + "\n" +
                reason + "\n" + SocialSituation.getString(situation) +
                "\n" + public_string + "\n" + username;
        return mood_event_string;
    }

    /**
     * datetime getter
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     * epoch_time getter
     */
    public long getEpochTime() {
        return epoch_time;
    }

    /**
     * mood getter
     */
    public Mood getMood() {
        return mood;
    }

    /**
     * reason getter
     */
    public String getReason() {
        return reason;
    }

    /**
     * situation getter
     */
    public SocialSituation.State getSituation() {
        return situation;
    }

    /**
     * datetime setter
     */
    public void setDatetime(String new_datetime) {
        datetime = new_datetime;
    }

    /**
     * epoch_time setter
     */
    public void getEpochTime(long new_epoch_time) {
        epoch_time = new_epoch_time;
    }

    /**
     * mood setter
     */
    public void setMood(Mood new_mood) {
        mood = new_mood;
    }

    /**
     * reason setter
     */
    public void setReason(String new_reason) {
        reason = new_reason;
    }

    /**
     * situation setter
     */
    public void setSituation(SocialSituation.State new_situation) {
        situation = new_situation;
    }

    /**
     * is_public getter
     */
    public boolean getIsPublic() {
        return is_public;
    }

    /**
     * is_public setter
     */
    public void setIsPublic(boolean is_public) {
        this.is_public = is_public;
    }
    /**
     * location getter
     */
    public LatLng getLocation() {
        return location;
    }

    /**
     * location setter
     */
    public void setLocation(LatLng location) {
        this.location = location;
    }
    /**
     * username getter
     */
    public String getUsername() {
        return username;
    }
    /**
     * username setter
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * encoded photo getter
     */
    public String getPhotoString(){
        return photo_string;
    }
    /**
     * encoded photo setter
     */
    public void setPhotoString(String new_photo_string){
        photo_string = new_photo_string;
    }
    /**
     * comments array getter
     */
    public ArrayList<Comment> getComments(){
        return comments;
    }
    /**
     * comments array setter
     */
    public void setComments(ArrayList<Comment> new_comments){
        comments = new_comments;
    }
    /**
     * add comment to the comments array
     */
    public void addComment(Comment comment){
        comments.add(comment);
    }
}
