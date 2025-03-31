package com.example.moodmasters.Events.FollowRequestsScreen;

import android.content.Context;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.FollowRequestList;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;

public class FollowRequestsScreenIgnoreEvent implements MVCController.MVCEvent {
    private int position;
    public FollowRequestsScreenIgnoreEvent(int init_position){
        position = init_position;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        FollowRequestList follow_request_list = (FollowRequestList) model.getBackendObject(BackendObject.State.FOLLOWREQUESTLIST);
        model.removeDatabaseDataBackendObject(BackendObject.State.FOLLOWREQUESTLIST, follow_request_list.getFollowRequestsPosition(position), (w, v)-> {
            if (v){
                model.notifyViews(BackendObject.State.FOLLOWREQUESTLIST);
                Toast.makeText(context, "Follow request ignored!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "There Was An Error In Ignoring The Follow Request, Please Try Again At Another Time", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
