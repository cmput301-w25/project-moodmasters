package com.example.moodmasters.Events.ViewProfileScreen;

import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.ViewProfileScreen.ViewProfileScreenActivity;

public class ViewProfileScreenFollowEvent implements MVCController.MVCEvent {
    private String status;
    public ViewProfileScreenFollowEvent(String init_status){
        status = init_status;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ViewProfileScreenActivity activity = (ViewProfileScreenActivity) context;
        Button follow_button = activity.findViewById(R.id.followButton);
        if (status.equals("Requested")) {
            Toast.makeText(context, "Follow request already sent", Toast.LENGTH_SHORT).show();
            return;
        }
        if (status.equals("Following")) {
            Toast.makeText(context, "Already following", Toast.LENGTH_SHORT).show();
            return;
        }
        model.createDatabaseDataBackendObject(BackendObject.State.FOLLOWREQUEST, (w, v) -> {
            if (v){
                Toast.makeText(context, "Follow request sent!", Toast.LENGTH_SHORT).show();
                follow_button.setText("Requested");
            }
            else{
                Toast.makeText(context, "Error sending request", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
