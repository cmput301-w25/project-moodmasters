package com.example.moodmasters.Events.MoodEventViewingScreen;

import android.content.Context;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.MoodEventViewingScreen.MoodEventViewingScreenActivity;

public class MoodEventViewingScreenDeleteEvent implements MVCController.MVCEvent {
    private final MoodEvent moodEvent;
    private int position;

    public MoodEventViewingScreenDeleteEvent(MoodEvent moodEvent, int init_position) {
        this.moodEvent = moodEvent;
        this.position = init_position;
    }

    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        model.removeFromBackendList(BackendObject.State.MOODHISTORYLIST, position, (w, v) -> {});
        model.removeView((MoodEventViewingScreenActivity) context);
        ((MoodEventViewingScreenActivity) context).finish();
    }
}