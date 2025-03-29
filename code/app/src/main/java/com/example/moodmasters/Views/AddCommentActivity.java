package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.AddCommentConfirmEvent;
import com.example.moodmasters.Events.MoodEventCreateCommentEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Objects.ObjectsApp.Comment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddCommentActivity extends AppCompatActivity implements MVCView {
    private static MoodEvent mood_event;
    private static int position;
    private EditText commentEditText;
    private Button cancelButton;
    private Button okButton;
    private String username;
    private String timestamp;

    // Firestore instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void initialize(MVCModel model) {
        Participant user = (Participant) model.getBackendObject(BackendObject.State.USER);
        username = user.getUsername();
    }

    public void update(MVCModel model) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mood_comment);  // Use add_mood_comment.xml

        timestamp = getCurrentTimestamp();
        controller.addBackendView(this, BackendObject.State.USER);

        mood_event = MoodEventCreateCommentEvent.getMoodEvent();
        position = MoodEventCreateCommentEvent.getPosition();
        System.out.println(mood_event.getMoodEventString()); // debugging print

        // Initialize UI components
        commentEditText = findViewById(R.id.comment_edit_text);
        cancelButton = findViewById(R.id.cancel_button);
        okButton = findViewById(R.id.ok_button);


        // TODO: retrieve the passed MoodEvent Object

        // Get the list of comments from the MoodEvent Object
        List<Comment> comments = mood_event.getComments();


        // Handle Cancel Button Click
        cancelButton.setOnClickListener(v -> {
            // Close this activity without sending anything back
            setResult(RESULT_CANCELED);
            controller.removeBackendView(this);
            finish();
        });

        // Handle OK Button Click
        okButton.setOnClickListener(v -> {
            // Get the comment typed by the user
            String newCommentContent = commentEditText.getText().toString();

            // Create the new Comment object with username, timestamp, and the new content
            Comment newComment = new Comment(username, timestamp, newCommentContent);

            // TODO: Add new comment to the list of comments in MoodEvent Object
            comments.add(newComment);
            mood_event.setComments(comments);
            // TODO: Update the MoodEvent with this new comment
            controller.execute(new AddCommentConfirmEvent(mood_event, position), this);

        });
    }

    // Method to get the current timestamp in desired format
    private String getCurrentTimestamp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Use LocalDateTime to get the current timestamp in a human-readable format
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return now.format(formatter);  // Returns timestamp like "2025-03-25 14:30:45"
        } else {
            // Fallback for devices below Android O (API 26)
            // Return timestamp in milliseconds
            long timestampMillis = System.currentTimeMillis();
            return String.valueOf(timestampMillis);  // Returns timestamp in milliseconds
        }
    }
}
