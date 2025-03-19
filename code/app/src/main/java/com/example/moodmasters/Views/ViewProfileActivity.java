package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.R;

public class ViewProfileActivity extends AppCompatActivity {

    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        usernameTextView = findViewById(R.id.usernameTextView);

        // Retrieve the selected username passed from the UserSearchActivity
        String selectedUser = getIntent().getStringExtra("selectedUser");

        // Display the selected username (You can add more user data here)
        if (selectedUser != null) {
            usernameTextView.setText(selectedUser);
        }
        findViewById(R.id.backButton).setOnClickListener(v -> {
            finish();  // Go back to the previous activity (UserSearchActivity)
        });
    }

}
