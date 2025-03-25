package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.R;
import com.example.moodmasters.Objects.ObjectsMisc.CommentAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ViewMoodCommentActivity extends AppCompatActivity {

    private ListView commentListView;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> commentList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mood_comment); // Ensure this XML layout is used

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize ListView and the comment list
        commentListView = findViewById(R.id.view_comment_list);
        commentList = new ArrayList<>();

        // Initialize the adapter with the custom layout and data
        commentAdapter = new CommentAdapter(this, commentList, db);
        commentListView.setAdapter(commentAdapter);

        // Sample comments (Replace with actual data fetching logic)
        // commentList.add(new Comment("John Doe", "Jan 1, 2025 | 10:00 AM", "This is a comment!"));
        // commentList.add(new Comment("Jane Smith", "Jan 1, 2025 | 10:05 AM", "Another comment!"));

        // Notify adapter that the data has changed
        // commentAdapter.notifyDataSetChanged();

        // Initialize the "X" button to close the activity
        ImageButton xButton = findViewById(R.id.view_mood_x_button);
        xButton.setOnClickListener(v -> finish());

        // Initialize the "Add Comment" button
        Button addCommentButton = findViewById(R.id.add_comment_button);
            addCommentButton.setOnClickListener(v -> {
                // Launch Add Comment screen (comment_add.xml)
                // Log.d("ViewMoodCommentActivity", "button clicked");
                Intent intent = new Intent(ViewMoodCommentActivity.this, AddCommentActivity.class);
                //String username = "John Doe";
                // String timestamp = "Jan 1, 2025 | 10:10 AM";

                //Log.d("ViewMoodCommentActivity", "Username: " + username);
                //Log.d("ViewMoodCommentActivity", "Timestamp: " + timestamp);

                //intent.putExtra("username", username);
                //intent.putExtra("timestamp", timestamp);
                //startActivityForResult(intent, 1);

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

            // Create a new Comment object and add it to the list
            Comment newComment = new Comment(username, timestamp, content);
            commentList.add(newComment);

            // Notify the adapter that the data has changed
            commentAdapter.notifyDataSetChanged();
        }
    }
}
