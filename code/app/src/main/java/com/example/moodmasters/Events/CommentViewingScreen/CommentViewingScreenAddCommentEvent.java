package com.example.moodmasters.Events.CommentViewingScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.AddMoodCommentScreen.AddMoodCommentScreenActivity;

import java.util.ArrayList;

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
    public static MoodEvent getMoodEvent(){
        return mood_event;
    }
    public static int getPosition(){
        return position;
    }
    public static ArrayList<Comment> getCommentList(){
        return comments_list;
    }
    public static String getMoodEventListType(){
        return mood_event_list_type;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        Intent intent = new Intent(context, AddMoodCommentScreenActivity.class);
        context.startActivity(intent);
    }
}
