package com.example.moodmasters.Events.UserSearchScreen;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsBackend.UserSearch;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A class that will search firebase participants based on username
 * */

public class UserSearchScreenOkEvent implements MVCController.MVCEvent{
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        Activity activity = (Activity) context;
        EditText search_input = activity.findViewById(R.id.searchInput);
        String input_query = search_input.getText().toString();

        if (input_query.trim().isEmpty()) {
            Toast.makeText(activity, "Enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        final String search_query = input_query.trim().toLowerCase(Locale.ROOT); // Case-insensitive search
        ((UserSearch) model.getBackendObject(BackendObject.State.USERSEARCH)).setKeyword(search_query);
        model.fetchDatabaseDataBackendObject(BackendObject.State.USERSEARCH, (w, v) -> {
            UserSearch user_search = (UserSearch) w;
            if (v && !user_search.getParticipantListString().isEmpty()){
                model.notifyViews(BackendObject.State.USERSEARCH);
            }
            else if (v && user_search.getParticipantListString().isEmpty()){
                Toast.makeText(activity, "No matching users found.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(activity, "Error searching users.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}