package com.example.moodmasters.Events.UserSearchScreen;

import android.content.Context;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.AlterMoodEventActivity;
import com.example.moodmasters.Views.UserSearchActivity;

public class UserSearchScreenCancelEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ((UserSearchActivity) context).finish();
    }
}
