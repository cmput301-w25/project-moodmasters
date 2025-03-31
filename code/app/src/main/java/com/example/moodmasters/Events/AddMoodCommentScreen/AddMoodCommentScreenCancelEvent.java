package com.example.moodmasters.Events.AddMoodCommentScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.AddMoodCommentScreen.AddMoodCommentScreenActivity;

public class AddMoodCommentScreenCancelEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        AddMoodCommentScreenActivity add_comment_activity = (AddMoodCommentScreenActivity) context;
        add_comment_activity.finish();
    }
}
