package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AddCommentActivity extends AppCompatActivity implements MVCView {

    private EditText commentEditText;
    private Button cancelButton;
    private Button okButton;
    private String username;
    private String timestamp;

    public void initialize(MVCModel model){
        Participant user = (Participant) model.getBackendObject(BackendObject.State.USER);
        username = user.getUsername();
    }
    public void update(MVCModel model){
        // not necessary, nothing to update
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mood_comment);  // Use add_mood_comment.xml
        timestamp = getCurrentTimestamp();
        controller.addBackendView(this, BackendObject.State.USER);
        commentEditText = findViewById(R.id.comment_edit_text);
        cancelButton = findViewById(R.id.cancel_button);
        okButton = findViewById(R.id.ok_button);

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

            // Return the comment to the previous activity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("username", newComment.getUsername());
            resultIntent.putExtra("timestamp", newComment.getTimestamp());
            resultIntent.putExtra("content", newComment.getContent());
            setResult(RESULT_OK, resultIntent);
            finish();
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
