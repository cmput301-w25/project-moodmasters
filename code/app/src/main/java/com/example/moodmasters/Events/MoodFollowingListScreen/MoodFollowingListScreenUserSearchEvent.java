package com.example.moodmasters.Events.MoodFollowingListScreen;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.UserSearchScreen.UserSearchScreenActivity;

public class MoodFollowingListScreenUserSearchEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.createBackendObject(BackendObject.State.USERSEARCH);
        Intent intent = new Intent(context, UserSearchScreenActivity.class);
        context.startActivity(intent);
    }
}
