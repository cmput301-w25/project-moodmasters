package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsMisc.CommentAdapter;
import com.example.moodmasters.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CommentViewingActivity extends AppCompatActivity {

    private ListView commentsListView;
    private Button addCommentButton;
    private ArrayList<Comment> commentsList;
    private CommentAdapter commentsAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mood_comment);  // What it looks like when you click "Comments"

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Reference the ListView by the correct ID
        commentsListView = findViewById(R.id.view_comment_list); // ListView for the comments
        addCommentButton = findViewById(R.id.add_comment_button); // Add comment button

        // Initialize the list of comments
        commentsList = new ArrayList<>(); // commentsList is the source of data for the comments
        commentsAdapter = new CommentAdapter(this, commentsList, db); // Converts each item in the ArrayList to be displayed onto screen
        commentsListView.setAdapter(commentsAdapter);  // Set the adapter for the ListView

        // Handle "Add Comment" Button Click
        addCommentButton.setOnClickListener(v -> {
            // Launch Add Comment screen (comment_add.xml)
            Intent intent = new Intent(CommentViewingActivity.this, AddCommentActivity.class);
            startActivityForResult(intent, 1);  // This starts the activity and expects a result back

        });
        // Initialize the xButton to be used if the user wants to leave the comments
        ImageButton xButton = findViewById(R.id.view_mood_x_button);
        // Set an OnClickListener to handle the button click
        xButton.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Get the added comment from AddCommentActivity
            String username = data.getStringExtra("username");
            String timestamp = data.getStringExtra("timestamp");
            String content = data.getStringExtra("content");

            // Create a new Comment object
            Comment newComment = new Comment(username, timestamp, content);

            // Add the new comment to the list
            commentsList.add(newComment);

            // Update the MoodEvent with this ??
            commentsAdapter.notifyDataSetChanged();  // Refresh the ListView
        }
    }
}
