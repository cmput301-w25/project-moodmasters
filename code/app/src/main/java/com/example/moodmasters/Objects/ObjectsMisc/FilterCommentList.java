package com.example.moodmasters.Objects.ObjectsMisc;

import com.example.moodmasters.Objects.ObjectsApp.Comment;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterCommentList {
    private Map<String, List<Comment>> filtered_comments;  // A map to store different filtered comment lists
    private List<Comment> originalComments;  // Store the original unfiltered list

    public FilterCommentList(){
        filtered_comments = new HashMap<>();
    }

    // Store the original list of comments so it can be reverted
    public void setOriginalComments(List<Comment> object_list) {
        this.originalComments = object_list;
    }

    // Filter by recency (most recent first)
    public void filterByRecency(List<Comment> object_list) {
        if (object_list == null || object_list.isEmpty()) {
            return;
        }

        // Sort comments by timestamp (most recent first)
        Collections.sort(object_list, (c1, c2) -> Long.compare(c2.getTimestamp(), c1.getTimestamp()));

        // Save the filtered list (filtered by recency)
        filtered_comments.put("recency", object_list);
    }

    // Revert to the original list of comments
    public void revertFilterByRecency(List<Comment> object_list) {
        if (originalComments != null) {
            // Clear the object_list and restore the original order of comments
            object_list.clear();
            object_list.addAll(originalComments);
        }
    }

}
