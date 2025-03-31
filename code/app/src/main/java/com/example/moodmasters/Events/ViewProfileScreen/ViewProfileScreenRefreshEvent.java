package com.example.moodmasters.Events.ViewProfileScreen;

import android.content.Context;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.ViewProfileScreen.ViewProfileScreenActivity;

public class ViewProfileScreenRefreshEvent implements MVCController.MVCEvent{
    private Participant target_participant;
    public ViewProfileScreenRefreshEvent(Participant init_target_participant){
        target_participant = init_target_participant;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        ViewProfileScreenActivity activity = (ViewProfileScreenActivity) context;
        SwipeRefreshLayout swipe_container = activity.findViewById(R.id.swipe_container);
        target_participant.fetchDatabaseData(model.getDatabase(), model, (w, v) -> {
            swipe_container.setRefreshing(false);
            if (v){
                model.notifyViews(BackendObject.State.USERSEARCH);
            }
            else{
                Toast.makeText(context, "Error: Failed To Fetch From Database", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
