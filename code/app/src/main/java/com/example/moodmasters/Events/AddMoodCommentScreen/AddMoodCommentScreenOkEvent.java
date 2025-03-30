package com.example.moodmasters.Events.AddMoodCommentScreen;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Objects.ObjectsBackend.MoodFollowingList;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.AddCommentActivity;
import com.example.moodmasters.Views.AlterMoodEventActivity;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        AddCommentActivity activity = (AddCommentActivity) context;

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
        comments_list.add(new_comment);
        MoodEvent new_mood_event = new MoodEvent(mood_event.getDatetime(), mood_event.getEpochTime(), mood_event.getMood(),
                mood_event.getIsPublic(), mood_event.getReason(), mood_event.getSituation(), mood_event.getLocation(),
                mood_event.getUsername(), mood_event.getPhotoString(), comments_list);
        if (mood_event_list_type.equals("MoodHistoryList")){
            model.replaceObjectBackendList(BackendObject.State.MOODHISTORYLIST, position, new_mood_event);
        }
        else if (mood_event_list_type.equals("MoodFollowingList")){
            FollowingList following_list = (FollowingList) model.getBackendObject(BackendObject.State.FOLLOWINGLIST);
            MoodFollowingList mood_following_list = (MoodFollowingList) model.getBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
            Participant creator = following_list.getParticipant(mood_event.getUsername());
            MoodHistoryList creator_mood_history = creator.getMoodHistoryList();
            List<MoodEvent> creator_mood_history_list = creator_mood_history.getList();

            // have to code this manually instead of using replaceObjectBackendList, might be better to use dependency injection for that method,
            // and also need to create method for replacing object with another object in MVCBackendList
            int mood_history_position = creator_mood_history_list.indexOf(mood_event);
            creator_mood_history.replaceObjectPosition(mood_history_position, new_mood_event);
            creator_mood_history.removeDatabaseData(model.getDatabase(), mood_event);
            creator_mood_history.addDatabaseData(model.getDatabase(), new_mood_event);

            mood_following_list.replaceObjectPosition(position, new_mood_event);

            model.notifyViews(BackendObject.State.MOODFOLLOWINGLIST);
        }
        else{
            throw new InvalidParameterException("Error: AddMoodCommentScreenOkEvent mood_event_list_type is invalid with value of " + mood_event_list_type);
        }
        activity.finish();
    }
}
