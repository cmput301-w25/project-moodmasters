package com.example.moodmasters.Objects.ObjectsBackend;

import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsMisc.CommentList;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class CommentHistoryList extends CommentList {

    /**
     * CommentHistoryList constructor.
     * @param list
     *  This is a list of the current user's MoodEvent comments.
     * @param doc_ref
     *  This is the DocumentReference which references the document where the user's
     *  data is stored.
     * @param snapshot
     *  This is the DocumentSnapshot of the document where the user's data is stored.
     */
    public CommentHistoryList(ArrayList<Comment> list, DocumentReference doc_ref, DocumentSnapshot snapshot) {
        super(list, doc_ref, snapshot);
    }

    /**
     * CommentHistoryList constructor.
     * @param doc_ref
     *  This is the DocumentReference which references the document where the user's
     *  data is stored.
     * @param snapshot
     *  This is the DocumentSnapshot of the document where the user's data is stored.
     */
    public CommentHistoryList(DocumentReference doc_ref, DocumentSnapshot snapshot) {
        super(doc_ref, snapshot);
    }

    public void setDatabaseData(DocumentReference doc_ref, DocumentSnapshot snapshot){
        // this is done by the Participant class
    }

    /**
     * This handles the updating of CommentHistoryList data into the database.
     * @param doc_ref
     *  This is the DocumentReference which references the document where the user's
     *  data is stored.
     */
    public void updateDatabaseData(DocumentReference doc_ref){
        doc_ref.set(this);
    }
}
