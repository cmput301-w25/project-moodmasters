package com.example.moodmasters;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;

public class SignupLoginScreenActivity extends AppCompatActivity implements MVCView {
    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        // skip for now
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup_login_screen);
        // TODO: Add view to model via controller if it is found necessary
        Button ok_button = findViewById(R.id.signup_login_ok_button);
        ok_button.setOnClickListener(v ->{
            controller.execute(new LoginScreenOkEvent(), this);
        });
        // initialize backend here

    }
}