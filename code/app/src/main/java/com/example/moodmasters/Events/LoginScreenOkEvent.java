package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCEvent;
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

public class LoginScreenOkEvent implements MVCEvent {
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
    public void executeEvent(Context context, MVCModel backend, MVCController controller) {
        // TODO: Verify user entered proper username in database, just need to make sure field is non-empty and check if the username
        // is already in the database in the Participant.setDatabaseData method, if it isn't add it, else retrieve necessary data from
        // database (more specifically mood history list)
        TextView label = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_label);
        if (label.getText().equals("Sign Up")) {
            EditText entered_username = ((SignupLoginScreenActivity) context).findViewById(R.id.signup_login_enter_username);
            username = entered_username.getText().toString().trim();
            backend.createBackendObject(BackendObject.State.USER);
            this.getDocRef().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot snapshot = task.getResult();
                        backend.getBackendObject(BackendObject.State.USER).setDatabaseData(doc_ref, snapshot);
                        context.startActivity(new Intent((SignupLoginScreenActivity) context, MoodHistoryListActivity.class));
                    }
                }
            });
        }
    }
}
