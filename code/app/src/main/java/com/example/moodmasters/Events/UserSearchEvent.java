// com.example.moodmasters.Events.UserSearchEvent.java
package com.example.moodmasters.Events;

import android.content.Context;
import android.content.Intent;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Views.UserSearchActivity;

public class UserSearchEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        Intent intent = new Intent(context, UserSearchActivity.class);
        context.startActivity(intent);
    }
}