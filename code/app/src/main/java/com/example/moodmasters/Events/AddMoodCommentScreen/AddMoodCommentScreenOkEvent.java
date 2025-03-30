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
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.AddCommentActivity;
import com.example.moodmasters.Views.AlterMoodEventActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AddMoodCommentScreenOkEvent implements MVCController.MVCEvent {
    private MoodEvent mood_event;
    private int position;
    private ArrayList<Comment> comments_list;

    public AddMoodCommentScreenOkEvent(MoodEvent init_mood_event, int init_position, ArrayList<Comment> init_comments_list){
        mood_event = init_mood_event;
        position = init_position;
        comments_list = init_comments_list;
    }

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        AddCommentActivity activity = (AddCommentActivity) context;

        EditText commment_text = activity.findViewById(R.id.comment_edit_text);
        String comment_string = commment_text.getText().toString().trim();

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
        model.replaceObjectBackendList(BackendObject.State.MOODHISTORYLIST, position, new_mood_event);
        activity.finish();
    }
}
