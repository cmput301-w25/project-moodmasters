package com.example.moodmasters.Objects.ObjectsBackend;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a class that keeps track of MoodEvents in the current user's history.
 */
public class MoodHistoryList extends MoodEventList {
    private Participant participant;            /*wtf is vero talking about circular references are completely fine in java*/
    public MoodHistoryList(ArrayList<MoodEvent> list, Participant init_participant) {
        super(list);
        participant = init_participant;
    }
    public MoodHistoryList(Participant init_participant) {
        super();
        participant = init_participant;
    }
    public MoodHistoryList() {
        super();
    }
    public void removeDatabaseData(MVCDatabase database, Object object, OnSuccessRemoveListener listener){
        DocumentReference doc_ref = database.getDocument(participant.getUsername());
        doc_ref.update("list", FieldValue.arrayRemove(object)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess(MoodHistoryList.this, true);
                }
                else{
                    listener.onSuccess(MoodHistoryList.this, false);
                }
            }
        });
    }
    public void addDatabaseData(MVCDatabase database, Object object, OnSuccessAddListener listener){
        DocumentReference doc_ref = database.getDocument(participant.getUsername());
        doc_ref.update("list", FieldValue.arrayUnion(object)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess(MoodHistoryList.this, true);
                }
                else{
                    listener.onSuccess(MoodHistoryList.this, false);
                }
            }
        });
    }
}
