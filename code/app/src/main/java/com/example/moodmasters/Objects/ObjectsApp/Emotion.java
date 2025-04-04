package com.example.moodmasters.Objects.ObjectsApp;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a class that represents all possible emotions that a Mood can have.
 */
public class Emotion {
    public enum State {
        HAPPY,
        SAD,
        ANGRY,
        SCARED,
        DISGUSTED,
        CONFUSED,
        ASHAMED,
        SURPRISED
    }

    /**
     * This returns the passed emotion State as a String.
     * @param conv_emotion_state
     *  This is the emotion as a State to be returned as a string.
     * @return
     *  Return the passed emotion State as a String.
     */
    public static String getString(State conv_emotion_state){
        if (conv_emotion_state == State.HAPPY){
            return "Happy";
        }
        else if (conv_emotion_state == State.SAD){
            return "Sad";
        }
        else if (conv_emotion_state == State.ANGRY){
            return "Angry";
        }
        else if (conv_emotion_state == State.SCARED){
            return "Scared";
        }
        else if (conv_emotion_state == State.DISGUSTED){
            return "Disgusted";
        }
        else if (conv_emotion_state == State.CONFUSED){
            return "Confused";
        }
        else if (conv_emotion_state == State.ASHAMED){
            return "Ashamed";
        }
        else if (conv_emotion_state == State.SURPRISED){
            return "Surprised";
        }
        return "Error";
    }

    /**
     * This returns the passed emotion String as a State.
     * @param emotion_string
     *  This is the emotion as a String to be returned as a State.
     * @return
     *  Return the passed emotion String as a State.
     */
    public static State fromStringToEmotionState(String emotion_string){
        if (emotion_string.equalsIgnoreCase("Happy")){
            return State.HAPPY;
        }
        else if (emotion_string.equalsIgnoreCase("Sad")){
            return State.SAD;
        }
        else if (emotion_string.equalsIgnoreCase("Angry")){
            return State.ANGRY;
        }
        else if (emotion_string.equalsIgnoreCase("Scared")){
            return State.SCARED;
        }
        else if (emotion_string.equalsIgnoreCase("Disgusted")){
            return State.DISGUSTED;
        }
        else if (emotion_string.equalsIgnoreCase("Confused")){
            return State.CONFUSED;
        }
        else if (emotion_string.equalsIgnoreCase("Ashamed")){
            return State.ASHAMED;
        }
        else if (emotion_string.equalsIgnoreCase("Surprised")){
            return State.SURPRISED;
        }
        else{
            throw new IllegalArgumentException("Error: Invalid String given to Emotion.fromStringToEmotionState");
        }
    }

    /**
     * This returns a List of all emotions as Stings
     * @return
     *  Return a List containing all emotions as Strings
     */
    public static List<String> getStringList(){
        List<String> emotion_list = new ArrayList<String>(Emotion.State.values().length);
        int i = 0;
        for (Emotion.State emotion : Emotion.State.values()) {
            emotion_list.add(i, Emotion.getString(emotion));
            i++;
        }
        return emotion_list;
    }
}
