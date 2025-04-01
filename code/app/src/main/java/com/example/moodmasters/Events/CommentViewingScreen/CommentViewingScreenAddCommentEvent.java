package com.example.moodmasters.Events.CommentViewingScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.AddMoodCommentScreen.AddMoodCommentScreenActivity;

import java.util.ArrayList;
/**
 * Class that is responsible for handling the UI interaction choosing to add a comment on the
 * CommentViewingScreen so that the user can post a comment
 * */
public class CommentViewingScreenAddCommentEvent implements MVCController.MVCEvent {
    private static MoodEvent mood_event;
    private static int position;
    private static ArrayList<Comment> comments_list;
    private static String mood_event_list_type;

    public CommentViewingScreenAddCommentEvent(MoodEvent init_mood_event, int init_position, ArrayList<Comment> init_comments_list, String init_mood_event_list_type){
        mood_event = init_mood_event;
        position = init_position;
        comments_list = init_comments_list;
        mood_event_list_type = init_mood_event_list_type;
    }
    /**
     * Gets the mood event associated to this comment
     * @return
     *  MoodEvent field of this class passed along by previous activities
     * */
    public static MoodEvent getMoodEvent(){
        return mood_event;
    }
    /**
     * Gets the position associated to this comment
     * @return
     *  position field of this class passed along y previous activities
     * */
    public static int getPosition(){
        return position;
    }
    /**
     * Gets the comment list associated to this comment
     * @return
     *  comment list field of this class passed along y previous activities
     * */
    public static ArrayList<Comment> getCommentList(){
        return comments_list;
    }
    /**
     * Gets the mood event list type associated to the mood event associated to this comment
     * @return
     *  mood event list type either "MoodHistoryList" or "MoodFollowingList"
     * */
    public static String getMoodEventListType(){
        return mood_event_list_type;
    }
    /**
     * Executes code that is necessary for the UI interaction of allowing the user to attach a comment
     * to a mood event, this just brings up a new screen that will allow the user to do so (more specifically
     * the AddMoodCommentScreen)
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        Intent intent = new Intent(context, AddMoodCommentScreenActivity.class);
        context.startActivity(intent);
    }
}
