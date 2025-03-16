package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.example.moodmasters.Events.UserSearchOkEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Participant; // Import Participant
import com.example.moodmasters.R;

import java.util.ArrayList;

public class UserSearchActivity extends AppCompatActivity {

    private EditText searchInput;
    private ListView searchResultsListView;
    private Button backButton, searchButton;
    private ArrayAdapter<String> adapter;
    private UserSearchOkEvent searchEvent;
    private Participant currentUser; // Store the current user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_search_screen);

        searchInput = findViewById(R.id.searchInput);
        searchResultsListView = findViewById(R.id.searchResultsListView);
        backButton = findViewById(R.id.backButton);
        searchButton = findViewById(R.id.searchButton);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        searchResultsListView.setAdapter(adapter);

        // Retrieve the current participants username
        currentUser = new Participant(LoginScreenOkEvent.getUsername());

        searchEvent = new UserSearchOkEvent(currentUser); // Begin searching

        // Handle Back Button
        backButton.setOnClickListener(v -> {
            finish();
        });


        // Handle Search Button
        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString();
            searchEvent.executeSearch(this, query, searchResultsListView, adapter);
        });
    }
}