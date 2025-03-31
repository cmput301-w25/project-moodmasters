package com.example.moodmasters.Objects.ObjectsBackend;

import android.widget.Toast;

import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCBackendList;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Class that represents the following list which has a multitude of uses like for the mood following list,
 * nad for the following activity
 * */
public class FollowingList extends MVCBackend implements MVCDatabase.Fetch, MVCDatabase.Remove{
    private String username;
    private List<Participant> following_list;
    private ArrayList<String> following_list_usernames;
    private MoodFollowingList mood_following_list;
    public static Boolean[] temp_bool_list; // temporary to support this synchronous model to tell when the mood following list is ready


    public FollowingList(String username) {
        this.username = username;
        this.following_list = new ArrayList<Participant>();
        this.following_list_usernames = new ArrayList<String>();
        this.mood_following_list = new MoodFollowingList();
    }
    /**
     * Function for fetching the following list of a user and each participant of the following lists mood history
     * @param database
     *  Database object that stores documents and collections
     * @param model
     *  MVCModel that is needed to get other necessary data that might be needed in the function
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    @Override
    public void fetchDatabaseData(MVCDatabase database, MVCModel model, OnSuccessFetchListener listener){
        database.addCollection("participants");
        database.addDocument(username);
        CollectionReference following_ref = database.getDocument(username).collection("following");
        following_ref.get().addOnCompleteListener(task -> {
            following_list.clear();
            following_list_usernames.clear();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    following_list.add(new Participant(document.getId()));
                    following_list_usernames.add(document.getId());
                }
                if (following_list.isEmpty()){
                    listener.onSuccess(FollowingList.this, true);
                    return;
                }
                boolean[] bool_array = new boolean[following_list.size()];
                Arrays.fill(bool_array, false);
                for (Participant followee: following_list){
                    followee.fetchDatabaseData(database, model, (v, w) -> {
                        if (w){
                            int position = following_list.indexOf(followee);
                            bool_array[position] = true;
                            if (!Arrays.asList(bool_array).contains(false)){
                                listener.onSuccess(FollowingList.this, true);
                            }
                        }
                        else {
                            listener.onSuccess(FollowingList.this, false);
                        }
                    });
                }
            }
            else{
                listener.onSuccess(FollowingList.this, false);
            }
        });
    }
    /**
     * Function for removing a follower from a the following list in the database
     * @param database
     *  Database object that stores documents and collections
     * @param object
     *  object to remove to the database
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    @Override
    public <T> void removeDatabaseData(MVCDatabase database, T object, OnSuccessRemoveListener listener) {
        String followee = (String) object;
        DocumentReference user_ref = database.getDocument(username);
        database.addDocument(followee);
        DocumentReference followee_ref = database.getDocument(followee);
        user_ref.collection("following").document(followee)
                .delete().addOnSuccessListener(aVoid -> {
                    followee_ref.collection("followers").document(username).delete();
                    listener.onSuccess(FollowingList.this, true);
                })
                .addOnFailureListener(e -> {
                    listener.onSuccess(FollowingList.this, false);
                });;
    }

    private void updateMoodFollowingList(){
        ArrayList<MoodEvent> followees_recent_mood_events = mood_following_list.getList();
        followees_recent_mood_events.clear();
        for (Participant participant: following_list){
            MoodHistoryList participant_history_list = participant.getMoodHistoryList();
            List<MoodEvent> followee_history_list = participant_history_list.getList();
            followee_history_list.sort((a, b) -> Long.compare(a.getEpochTime(), b.getEpochTime()));
            List<MoodEvent> most_recent_mood_events = new ArrayList<MoodEvent>();
            int i = 0;
            for (MoodEvent mood_event: followee_history_list){
                System.out.println(mood_event.getMoodEventString());
                if (mood_event.getIsPublic()){
                    most_recent_mood_events.add(mood_event);
                    i++;
                }
                if (i == MoodFollowingList.getMoodEventsPerParticipant()){
                    break;
                }
            }
            followees_recent_mood_events.addAll(most_recent_mood_events);
        }
    }
    /**
     * Gets the mood following list updated to the most recent version locally
     * @return
     *  Returns the MoodFollowingList produced
     * */
    public MoodFollowingList getMoodFollowingList(){
        updateMoodFollowingList();
        return mood_following_list;
    }
    /**
     * Gets the Participant in the following list associated with the username
     * @param username
     *  the username of the participant that is wanted to be retrieved
     * @return
     *  The participant with the same username
     * */
    public Participant getParticipant(String username){
        for (Participant participant: following_list){
            if (participant.getUsername().equals(username)){
                return participant;
            }
        }
        return null;
    }
    /**
     * Gets the current following list
     * @return
     *  The current following list
     * */
    public List<Participant> getFollowingList(){
        return following_list;
    }
    /**
     * @param username
     *  participant with this username to remove
     *
     * */
    public void removeFollowingListElement(String username){
        Participant removable;
        int index = 0;
        for (Participant participant: following_list){
            if (participant.getUsername().equals(username)){
                break;
            }
            index++;
        }
        following_list.remove(index);
        following_list_usernames.remove(index);
    }
    /**
     * Getter for people following
     * @return
     *  List corresponding to the names of all participants in the following list
     * */
    public ArrayList<String> getFollowingListUsernames(){
        return following_list_usernames;
    }
}
