package com.example.moodmasters.Objects.ObjectsBackend;

import android.widget.Toast;

import com.example.moodmasters.Events.FollowersListScreen.FollowerListScreenRemoveEvent;
import com.example.moodmasters.MVC.MVCBackend;
import com.example.moodmasters.MVC.MVCBackendList;
import com.example.moodmasters.MVC.MVCDatabase;
import com.example.moodmasters.MVC.MVCModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
/**
 * Class that represents the follower list that is used for the follower list activity
 * */
public class FollowerList extends MVCBackend implements MVCDatabase.Fetch, MVCDatabase.Remove{
    private String username;
    private ArrayList<String> follower_list;

    public FollowerList(String init_username){
        username = init_username;
        follower_list = new ArrayList<String>();
    }
    public FollowerList(){
        username = null;
        follower_list = new ArrayList<String>();
    }

    /**
     * Function responsible for fetching from the database the amount of participants a user is following
     * @param database
     *  Database object that stores documents and collections
     * @param model
     *  MVCModel that is needed to get other necessary data that might be needed in the function
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    @Override
    public void fetchDatabaseData(MVCDatabase database, MVCModel model, OnSuccessFetchListener listener) {
        DocumentReference doc_ref = database.getDocument(username);
        doc_ref.collection("followers").get().addOnSuccessListener(snapshot -> {
            List<String> followers = new ArrayList<>();
            for (DocumentSnapshot doc : snapshot.getDocuments()) {
                followers.add(doc.getId());
            }
            follower_list.addAll(followers);
            listener.onSuccess(FollowerList.this, true);
        }).addOnFailureListener(v -> {
            listener.onSuccess(FollowerList.this, false);
        });
    }

    /**
     * Function responsible for removing a follower from a user's follower list
     * @param database
     *  Database object that stores documents and collections
     * @param object
     *  object to remove to the database
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    @Override
    public <T> void removeDatabaseData(MVCDatabase database, T object, OnSuccessRemoveListener listener) {
        String follower = (String) object;
        DocumentReference user_ref = database.getDocument(username);
        database.addDocument(follower);
        DocumentReference follower_ref = database.getDocument(follower);
        user_ref.collection("followers")
                .document(follower)        // mona
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // 2. Remove lisa from mona's following
                    follower_ref.collection("following").document(username).delete();
                    listener.onSuccess(FollowerList.this, true);
                })
                .addOnFailureListener(e -> {
                    listener.onSuccess(FollowerList.this, false);
                });
    }

    /**
     * Getter for returning follower list
     * @return
     *  Returns follower list
     * */
    public ArrayList<String> getFollowerList(){
        return follower_list;
    }
    /**
     * Gets rid of an element of the follow_list array
     * @param follower
     *  Element of array to remove
     * */
    public void removeFollowerListElement(String follower){
        follower_list.remove(follower);
    }
}
