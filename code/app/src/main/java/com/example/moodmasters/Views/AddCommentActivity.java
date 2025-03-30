package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.AddMoodCommentScreen.AddMoodCommentScreenCancelEvent;
import com.example.moodmasters.Events.CommentViewingScreen.CommentViewingScreenAddCommentEvent;
import com.example.moodmasters.Events.AddMoodCommentScreen.AddMoodCommentScreenOkEvent;
import com.example.moodmasters.Events.CommentViewingScreen.CommentViewingScreenAddCommentEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.R;

import java.util.ArrayList;

public class AddCommentActivity extends AppCompatActivity implements MVCView {
    private MoodEvent mood_event;
    private int position;
    private ArrayList<Comment> comments_list;

    public AddCommentActivity(){
        super();
        mood_event = CommentViewingScreenAddCommentEvent.getMoodEvent();
        position = CommentViewingScreenAddCommentEvent.getPosition();
        comments_list = CommentViewingScreenAddCommentEvent.getCommentList();
    }
    public void initialize(MVCModel model) {
        // not necessary, nothing to initialize
    }

    public void update(MVCModel model) {
        // not necessary, nothing to update
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mood_comment);  // Use add_mood_comment.xml

        // Initialize UI components
        Button cancel_button = findViewById(R.id.cancel_button);
        Button ok_button = findViewById(R.id.ok_button);

        // Handle Cancel Button Click
        cancel_button.setOnClickListener(v -> {
            // Close this activity without sending anything back
            controller.execute(new AddMoodCommentScreenCancelEvent(), this);
        });

        // Handle OK Button Click
        ok_button.setOnClickListener(v -> {
            controller.execute(new AddMoodCommentScreenOkEvent(mood_event, position, comments_list), this);
        });
    }

}
