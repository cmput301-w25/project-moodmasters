package com.example.moodmasters.Events.CommentViewingScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.AddCommentActivity;
import com.example.moodmasters.Views.CommentViewingActivity;

public class CommentViewingScreenCancelEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        CommentViewingActivity comment_viewing_activity = (CommentViewingActivity) context;
        model.removeView(comment_viewing_activity);
        model.removeView(comment_viewing_activity.getArrayAdapter());
        comment_viewing_activity.finish();
    }
}
