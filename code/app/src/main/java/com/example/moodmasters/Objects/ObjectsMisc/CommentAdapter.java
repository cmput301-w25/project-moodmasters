package com.example.moodmasters.Objects.ObjectsMisc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends ArrayAdapter<Comment> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public CommentAdapter(Context context, List<Comment> comments) {
        super(context, 0, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the current comment item
        Comment currentComment = getItem(position);

        // Check if the view is being reused, otherwise inflate a new one
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_list_item, parent, false);
        }

        // Get the TextViews for username, timestamp, and content
        TextView usernameText = convertView.findViewById(R.id.comment_username);
        TextView timestampText = convertView.findViewById(R.id.comment_timestamp);
        TextView contentText = convertView.findViewById(R.id.comment_content);

        // Set the text for username, timestamp, and content
        if (currentComment != null) {
            usernameText.setText(currentComment.getUsername());
            // Convert the timestamp to a formatted string
            String formattedTimestamp = dateFormat.format(currentComment.getTimestamp());
            timestampText.setText(formattedTimestamp);
            contentText.setText(currentComment.getContent());
        }

        return convertView;
    }
}