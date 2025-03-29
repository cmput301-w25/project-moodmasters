package com.example.moodmasters.Objects.ObjectsMisc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> comments;
    private FirebaseFirestore db;

    public CommentAdapter(Context context, List<Comment> comments, FirebaseFirestore db) {
        this.context = context;
        this.comments = comments;
        this.db = db;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Sets the layout that we want for the comment (has username, timestamp and username)
        // Responsible for creating or reusing a view for each item in the listview
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);
        }

        // Find the views for username, timestamp, and content
        TextView usernameTextView = convertView.findViewById(R.id.comment_username);
        TextView timestampTextView = convertView.findViewById(R.id.comment_timestamp);
        TextView contentTextView = convertView.findViewById(R.id.comment_content);

        // Get the current comment
        Comment comment = comments.get(position);

        // Set the data for each item
        usernameTextView.setText(comment.getUsername());
        timestampTextView.setText(comment.getTimestamp());
        contentTextView.setText(comment.getContent());

        return convertView;
    }
}
