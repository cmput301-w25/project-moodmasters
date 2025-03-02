package com.example.moodmasters.Objects.ObjectsApp;

import java.time.LocalDate;
import java.time.LocalTime;

public class MoodEvent {
    private LocalDate date;
    private LocalTime time;
    private Mood mood;
    private String trigger;
    private SocialSituation situation;
    private Participant participant;        /*Member isn't really necessary for halfway milestone but will be for map implementation*/

    public enum SocialSituation{
        ALONE,
        PAIR,
        SEVERAL,
        CROWD
    }

    public MoodEvent(LocalDate init_date, LocalTime init_time, Mood init_mood, Participant init_participant){
        date = init_date;
        time = init_time;
        mood = init_mood;
        participant = init_participant;
    }
    public MoodEvent(LocalDate init_date, LocalTime init_time, Mood init_mood, Participant init_participant, String init_trigger){
        this(init_date, init_time, init_mood, init_participant);
        trigger = init_trigger;
    }
    public MoodEvent(LocalDate init_date, LocalTime init_time, Mood init_mood, Participant init_participant, SocialSituation init_situation){
        this(init_date, init_time, init_mood, init_participant);
        situation = init_situation;
    }
    public MoodEvent(LocalDate init_date, LocalTime init_time, Mood init_mood, Participant init_participant, String init_trigger, SocialSituation init_situation){
        this(init_date, init_time, init_mood, init_participant);
        trigger = init_trigger;
        situation = init_situation;
    }
    public LocalDate getDate(){
        return date;
    }
    public LocalTime getTime(){
        return time;
    }
    public Mood getMood(){
        return mood;
    }
    public String getTrigger(){
        return trigger;
    }
    public SocialSituation getSituation(){
        return situation;
    }
    public Participant getParticipant(){
        return participant;
    }

    public void setDate(LocalDate new_date){
        date = new_date;
    }
    public void setTime(LocalTime new_time){
        time = new_time;
    }
    public void setMood(Mood new_mood){
        mood = new_mood;
    }
    public void setTrigger(String new_trigger){
        trigger = new_trigger;
    }
    public void setSituation(SocialSituation new_situation){
        situation = new_situation;
    }
    public void setParticipant(Participant new_participant){
        participant = new_participant;
    }

}
