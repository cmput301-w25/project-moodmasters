package com.example.moodmasters.Events.AddMoodCommentScreen;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Views.AddCommentActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AddMoodCommentScreenCancelEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller){
        AddCommentActivity add_comment_activity = (AddCommentActivity) context;
        add_comment_activity.finish();
    }
}
