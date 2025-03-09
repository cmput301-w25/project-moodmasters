package com.example.moodmasters.Objects.ObjectsApp;

import androidx.annotation.Nullable;

import java.util.HashMap;

/**
 * This is a class that represents a single mood event created by a user.
 */
public class MoodEvent {
    private String datetime;
    private long epoch_time;
    private Mood mood;
    private String reason;
    private String trigger;
    private SocialSituation.State situation;

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
     * @param init_trigger
     *  (optional) This is the MoodEvent's trigger.
     * @param init_situation
     *  (optional) This is the MoodEvent's social situation.
     */
    public MoodEvent(String init_datetime, long init_epoch_time, Mood init_mood, @Nullable String init_reason,
                     @Nullable String init_trigger, @Nullable SocialSituation.State init_situation){
        datetime = init_datetime;
        mood = init_mood;
        epoch_time = init_epoch_time;
        reason = init_reason;
        trigger = init_trigger;
        situation = init_situation;
    }

    /**
     * MoodEvent constructor.
     * @param map
     *  This is a HashMap retrieved from the Firebase database containing mood event information.
     */
    public MoodEvent(HashMap map) {
        datetime = (String) map.get("datetime");
        mood = new Mood((HashMap) map.get("mood"));
        epoch_time = (long) map.get("epochTime");
        reason = (String) map.get("reason");
        situation = SocialSituation.fromStringToSocialState((String) map.get("situation"));
        trigger = (String) map.get("trigger");
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
     * trigger getter
     */
    public String getTrigger() {
        return trigger;
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
     * trigger setter
     */
    public void setTrigger(String new_trigger) {
        trigger = new_trigger;
    }

    /**
     * situation setter
     */
    public void setSituation(SocialSituation.State new_situation) {
        situation = new_situation;
    }

}
