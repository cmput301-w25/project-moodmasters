package com.example.moodmasters.Events.AlterMoodEventScreen;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenPhotoUploadFragment;

public class AlterMoodEventScreenShowPhotoDialogEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel backend, MVCController controller) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        AlterMoodEventScreenPhotoUploadFragment dialog = new AlterMoodEventScreenPhotoUploadFragment();
        dialog.show(fm, "PhotoUploadDialog");
    }
}