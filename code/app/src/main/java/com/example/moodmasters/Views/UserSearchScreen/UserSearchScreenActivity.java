package com.example.moodmasters.Views.UserSearchScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.UserSearchScreen.UserSearchScreenCancelEvent;
import com.example.moodmasters.Events.UserSearchScreen.UserSearchScreenOkEvent;
import com.example.moodmasters.Events.UserSearchScreen.UserSearchScreenViewProfileEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsBackend.UserSearch;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.ViewProfileScreen.ViewProfileScreenActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserSearchScreenActivity extends AppCompatActivity implements MVCView {
    private ArrayAdapter<String> adapter;

    public void update(MVCModel model){
        adapter.notifyDataSetChanged();
    }

    public void initialize(MVCModel model){
        UserSearch user_search = (UserSearch) model.getBackendObject(BackendObject.State.USERSEARCH);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user_search.getParticipantListString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_search_screen);
        controller.addBackendView(this, BackendObject.State.USERSEARCH);

        ListView search_results_list_view = findViewById(R.id.searchResultsListView);
        ImageButton back_button = findViewById(R.id.backButton);
        Button search_button = findViewById(R.id.searchButton);

        search_results_list_view.setAdapter(adapter);

        // Retrieve the current participant's username

        // Handle Back Button
        back_button.setOnClickListener(v -> {
            controller.execute(new UserSearchScreenCancelEvent(), this);
        });

        // Handle Search Button
        search_button.setOnClickListener(v -> {
            controller.execute(new UserSearchScreenOkEvent(), this);
        });

        search_results_list_view.setOnItemClickListener((parent, view, position, id) -> {
            controller.execute(new UserSearchScreenViewProfileEvent((String) parent.getItemAtPosition(position)), this);
        });
    }

}