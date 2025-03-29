package com.example.moodmasters.Events;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Objects.ObjectsMisc.CommentAdapter;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.AddCommentActivity;
import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.Views.ViewMoodCommentActivity;
import com.google.android.gms.maps.model.LatLng;


import java.util.List;

public class AddCommentConfirmEvent implements MVCController.MVCEvent {
    private MoodEvent mood_event;
    private int position;
    public AddCommentConfirmEvent(MoodEvent init_mood_event, int init_position){
        mood_event = init_mood_event;
        position = init_position;
}
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        AddCommentActivity activity = (AddCommentActivity) context;

        // Fetch comments from current MoodEvent
        List<Comment> updatedComments = mood_event.getComments();
        CommentAdapter commentAdapter = ViewMoodCommentActivity.getAdapter();

        Participant user = ((Participant) model.getBackendObject(BackendObject.State.USER));


        MoodEvent new_mood_event = new MoodEvent(mood_event.getDatetime(), mood_event.getEpochTime(), mood_event.getMood(),
                mood_event.getIsPublic(), mood_event.getReason(), mood_event.getSituation(), mood_event.getLocation(), user.getUsername(), mood_event.getComments());

        model.replaceObjectBackendList(BackendObject.State.MOODHISTORYLIST, position, new_mood_event);
        commentAdapter.notifyDataSetChanged();
        ((AddCommentActivity) context).finish();
        }
    }
