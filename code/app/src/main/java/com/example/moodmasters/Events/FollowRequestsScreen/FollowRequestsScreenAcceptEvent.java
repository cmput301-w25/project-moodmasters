package com.example.moodmasters.Events.FollowRequestsScreen;

import android.content.Context;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.FollowRequestList;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
/**
 * Class that is responsible for handling the UI interaction of accepting a follow request in the FollowRequestsScreen
 * */
public class FollowRequestsScreenAcceptEvent implements MVCController.MVCEvent {
    private int position;
    public FollowRequestsScreenAcceptEvent(int init_position){
        position = init_position;
    }
    /**
     * Executes code that is necessary for the UI interaction of accepting a new follower, updates the
     * database so that the participant is now following the user
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        FollowRequestList follow_request_list = (FollowRequestList) model.getBackendObject(BackendObject.State.FOLLOWREQUESTLIST);
        model.addDatabaseDataBackendObject(BackendObject.State.FOLLOWREQUESTLIST, follow_request_list.getFollowRequestsPosition(position), (w, v)-> {
            if (v){
                model.notifyViews(BackendObject.State.FOLLOWREQUESTLIST);
                Toast.makeText(context, "Follow request accepted!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "There Was An Error In Accepting The Follow Request, Please Try Again At Another Time", Toast.LENGTH_LONG).show();
            }

        });
    }
}
