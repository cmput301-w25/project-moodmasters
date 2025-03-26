package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.SignupLoginScreenActivity;
import com.example.moodmasters.Views.MoodHistoryListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.InvalidParameterException;

public class LoginScreenOkEvent implements MVCController.MVCEvent {
    private static String username;
    private boolean signup;
    private String action;
    private Context context;
    private MVCModel model;
    private boolean activity_launched = false;

    public static String getUsername(){
        return username;
    }
    public boolean getSignUp(){
        return signup;
    }

    public void setAction(String new_action){
        action = new_action;
    }
    public boolean getActivityLaunched(){
        return activity_launched;
    }

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        // Allow the same action for both Login and Sign Up (minimal changes)
        this.context = context;
        this.model = model;
        action = "";
        EditText entered_username = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_enter_username);
        TextView label = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_label);
        username = entered_username.getText().toString().trim();


        if (username.isEmpty()) {
            Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        signup = label.getText().equals("Sign Up");
        model.createBackendObject(BackendObject.State.USER);
    }
    public void afterDatabaseQuery(){
        EditText entered_username = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_enter_username);
        if (action.equals("SignupError")){
            model.removeBackendObject(BackendObject.State.USER);
            Toast.makeText(context, "Username already taken. Please choose another.", Toast.LENGTH_SHORT).show();
        }
        else if (action.equals("LoginError")){
            model.removeBackendObject(BackendObject.State.USER);
            Toast.makeText(context, "Username not found. Please sign up first.", Toast.LENGTH_SHORT).show();
        }
        else if (action.equals("GoMoodHistoryActivity")){
            activity_launched = true;
            context.startActivity(new Intent((SignupLoginScreenActivity) context, MoodHistoryListActivity.class));
            entered_username.setText("");
            model.createBackendObject(BackendObject.State.FOLLOWINGLIST);
        }
        else{
            throw new InvalidParameterException("Error: invalid action in LoginScreenOkEvent");
        }
    }
}