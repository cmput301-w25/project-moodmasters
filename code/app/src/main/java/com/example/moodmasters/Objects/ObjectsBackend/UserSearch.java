package com.example.moodmasters.Objects.ObjectsBackend;

import android.widget.Toast;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Class that is responsible for helping search for users during user search in the UserSearchScreen
 * */
public class UserSearch extends MVCBackend implements MVCDatabase.Fetch {
    List<String> participant_list_string;
    List<Participant> participant_list;
    String keyword;
    String username;
    public UserSearch(String init_username){
        participant_list_string = new ArrayList<String>();
        participant_list = new ArrayList<Participant>();
        keyword = null;
        username = init_username;
    }
    @Override
    public void fetchDatabaseData(MVCDatabase database, MVCModel model, OnSuccessFetchListener listener) {
        CollectionReference users_ref = model.getDatabase().getCollection();
        users_ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                for (QueryDocumentSnapshot document : task.getResult()) {
                    String firestore_username = document.getId().trim(); // Get username from Firestore document ID

                    // If username starts with the search query, add it
                    if (firestore_username.toLowerCase(Locale.ROOT).startsWith(keyword) && !firestore_username.equals(username)) {
                        participant_list_string.add(firestore_username);
                        participant_list.add(new Participant(firestore_username));
                    }
                }

                listener.onSuccess(UserSearch.this, true);

            }
            else {
                listener.onSuccess(UserSearch.this, false);
            }
        });
    }
    public List<String> getParticipantListString(){
        return participant_list_string;
    }
    public void setParticipantListString(List<String> new_participant_list_string){
        participant_list_string = new_participant_list_string;
    }

    public List<Participant> getParticipantList(){
        return participant_list;
    }
    public void setParticipantList(List<Participant> new_participant_list){
        participant_list = new_participant_list;
    }
    public Participant getParticipant(String participant_username){
        for (Participant participant: participant_list){
            if (participant.getUsername().equals(participant_username)){
                return participant;
            }
        }
        return null;
    }
    public String getKeyword(){
        return keyword;
    }
    public void setKeyword(String new_keyword){
        keyword = new_keyword;
    }
}
