package com.example.moodmasters.Events.AddMoodCommentScreen;

import android.content.Context;
import android.icu.util.Calendar;
import android.widget.EditText;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Objects.ObjectsBackend.MoodFollowingList;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.AddMoodCommentScreen.AddMoodCommentScreenActivity;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for handling the UI interactions related to add comment button in the
 * AddMoodCommentScreen
 * */
public class AddMoodCommentScreenOkEvent implements MVCController.MVCEvent {
    private MoodEvent mood_event;
    private int position;
    private ArrayList<Comment> comments_list;
    private String mood_event_list_type;
    public AddMoodCommentScreenOkEvent(MoodEvent init_mood_event, int init_position, ArrayList<Comment> init_comments_list, String init_mood_event_list_type){
        mood_event = init_mood_event;
        position = init_position;
        comments_list = init_comments_list;
        mood_event_list_type = init_mood_event_list_type;
    }
    /**
     * This function contains all the code for adding a mood event comment once the ok button has been
     * hit on the AddMoodCommentScreen
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        AddMoodCommentScreenActivity activity = (AddMoodCommentScreenActivity) context;

        EditText comment_text = activity.findViewById(R.id.comment_edit_text);
        String comment_string = comment_text.getText().toString().trim();

        if (comment_string.isEmpty()){
            comment_text.setError("Comments Cannot Be Empty");
            return;
        }

        long epoch_time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(epoch_time);
        DateFormat format = new SimpleDateFormat("MMM dd yyyy | HH:mm");
        String datetime = format.format(calendar.getTime());
        String username = ((Participant) model.getBackendObject(BackendObject.State.USER)).getUsername();

        Comment new_comment = new Comment(username, comment_string, datetime);
        MoodEvent new_mood_event = new MoodEvent(mood_event.getDatetime(), mood_event.getEpochTime(), mood_event.getMood(),
                mood_event.getIsPublic(), mood_event.getReason(), mood_event.getSituation(), mood_event.getLocation(),
                mood_event.getUsername(), mood_event.getPhotoString(), new ArrayList<Comment>());
        if (mood_event_list_type.equals("MoodHistoryList")){
            MoodHistoryList creator_mood_history = (MoodHistoryList) model.getBackendObject(BackendObject.State.MOODHISTORYLIST) ;
            MoodEvent old_mood_event = creator_mood_history.getObjectPosition(position);
            creator_mood_history.replaceObjectPosition(position, new_mood_event);
            creator_mood_history.removeDatabaseData(model.getDatabase(), old_mood_event, (v, w) -> {});
            comments_list.add(new_comment);
            new_mood_event.setComments(comments_list);
            creator_mood_history.addDatabaseData(model.getDatabase(), new_mood_event, (v, w) -> {});
            model.notifyViews(BackendObject.State.MOODHISTORYLIST);
        }
        else if (mood_event_list_type.equals("MoodFollowingList")){
            FollowingList following_list = (FollowingList) model.getBackendObject(BackendObject.State.FOLLOWINGLIST);
            MoodFollowingList mood_following_list = (MoodFollowingList) model.getBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
            Participant creator = following_list.getParticipant(mood_event.getUsername());
            MoodHistoryList creator_mood_history = creator.getMoodHistoryList();
            List<MoodEvent> creator_mood_history_list = creator_mood_history.getList();
            System.out.println("AddMoodCommentScreenOkEvent CONTAINS: " + creator_mood_history_list.contains(mood_event));
            // have to code this manually instead of using replaceObjectBackendList, might be better to use dependency injection for that method,
            // and also need to create method for replacing object with another object in MVCBackendList
            int mood_history_position = creator_mood_history_list.indexOf(mood_event);
            creator_mood_history.replaceObjectPosition(mood_history_position, new_mood_event);
            mood_following_list.replaceObjectPosition(position, new_mood_event);
            creator_mood_history.removeDatabaseData(model.getDatabase(), mood_event, (v, w) -> {});
            comments_list.add(new_comment);
            new_mood_event.setComments(comments_list);
            creator_mood_history.addDatabaseData(model.getDatabase(), new_mood_event, (v, w) -> {});

            model.notifyViews(BackendObject.State.MOODFOLLOWINGLIST);
        }
        else{
            throw new InvalidParameterException("Error: AddMoodCommentScreenOkEvent mood_event_list_type is invalid with value of " + mood_event_list_type);
        }
        activity.finish();
    }
}
