package com.example.moodmasters.Objects.ObjectsApp;

import com.example.moodmasters.Objects.ObjectsBackend.MoodList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;

import java.util.HashMap;

public class MoodEvent {
    private String datetime;
    private long epoch_time;            /* necessary for storing on database, there might be a better solution as opposed to increasing class size but this is fine for now*/
    private Mood mood;
    // Problem: since participant Has-A MoodEvent, MoodEvent cannot Have-A participant
    // (this creates an object cycle)
    //private Participant participant;
    private String reason;
    private String trigger;
    private SocialSituation.State situation;


    public MoodEvent(String init_datetime, long init_epoch_time, Mood init_mood, String init_reason){
        datetime = init_datetime;
        mood = init_mood;
        //participant = init_participant;
        epoch_time = init_epoch_time;
        reason = init_reason;
    }

    public MoodEvent(HashMap map){
        datetime = (String) map.get("datetime");
        mood = new Mood((HashMap) map.get("mood"));
        //participant = init_participant;
        epoch_time = (long) map.get("epochTime");
        reason = (String) map.get("reason");
        situation = (SocialSituation.State) map.get("situation");
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
//    public Participant getParticipant(){
//        return participant;
//    }
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
//    public void setParticipant(Participant new_participant){
//        participant = new_participant;
//    }
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
