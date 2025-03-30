package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.MoodEventCreateCommentEvent;
import com.example.moodmasters.Events.ViewCommentsEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Objects.ObjectsMisc.CommentAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.moodmasters.Views.CommentAdapterManager;

import java.util.ArrayList;
import java.util.List;

public class ViewMoodCommentActivity extends AppCompatActivity implements MVCView {
    private static MoodEvent mood_event;
    private static int position;
    private ListView commentListView;
    private static CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private FirebaseFirestore db;
    private String username;

    public void initialize(MVCModel model) {
        Participant user = (Participant) model.getBackendObject(BackendObject.State.USER);
        username = user.getUsername();
    }

    public void update(MVCModel model) {
        // not necessary, nothing to update
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mood_comment); // Ensure this XML layout is used
        mood_event = ViewCommentsEvent.getMoodEvent();
        position = ViewCommentsEvent.getPosition();

        System.out.println(mood_event.getMoodEventString());
        db = FirebaseFirestore.getInstance();

        // Initialize ListView and the comment list
        commentListView = findViewById(R.id.view_comment_list);
        if (commentListView == null) {
            Log.e("ViewMoodCommentActivity", "commentListView is null!");
        } else {
            Log.d("ViewMoodCommentActivity", "commentListView is initialized"); }
        commentList = mood_event.getComments();
        if (commentList == null) {
            Log.e("ViewMoodCommentActivity", "commentList is null!");
        } else {
            Log.d("ViewMoodCommentActivity", "commentList is initialized"); }

        // Initialize the adapter with the custom layout and data
        commentAdapter = new CommentAdapter(this, commentList, db);
        if (commentAdapter == null) {
            Log.e("ViewMoodCommentActivity", "commentAdapter is null!");
        } else {
            Log.d("ViewMoodCommentActivity", "commentAdapter is initialized" + commentAdapter);
        }
        if (commentListView.getAdapter() == null) {
            // commentListView.setAdapter(commentAdapter);
            CommentAdapterManager.setAdapter(commentAdapter);
            Log.d("ViewMoodCommentActivity", "Setting adapter to ListView");


        } else {
            Log.d("ViewMoodCommentActivity", "Adapter already set, skipping.");
        }


        // Initialize the "X" button to close the activity
        ImageButton xButton = findViewById(R.id.view_mood_x_button);
        xButton.setOnClickListener(v -> finish());

        // Initialize the "Add Comment" button
        Button addCommentButton = findViewById(R.id.add_comment_button);
        addCommentButton.setOnClickListener(v -> {
            controller.execute(new MoodEventCreateCommentEvent(mood_event, position), this);

        });

    }

}
