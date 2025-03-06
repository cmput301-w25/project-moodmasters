package com.example.moodmasters.Objects.ObjectsApp;

import java.util.HashMap;

/**
 * This is a class that represents a single mood.
 * */
public class Mood {
    private Emotion.State emotion;
    private int color;
    private int emoticon;

    public Mood(Emotion.State init_emotion, int init_color, int init_emoticon){
        color = init_color;
        emotion = init_emotion;
        emoticon = init_emoticon;
    }
    public Mood(HashMap map){
        color = (int)(long) map.get("color");
        emotion = Emotion.fromStringToEmotionState((String) map.get("emotion"));
        emoticon = (int)(long) map.get("emoticon");
    }
    public Emotion.State getEmotion(){
        return emotion;
    }
    public int getColor(){
        return color;
    }
    public int getEmoticon(){ return emoticon;}
    public void setEmotion(Emotion.State new_emotion){
        emotion = new_emotion;
    }
    public void setColor(int new_color){
        color = new_color;
    }
    public void setEmoticon(int new_emoticon){
        emoticon = new_emoticon;
    }
    public String getEmotionString(){
        return Emotion.getString(emotion);
    }

}
