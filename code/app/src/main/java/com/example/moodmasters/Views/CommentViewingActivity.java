package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.R;

import java.util.ArrayList;

public class CommentViewingActivity extends AppCompatActivity {

    private ListView commentsListView;
    private Button addCommentButton;
    private ArrayList<String> commentsList;
    private ArrayAdapter<String> commentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mood_comment);  // This is your comment viewing layout

        // Reference the ListView by the correct ID
        commentsListView = findViewById(R.id.view_comment_list);

        addCommentButton = findViewById(R.id.add_comment_button); // Reference the Add Comment button

        commentsList = new ArrayList<>();
        commentsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, commentsList);
        commentsListView.setAdapter(commentsAdapter);  // Set the adapter for the ListView

        // Handle "Add Comment" Button Click
        addCommentButton.setOnClickListener(v -> {
            // Launch Add Comment screen (comment_add.xml)
            Intent intent = new Intent(CommentViewingActivity.this, AddCommentActivity.class);
            startActivityForResult(intent, 1);  // Start the add comment activity for result
        });
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
            String comment = data.getStringExtra("new_comment");
            commentsList.add(comment);  // Add the new comment to the list
            commentsAdapter.notifyDataSetChanged();  // Refresh the ListView
        }
    }
}


