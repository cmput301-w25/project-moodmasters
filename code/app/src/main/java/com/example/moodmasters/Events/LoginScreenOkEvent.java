package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

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
        // TODO: Verify user entered proper username in database, just need to make sure field is non-empty and check if the username
        // is already in the database in the Participant.setDatabaseData method, if it isn't add it, else retrieve necessary data from
        // database (more specifically mood history list)
        EditText entered_username = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_enter_username);
        username = entered_username.getText().toString().trim();
        backend.createBackendObject(BackendObject.State.USER);
        context.startActivity(new Intent((SignupLoginScreenActivity) context, MoodHistoryListActivity.class));
    }
}
