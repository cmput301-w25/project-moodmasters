package com.example.moodmasters.Events.UserSearchScreen;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.FollowRequest;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsBackend.UserSearch;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.UserSearchScreen.UserSearchScreenActivity;
import com.example.moodmasters.Views.ViewProfileScreen.ViewProfileScreenActivity;

public class UserSearchScreenViewProfileEvent implements MVCController.MVCEvent {
    String selected_user;
    public UserSearchScreenViewProfileEvent(String init_selected_user){
        selected_user = init_selected_user;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.createBackendObject(BackendObject.State.FOLLOWREQUEST);
        ((FollowRequest) model.getBackendObject(BackendObject.State.FOLLOWREQUEST)).setTargetUsername(selected_user);
        model.fetchDatabaseDataBackendObject(BackendObject.State.FOLLOWREQUEST, (w, v) -> {
            if (!v){
                Toast.makeText(context, "Error Checking Follow Status, Cannot Open Profile", Toast.LENGTH_SHORT).show();
                return;
            }
            FollowRequest follow_request = (FollowRequest) w;
            Participant selected_participant = ((UserSearch) model.getBackendObject(BackendObject.State.USERSEARCH)).getParticipant(selected_user);
            Intent intent = new Intent(context, ViewProfileScreenActivity.class);
            selected_participant.fetchDatabaseData(model.getDatabase(), model, (w2, v2) -> {
                System.out.println("STATUS " + follow_request.getStatus());
                intent.putExtra("selected_user", selected_user);
                intent.putExtra("follow_request_status", follow_request.getStatus());
                context.startActivity(intent);
            });

        });
    }
}
