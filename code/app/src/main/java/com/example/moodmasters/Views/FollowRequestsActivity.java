package com.example.moodmasters.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.example.moodmasters.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class FollowRequestsActivity extends AppCompatActivity {
    private ListView requestsListView;
    private FirebaseFirestore db;
    private List<String> followRequestsList;
    private FollowRequestsAdapter adapter;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_requests);

        requestsListView = findViewById(R.id.follow_requests_list);
        db = FirebaseFirestore.getInstance();
        currentUsername = LoginScreenOkEvent.getUsername();
        followRequestsList = new ArrayList<>();
        adapter = new FollowRequestsAdapter(this, followRequestsList, db);
        requestsListView.setAdapter(adapter);

        loadFollowRequests();

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }

    private void loadFollowRequests() {
        currentUsername = LoginScreenOkEvent.getUsername();
        db.collection("participants")
                .document(currentUsername)
                .collection("followRequests")
                .get()
                .addOnCompleteListener(task -> {
                    followRequestsList.clear();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            followRequestsList.add(document.getId());
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}