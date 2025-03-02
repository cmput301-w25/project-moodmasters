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
    private Emotion emotion;
    private Color color;
    public enum Emotion{
        HAPPY,
        SAD,
        ANGRY,
        SCARED,
        DISGUSTED,
        CONFUSED,
        ASHAMED,
        SUPRISED
    }
    public enum Color{
        YELLOW,
        BLUE,
        RED,
        BLACK,
        GREEN,
        PURPLE,
        PINK,
        ORANGE
    }

    public Mood(Emotion init_emotion, Color init_color){
        color = init_color;
        emotion = init_emotion;
    }
    public Emotion getEmotion(){
        return emotion;
    }
    public Color getColor(){
        return color;
    }
    public void setEmotion(Emotion new_emotion){
        emotion = new_emotion;
    }
    public void setColor(Color new_color){
        color = new_color;
    }
}
