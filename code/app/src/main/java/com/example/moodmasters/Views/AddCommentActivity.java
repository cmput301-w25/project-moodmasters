package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.R;

public class AddCommentActivity extends AppCompatActivity {

    private EditText commentEditText;
    private Button cancelButton;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mood_comment);  // Use add_mood_comment.xml

        commentEditText = findViewById(R.id.comment_edit_text);
        cancelButton = findViewById(R.id.cancel_button);
        okButton = findViewById(R.id.ok_button);

        // Handle Cancel Button Click
        cancelButton.setOnClickListener(v -> {
            // Close this activity without sending anything back
            setResult(RESULT_CANCELED);
            finish();
        });

        // Handle OK Button Click
        okButton.setOnClickListener(v -> {
            // Get the comment typed by the user
            String newComment = commentEditText.getText().toString();

            // Return the comment to the previous activity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("new_comment", newComment);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
