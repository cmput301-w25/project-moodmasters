package com.example.moodmasters.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CommentViewingAdapter extends ArrayAdapter<Comment> implements MVCView {
    private Context context;
    private String mood_event_list_type;
    public CommentViewingAdapter(Context context, ArrayList<Comment> comments, String init_mood_event_list_type) {
        super(context, 0, comments);
        this.context = context;
        this.mood_event_list_type = init_mood_event_list_type;
        if (mood_event_list_type.equals("MoodHistoryList")){
            controller.addBackendView(this, BackendObject.State.MOODHISTORYLIST);
        }
        if (mood_event_list_type.equals("MoodFollowingList")){
            controller.addBackendView(this, BackendObject.State.MOODFOLLOWINGLIST);
        }
    }
    public void initialize(MVCModel model){
        return;
    }

    public void update(MVCModel model){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Sets the layout that we want for the comment (has username, timestamp and username)
        // Responsible for creating or reusing a view for each item in the listview
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);
        }

        // Find the views for username, timestamp, and content
        TextView username_text_view = convertView.findViewById(R.id.comment_username);
        TextView timestamp_text_view = convertView.findViewById(R.id.comment_timestamp);
        TextView content_text_view = convertView.findViewById(R.id.comment_content);

        // Get the current comment
        Comment comment = getItem(position);

        // Set the data for each item
        username_text_view.setText(comment.getUsername());
        timestamp_text_view.setText(comment.getTimestamp());
        content_text_view.setText(comment.getContent());

        return convertView;
    }
}
