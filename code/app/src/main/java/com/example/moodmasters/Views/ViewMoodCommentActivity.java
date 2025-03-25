package com.example.moodmasters.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.moodmasters.R;

import java.util.ArrayList;

public class ViewMoodCommentActivity extends AppCompatActivity {

    private ListView commentListView;
    private ArrayAdapter<String> commentAdapter;
    private ArrayList<String> commentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mood_comment); // Ensure this XML layout is used

        // Initialize ListView and data
        commentListView = findViewById(R.id.view_comment_list);
        commentList = new ArrayList<>();
        commentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, commentList);
        commentListView.setAdapter(commentAdapter);

        // Initialize "Add a Comment" button
        Button addCommentButton = findViewById(R.id.add_comment_button);
        addCommentButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewMoodCommentActivity.this, AddCommentActivity.class);
            startActivity(intent);
        });

        ImageButton xButton = findViewById(R.id.view_mood_x_button);

        // Set an OnClickListener to handle the button click
        xButton.setOnClickListener(v -> {
                finish();
        });

    }
}
