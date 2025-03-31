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

import com.example.moodmasters.Events.MoodHistoryListScreen.MoodHistoryListScreenShowEvent;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.LoginSignupScreen.LoginSignupScreenActivity;
import com.example.moodmasters.Views.MoodHistoryListScreen.MoodHistoryListScreenActivity;
import com.example.moodmasters.Views.LoginSignupScreen.LoginSignupScreenActivity;
import com.example.moodmasters.Views.MoodHistoryListScreen.MoodHistoryListScreenActivity;

import java.security.InvalidParameterException;
import java.util.Objects;

public class LoginSignupScreenOkEvent implements MVCController.MVCEvent {
    private static String username;
    private String password;
    private boolean signup;

    public static String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public boolean getSignUp(){
        return signup;
    }

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        // Allow the same action for both Login and Sign Up (minimal changes)
        EditText entered_username = ((LoginSignupScreenActivity) context).findViewById(R.id.signup_login_enter_username);
        EditText entered_password = ((LoginSignupScreenActivity) context).findViewById(R.id.signup_login_enter_password);
        TextView label = ((LoginSignupScreenActivity) context).findViewById(R.id.signup_login_ok_button);
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
        Participant user = (Participant) model.getBackendObject(BackendObject.State.USER);
        user.fetchDatabaseData(model.getDatabase(), model, (backend_object, result) -> {
            Participant participant = (Participant) backend_object;
            if (signup && result){
                model.removeBackendObject(BackendObject.State.USER);
                Toast.makeText(context, "Username already taken. Please choose another.", Toast.LENGTH_SHORT).show();
            }
            else if (!signup && !result){
                model.removeBackendObject(BackendObject.State.USER);
                Toast.makeText(context, "Username not found. Please sign up first.", Toast.LENGTH_SHORT).show();
            }
            else if (!signup && !password.equals(participant.getPassword())){
                model.removeBackendObject(BackendObject.State.USER);
                Toast.makeText(context, "Invalid Password. Please Try Again", Toast.LENGTH_SHORT).show();
            }
            else if (!signup && password.equals(participant.getPassword())){
                entered_username.setText("");
                entered_password.setText("");
                model.createBackendObject(BackendObject.State.FOLLOWINGLIST);
                model.fetchDatabaseDataBackendObject(BackendObject.State.FOLLOWINGLIST, (w, v) ->{
                    model.createBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
                });
                model.createBackendObject(BackendObject.State.FOLLOWERLIST);
                model.createBackendObject(BackendObject.State.COUNTERS);
                controller.execute(new MoodHistoryListScreenShowEvent(), context);
            }
            else if (signup && !result){
                participant.setPassword(password);
                participant.createDatabaseData(model.getDatabase(), model, (create_backend_object, create_result) -> {
                    if (create_result){
                        entered_username.setText("");
                        entered_password.setText("");
                        model.createBackendObject(BackendObject.State.FOLLOWINGLIST);
                        model.fetchDatabaseDataBackendObject(BackendObject.State.FOLLOWINGLIST, (w, v) ->{
                            model.createBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
                        });
                        model.createBackendObject(BackendObject.State.FOLLOWERLIST);
                        model.createBackendObject(BackendObject.State.COUNTERS);
                        controller.execute(new MoodHistoryListScreenShowEvent(), context);
                    }
                    else{
                        Toast.makeText(context, "There Was A Error During SignUp, Please Try Again At A Later Time", Toast.LENGTH_SHORT).show();
                    }

                });
            }
            else{
                throw new InvalidParameterException("Error: invalid action in LoginScreenOkEvent");
            }

        });
    }
}