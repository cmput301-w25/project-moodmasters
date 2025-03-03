package com.example.moodmasters.Objects.ObjectsApp;

import java.util.ArrayList;
import java.util.List;

public class SocialSituation {
    public enum State{
        NONE,
        ALONE,
        PAIR,
        SEVERAL,
        CROWD
    }
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

    public static SocialSituation.State fromStringToSocialState(String social_situation_string){
        if (social_situation_string.equals("None")){
            return SocialSituation.State.NONE;
        }
        else if (social_situation_string.equals("Alone")){
            return SocialSituation.State.ALONE;
        }
        else if (social_situation_string.equals("Pair")){
            return SocialSituation.State.PAIR;
        }
        else if (social_situation_string.equals("Several")){
            return SocialSituation.State.SEVERAL;
        }
        else if (social_situation_string.equals("Crowd")){
            return SocialSituation.State.CROWD;
        }
        else{
            throw new IllegalArgumentException("Error: Invalid String given to Emotion.fromStringToEmotionState");
        }
    }

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
