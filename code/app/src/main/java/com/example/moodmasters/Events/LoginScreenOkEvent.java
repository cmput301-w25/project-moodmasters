package com.example.moodmasters.Events;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.SignupLoginScreenActivity;
import com.example.moodmasters.MoodHistoryListActivity;

public class LoginScreenOkEvent implements MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel backend, MVCController controller) {
        // TODO: Verify user entered proper username in database
        context.startActivity(new Intent((SignupLoginScreenActivity) context, MoodHistoryListActivity.class));
    }
}
