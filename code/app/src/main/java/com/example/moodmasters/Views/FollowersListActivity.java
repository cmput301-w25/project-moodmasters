package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FollowersListActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ListView listView;
    private FollowersListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers_list_screen);
        String username = getIntent().getStringExtra("username");
        db = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.followers_list_view);
        adapter = new FollowersListAdapter(this, new ArrayList<>(), username);
        listView.setAdapter(adapter);



        if (username == null) {
            Toast.makeText(this, "Missing username", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("participants").document(username).collection("followers")
                .get().addOnSuccessListener(snapshot -> {
                    List<String> followers = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshot.getDocuments()) {
                        followers.add(doc.getId());
                    }
                    adapter.clear();
                    adapter.addAll(followers);
                    adapter.notifyDataSetChanged();
                });

        ImageButton back = findViewById(R.id.back_button);
        back.setOnClickListener(v -> finish());
    }
}