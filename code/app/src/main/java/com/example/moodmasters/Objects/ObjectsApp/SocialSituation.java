package com.example.moodmasters.Objects.ObjectsApp;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a class that represents all possible social situations that a MoodEvent can have.
 */
public class SocialSituation {
    public enum State{
        NONE,
        ALONE,
        PAIR,
        SEVERAL,
        CROWD
    }

    /**
     * This returns the passed situation State as a String.
     * @param conv_social_state
     *  This is the situation as a State to be returned as a string.
     * @return
     *  Return the passed situation State as a String.
     */
    public static String getString(SocialSituation.State conv_social_state){
        if (conv_social_state == SocialSituation.State.NONE){
            return "None";
        }
        else if (conv_social_state == SocialSituation.State.ALONE){
            return "Alone";
        }
        else if (conv_social_state == SocialSituation.State.PAIR){
            return "Pair";
        }
        else if (conv_social_state == SocialSituation.State.SEVERAL){
            return "Several";
        }
        else if (conv_social_state == SocialSituation.State.CROWD){
            return "Crowd";
        }
        return "Error";         /* Impossible to get here */
    }

    /**
     * This returns the passed situation String as a State.
     * @param social_situation_string
     *  This is the situation as a String to be returned as a State.
     * @return
     *  Return the passed situation String as a State.
     */
    public static SocialSituation.State fromStringToSocialState(String social_situation_string){
        if (social_situation_string.equalsIgnoreCase("None")){
            return SocialSituation.State.NONE;
        }
        else if (social_situation_string.equalsIgnoreCase("Alone")){
            return SocialSituation.State.ALONE;
        }
        else if (social_situation_string.equalsIgnoreCase("Pair")){
            return SocialSituation.State.PAIR;
        }
        else if (social_situation_string.equalsIgnoreCase("Several")){
            return SocialSituation.State.SEVERAL;
        }
        else if (social_situation_string.equalsIgnoreCase("Crowd")){
            return SocialSituation.State.CROWD;
        }
        else{
            throw new IllegalArgumentException("Error: Invalid String given to Emotion.fromStringToEmotionState");
        }
    }

    /**
     * This returns a List of all situations as Stings
     * @return
     *  Return a List containing all situations as Strings
     */
    public static List<String> getStringList(){
        List<String> social_situation_list = new ArrayList<String>(SocialSituation.State.values().length);
        int i = 0;
        for (SocialSituation.State social_situation : SocialSituation.State.values()) {
            social_situation_list.add(i, SocialSituation.getString(social_situation));
            i++;
        }
        return social_situation_list;
    }
}
