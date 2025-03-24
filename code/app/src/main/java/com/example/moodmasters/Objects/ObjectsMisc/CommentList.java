package com.example.moodmasters.Objects.ObjectsMisc;

import com.example.moodmasters.MVC.MVCBackendList;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public abstract class CommentList extends MVCBackendList<Comment> {
    private final FilterCommentList filter;

    // Default Constructor
    public CommentList() {filter = new FilterCommentList();}

    // Constructor w/ pre-existing list
    public CommentList(ArrayList<Comment> list, DocumentReference docRef, DocumentSnapshot snapshot){
        super(list, docRef, snapshot);
        filter = new FilterCommentList();

    }
    // Constructor for Firebase
    public CommentList(DocumentReference docRef, DocumentSnapshot snapshot){
        super(docRef, snapshot);
        filter = new FilterCommentList();
    }
    public void recentCommentList(){ // Filter by recency
        filter.filterByRecency(object_list);
    }

    public void revertRecentCommentList(){ // Revert filter
        filter.revertFilterByRecency(object_list);
    }

}
