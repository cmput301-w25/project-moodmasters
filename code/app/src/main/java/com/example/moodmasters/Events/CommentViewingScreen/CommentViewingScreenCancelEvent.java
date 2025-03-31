package com.example.moodmasters.Events.CommentViewingScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.CommentViewingScreen.CommentViewingScreenActivity;

public class CommentViewingScreenCancelEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        CommentViewingScreenActivity comment_viewing_activity = (CommentViewingScreenActivity) context;
        model.removeView(comment_viewing_activity);
        model.removeView(comment_viewing_activity.getArrayAdapter());
        comment_viewing_activity.finish();
    }
}
