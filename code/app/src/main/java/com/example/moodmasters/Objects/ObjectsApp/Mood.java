package com.example.moodmasters.Objects.ObjectsApp;

import java.util.HashMap;

/**
 * This is a class that represents a single mood.
 * */
public class Mood {
    private Emotion.State emotion;
    private int color;
    private int emoticon;

    /**
     * Mood constructor.
     * @param init_emotion
     *  This is the Mood's emotional state.
     * @param init_color
     *  This is the Mood's color.
     * @param init_emoticon
     *  This is the Mood's emoticon.
     */
    public Mood(Emotion.State init_emotion, int init_color, int init_emoticon){
        color = init_color;
        emotion = init_emotion;
        emoticon = init_emoticon;
    }

    /**
     * Mood constructor.
     * @param map
     *  This is a HashMap retrieved from the Firebase database containing mood information.
     */
    public Mood(HashMap map){
        color = (int)(long) map.get("color");
        emotion = Emotion.fromStringToEmotionState((String) map.get("emotion"));
        emoticon = (int)(long) map.get("emoticon");
    }

    /**
     * emotion getter.
     */
    public Emotion.State getEmotion(){
        return emotion;
    }

    /**
     * color getter.
     */
    public int getColor(){
        return color;
    }

    /**
     * emoticon getter.
     */
    public int getEmoticon(){ return emoticon;}
    public void setEmotion(Emotion.State new_emotion){
        emotion = new_emotion;
    }

    /**
     * color setter.
     */
    public void setColor(int new_color){
        color = new_color;
    }

    /**
     * emoticon setter.
     */
    public void setEmoticon(int new_emoticon){
        emoticon = new_emoticon;
    }

    /**
     * Returns emotion as a string.
     * @return
     *  Return emotion as a string.
     */
    public String getEmotionString(){
        return Emotion.getString(emotion);
    }
}
