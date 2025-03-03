package com.example.moodmasters.Objects.ObjectsApp;

/**
 * There should be exactly 8 Mood object instances, one for each Emotion, i also
 * put the emotions and colors in order of which should be associated to which
 * but this can change with the programmers choice (happy associated with yellow,
 * sad associated with blue, ext), if you change any associations between emotions
 * and colors try to make it make sense (an example would be that angry is usually associated
 * with the color red, it wouldn't make sense to associate it with a bright yellow for example)
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
