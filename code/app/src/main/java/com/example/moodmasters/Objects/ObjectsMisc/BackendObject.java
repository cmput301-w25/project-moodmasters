package com.example.moodmasters.Objects.ObjectsMisc;

import com.example.moodmasters.MVC.MVCModel;

public class BackendObject {
    public enum State{
        USER,           /* the currently logged in participant which will be defined as user*/
        MOODLIST,          /* list that contains all of the 8 possible moods */
        FOLLOWINGLIST,
        MOODHISTORYLIST,
        MOODFOLLOWINGLIST,       /* used later for final checkpoint */
        MOODMAP
    }

    public static String getString(BackendObject.State backend_object){
        if (backend_object == BackendObject.State.USER){
            return "USER";
        }
        if (backend_object == BackendObject.State.MOODLIST){
            return "MOODLIST";
        }
        if (backend_object == BackendObject.State.FOLLOWINGLIST){
            return "FOLLOWINGLIST";
        }
        if (backend_object == BackendObject.State.MOODHISTORYLIST){
            return "MOODHISTORYLIST";
        }
        if (backend_object == BackendObject.State.MOODFOLLOWINGLIST){
            return "MOODFOLLOWINGLIST";
        }
        if (backend_object == BackendObject.State.MOODMAP){
            return "MOODMAP";
        }
        return "Error";         /* impossible to get here */
    }
}
