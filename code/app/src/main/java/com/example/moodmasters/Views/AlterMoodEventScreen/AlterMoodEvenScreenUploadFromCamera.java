package com.example.moodmasters.Views.AlterMoodEventScreen;

import android.content.Context;

import androidx.fragment.app.DialogFragment;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;

public class AlterMoodEvenScreenUploadFromCamera implements MVCController.MVCEvent {
    private DialogFragment fragment;
    public AlterMoodEvenScreenUploadFromCamera(DialogFragment init_fragment){
        fragment = init_fragment;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        AlterMoodEventScreenActivity activity = (AlterMoodEventScreenActivity) context;
        fragment.dismiss();
        activity.openCamera();
    }
}
