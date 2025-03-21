package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.SignupLoginScreenActivity;
import com.example.moodmasters.Views.MoodHistoryListActivity;

public class LoginScreenOkEvent implements MVCEvent {
    private static String username;

    public static String getUsername(){
        return username;
    }

    @Override
    public void executeEvent(Context context, MVCModel backend, MVCController controller) {
        // Cast context to the login activity
        SignupLoginScreenActivity loginActivity = (SignupLoginScreenActivity) context;
        // Get the EditText for the username input
        EditText enteredUsername = loginActivity.findViewById(R.id.signup_login_enter_username);
        // Retrieve and trim the username input
        username = enteredUsername.getText().toString().trim();

        // Check if the username is empty and show an error if so
        if (username.isEmpty()) {
            enteredUsername.setError("Username cannot be empty");
            Toast.makeText(context, "Please enter a username.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the backend user object (assuming that createBackendObject sets up a Participant)
        backend.createBackendObject(BackendObject.State.USER);
        // Optionally, add logic to check if the user exists or to create a new one

        // Start the MoodHistoryListActivity after successful login
        Intent intent = new Intent(loginActivity, MoodHistoryListActivity.class);
        context.startActivity(intent);
    }
}
