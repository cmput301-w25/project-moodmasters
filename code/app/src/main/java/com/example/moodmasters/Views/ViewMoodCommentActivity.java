package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Objects.ObjectsMisc.CommentAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ViewMoodCommentActivity extends AppCompatActivity implements MVCView {

    private ListView commentListView;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> commentList;
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
        db = FirebaseFirestore.getInstance();
        // Initialize ListView and the comment list
        commentListView = findViewById(R.id.view_comment_list);
        commentList = new ArrayList<>();

        // Initialize the adapter with the custom layout and data
        commentAdapter = new CommentAdapter(this, commentList, db);
        commentListView.setAdapter(commentAdapter);


        // Initialize the "X" button to close the activity
        ImageButton xButton = findViewById(R.id.view_mood_x_button);
        xButton.setOnClickListener(v -> finish());

        // TODO: Fetch the MoodEvent from MoodEventViewingActivity
        // Initialize the "Add Comment" button
        Button addCommentButton = findViewById(R.id.add_comment_button);
            addCommentButton.setOnClickListener(v -> {
                // TODO: Send the MoodEvent to AddCommentActivity
                Intent intent = new Intent(ViewMoodCommentActivity.this, AddCommentActivity.class);


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
