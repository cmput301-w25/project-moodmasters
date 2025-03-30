package com.example.moodmasters.Views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.moodmasters.Events.CommentViewingScreen.CommentViewingScreenAddCommentEvent;
import com.example.moodmasters.Events.CommentViewingScreen.CommentViewingScreenAddCommentEvent;
import com.example.moodmasters.Events.CommentViewingScreen.CommentViewingScreenCancelEvent;
import com.example.moodmasters.Events.MoodEventViewCommentsEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.PhotoDecoderEncoder;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CommentViewingActivity extends AppCompatActivity implements MVCView {
    private MoodEvent mood_event;
    private int position;
    private ArrayList<Comment> comments_list;
    private CommentViewingAdapter comments_adapter;
    private String mood_event_list_type;
    public CommentViewingActivity(){
        super();
        mood_event = MoodEventViewCommentsEvent.getMoodEvent();
        position = MoodEventViewCommentsEvent.getPosition();
        mood_event_list_type = MoodEventViewCommentsEvent.getMoodEventListType();
        comments_list = new ArrayList<Comment>(mood_event.getComments()); // new comment list on replacement
    }
    public void initialize(MVCModel model){
        return;
    }

    public void update(MVCModel model){
        // not really necessary but feels proper
        if (mood_event_list_type.equals("MoodHistoryList")){
            mood_event = model.getFromBackendList(BackendObject.State.MOODHISTORYLIST, position);
        }
        if (mood_event_list_type.equals("MoodFollowingList")){
            mood_event = model.getFromBackendList(BackendObject.State.MOODFOLLOWINGLIST, position);
        }
    }
    public void setScreen(){
        TextView emoji_view = findViewById(R.id.view_mood_emoji_label);
        TextView mood_view = findViewById(R.id.view_mood_emotion_label);
        TextView date_view = findViewById(R.id.view_mood_date_time_label);
        Toolbar layout_view = findViewById(R.id.view_mood_toolbar);
        Mood mood = mood_event.getMood();

        emoji_view.setText(mood.getEmoticon());
        mood_view.setText(mood.getEmotionString());
        date_view.setText(mood_event.getDatetime());
        int background_color = ContextCompat.getColor(this, mood.getColor());
        layout_view.setBackgroundColor(background_color);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mood_comment);  // What it looks like when you click "Comments"
        setScreen();
        if (mood_event_list_type.equals("MoodHistoryList")){
            controller.addBackendView(this, BackendObject.State.MOODHISTORYLIST);
        }
        if (mood_event_list_type.equals("MoodFollowingList")){
            controller.addBackendView(this, BackendObject.State.MOODFOLLOWINGLIST);
        }
        // Reference the ListView by the correct ID
        ListView comments_list_view = findViewById(R.id.view_comment_list); // ListView for the comments
        Button add_comment_button = findViewById(R.id.add_comment_button); // Add comment button

        // Initialize the list of comments
        comments_adapter = new CommentViewingAdapter(this, comments_list, mood_event_list_type); // Converts each item in the ArrayList to be displayed onto screen
        comments_list_view.setAdapter(comments_adapter);  // Set the adapter for the ListView

        // Handle "Add Comment" Button Click
        add_comment_button.setOnClickListener(v -> {
            controller.execute(new CommentViewingScreenAddCommentEvent(mood_event, position, comments_list, mood_event_list_type), this);
        });
        // Initialize the x_button to be used if the user wants to leave the comments
        ImageButton x_button = findViewById(R.id.view_mood_x_button);
        // Set an OnClickListener to handle the button click
        x_button.setOnClickListener(v -> {
            controller.execute(new CommentViewingScreenCancelEvent(), this);
        });
    }
    public CommentViewingAdapter getArrayAdapter(){
        return comments_adapter;
    }
}
