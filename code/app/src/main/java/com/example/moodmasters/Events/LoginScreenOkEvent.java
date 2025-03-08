package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
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

public class LoginScreenOkEvent implements MVCController.MVCEvent {
    private static String username;
    private FirebaseFirestore db;
    private static CollectionReference participants_ref;
    private static DocumentReference doc_ref;

    public static String getUsername(){
        return username;
    }

    public DocumentReference getDocRef() {
        db = FirebaseFirestore.getInstance();
        participants_ref = db.collection("participants");
        doc_ref = participants_ref.document(username);
        return doc_ref;
    }

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        // Allow the same action for both Login and Sign Up (minimal changes)
        EditText entered_username = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_enter_username);
        TextView label = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_label);
        username = entered_username.getText().toString().trim();


        if (username.isEmpty()) {
            Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        this.getDocRef().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();

                    if (label.getText().equals("Sign Up")) {
                        // Sign Up: Check if the username already exists
                        if (snapshot.exists()) {
                            Toast.makeText(context, "Username already taken. Please choose another.", Toast.LENGTH_SHORT).show();
                        } else {
                            model.createBackendObject(BackendObject.State.USER);
                            model.getBackendObject(BackendObject.State.USER).setDatabaseData(doc_ref, snapshot);
                            context.startActivity(new Intent((SignupLoginScreenActivity) context, MoodHistoryListActivity.class));
                        }
                    } else {
                        // Login: Check if the username exists
                        if (snapshot.exists()) {
                            if (model.getBackendObject(BackendObject.State.USER) == null) {
                                model.createBackendObject(BackendObject.State.USER);
                            }
                            model.getBackendObject(BackendObject.State.USER).setDatabaseData(doc_ref, snapshot);
                            context.startActivity(new Intent((SignupLoginScreenActivity) context, MoodHistoryListActivity.class));

                        } else {
                            Toast.makeText(context, "Username not found. Please sign up first.", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });
    }
}