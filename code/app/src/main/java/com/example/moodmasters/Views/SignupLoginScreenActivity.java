package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
/**
 * Startup activity for the app that allows a user to signup or login and access the app
 *
 * */
public class SignupLoginScreenActivity extends AppCompatActivity implements MVCView {
    private boolean isSignUp = true;

    public void update(MVCModel model) {
        // skip for now
    }

    public void initialize(MVCModel model) {
        // skip for now
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup_login_screen);
        controller.createBackendObject(BackendObject.State.MOODLIST);

        Button ok_button = findViewById(R.id.signup_login_ok_button);
        Button change_button = findViewById(R.id.signup_login_change_button);
        TextView label = findViewById(R.id.signup_login_label);

        // Set initial label text
        label.setText("Sign Up");

        ok_button.setOnClickListener(v -> {
            controller.execute(new LoginScreenOkEvent(), this);
        });

        // Toggle between Login and Sign Up
        change_button.setOnClickListener(v -> {
            isSignUp = !isSignUp;
            label.setText(isSignUp ? "Sign Up" : "Login");
        });
    }
}