package com.example.moodmasters.Events.LoginSignupScreen;

import static android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.SignupLoginScreenActivity;
import com.example.moodmasters.Views.MoodHistoryListActivity;

import java.security.InvalidParameterException;
import java.util.Objects;

public class LoginSignupScreenOkEvent implements MVCController.MVCEvent {
    private static String username;
    private String password;
    private boolean signup;
    private String action;
    private Context context;
    private MVCModel model;
    private boolean activity_launched = false;

    public static String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
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
        EditText entered_password = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_enter_password);
        TextView label = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_ok_button);
        username = entered_username.getText().toString().trim();
        password = entered_password.getText().toString().trim();


        if (username.isEmpty()) {
            Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()){
            Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        ConnectivityManager connectivityManager = getSystemService(context, ConnectivityManager.class);
        Network currentNetwork = connectivityManager.getActiveNetwork();
        if (currentNetwork == null || !Objects.requireNonNull(connectivityManager.getNetworkCapabilities(currentNetwork)).hasCapability(NET_CAPABILITY_VALIDATED)) {
            Toast.makeText(context, "You're offline! Please connect to the internet", Toast.LENGTH_SHORT).show();
            return;
        }

        signup = label.getText().equals("Sign Up");
        model.createBackendObject(BackendObject.State.USER);
    }
    public void afterDatabaseQuery(){
        EditText entered_username = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_enter_username);
        EditText entered_password = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_enter_password);
        if (action.equals("SignupError")){
            model.removeBackendObject(BackendObject.State.USER);
            Toast.makeText(context, "Username already taken. Please choose another.", Toast.LENGTH_SHORT).show();
        }
        else if (action.equals("LoginError")){
            model.removeBackendObject(BackendObject.State.USER);
            Toast.makeText(context, "Username not found. Please sign up first.", Toast.LENGTH_SHORT).show();
        }
        else if (action.equals("PasswordError")){
            model.removeBackendObject(BackendObject.State.USER);
            Toast.makeText(context, "Invalid Password. Please Try Again", Toast.LENGTH_SHORT).show();
        }
        else if (action.equals("GoMoodHistoryActivity")){
            activity_launched = true;
            context.startActivity(new Intent((SignupLoginScreenActivity) context, MoodHistoryListActivity.class));
            entered_username.setText("");
            entered_password.setText("");
            model.createBackendObject(BackendObject.State.FOLLOWINGLIST);
        }
        else{
            throw new InvalidParameterException("Error: invalid action in LoginScreenOkEvent");
        }
    }
}