package com.example.moodmasters.Events.AlterMoodEventScreen;

import android.content.Context;

import androidx.fragment.app.DialogFragment;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenActivity;

public class AlterMoodEventScreenGalleryUploadEvent implements MVCController.MVCEvent {
    private DialogFragment fragment;
    public AlterMoodEventScreenGalleryUploadEvent(DialogFragment init_fragment){
        fragment = init_fragment;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        AlterMoodEventScreenActivity activity = (AlterMoodEventScreenActivity) context;
        fragment.dismiss();
        activity.openImagePicker();
    }
}
