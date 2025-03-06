package com.example.moodmasters.Objects.ObjectsApp;

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

    public MoodEvent(String init_datetime, long init_epoch_time, Mood init_mood, String init_reason){
        datetime = init_datetime;
        mood = init_mood;
        epoch_time = init_epoch_time;
        reason = init_reason;
    }

    /**
     * MoodEvent constructor.
     * @param map
     *  This is a HashMap retrieved from the Firebase database containing mood event information.
     */
    public MoodEvent(HashMap map){
        datetime = (String) map.get("datetime");
        mood = new Mood((HashMap) map.get("mood"));
        epoch_time = (long) map.get("epochTime");
        reason = (String) map.get("reason");
        situation = SocialSituation.fromStringToSocialState((String) map.get("situation"));
        trigger = (String) map.get("trigger");
    }

    public MoodEvent(String init_datetime, long init_epoch_time, Mood init_mood, String init_reason,
                     String init_trigger){
        this(init_datetime, init_epoch_time, init_mood, init_reason);
        trigger = init_trigger;
    }
    public MoodEvent(String init_datetime, long init_epoch_time, Mood init_mood, String init_reason,
                     SocialSituation.State init_situation){
        this(init_datetime, init_epoch_time, init_mood, init_reason);
        situation = init_situation;
    }
    public MoodEvent(String init_datetime, long init_epoch_time, Mood init_mood, String init_reason,
                     String init_trigger, SocialSituation.State init_situation){
        this(init_datetime, init_epoch_time, init_mood, init_reason);
        trigger = init_trigger;
        situation = init_situation;
    }
    public String getDatetime(){
        return datetime;
    }
    public long getEpochTime(){
        return epoch_time;
    }
    public Mood getMood(){
        return mood;
    }
    public String getReason(){
        return reason;
    }
    public String getTrigger(){
        return trigger;
    }
    public SocialSituation.State getSituation(){
        return situation;
    }

    public void setDatetime(String new_datetime){
        datetime = new_datetime;
    }
    public void getEpochTime(long new_epoch_time){
        epoch_time = new_epoch_time;
    }
    public void setMood(Mood new_mood){
        mood = new_mood;
    }
    public void setReason(String new_reason){
        reason = new_reason;
    }
    public void setTrigger(String new_trigger){
        trigger = new_trigger;
    }
    public void setSituation(SocialSituation.State new_situation){
        situation = new_situation;
    }

}
