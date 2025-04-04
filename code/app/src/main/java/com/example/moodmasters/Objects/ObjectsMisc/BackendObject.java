package com.example.moodmasters.Objects.ObjectsMisc;

/**
 * A Static Class that stores the enums related to the backend objects and corresponding methods for each enum
 * */
public class BackendObject {
    public enum State{
        USER,           /* the currently logged in participant which will be defined as user*/
        MOODLIST,          /* list that contains all of the 8 possible moods */
        FOLLOWINGLIST,
        FOLLOWERLIST,
        MOODHISTORYLIST,
        MOODFOLLOWINGLIST,       /* used later for final checkpoint */
        MOODMAP,
        COUNTERS,
        USERSEARCH,
        FOLLOWREQUEST,
        FOLLOWREQUESTLIST
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
        if (backend_object == BackendObject.State.FOLLOWERLIST){
            return "FOLLOWERLIST";
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
        if (backend_object == BackendObject.State.COUNTERS){
            return "COUNTERS";
        }
        if (backend_object == BackendObject.State.USERSEARCH){
            return "USERSEARCH";
        }
        if (backend_object == BackendObject.State.FOLLOWREQUEST){
            return "FOLLOWREQUEST";
        }
        if (backend_object == BackendObject.State.FOLLOWREQUESTLIST){
            return "FOLLOWREQUESTLIST";
        }
        return "Error";         /* impossible to get here */
    }
}
