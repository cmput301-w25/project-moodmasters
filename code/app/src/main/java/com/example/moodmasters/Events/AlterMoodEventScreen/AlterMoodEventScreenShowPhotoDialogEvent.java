package com.example.moodmasters.Events.AlterMoodEventScreen;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenPhotoUploadFragment;

/**
 * Class that is responsible for handling the UI interaction of clicking on the photo button inside
 * the AlterMoodEventScreen, will bring up a dialog fragment that will allow you to select a picture
 * you want to attach to your mood event
 * */
public class AlterMoodEventScreenShowPhotoDialogEvent implements MVCController.MVCEvent {
    /**
     * Executes code that is necessary for the UI interaction of clicking the photo button inside the
     * AlterMoodEventScreen, this code just brings up a dialog fragment so the user can choose a photo
     * upload method
     * @param context
     *  The app context that can be used to bring up new UI elements like fragments and activities
     * @param model
     *  The model that the controller can interact with for possible data manipulation
     * @param controller
     *  The controller responsible for executing the MVCEvent in the first place
     * */
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        AlterMoodEventScreenPhotoUploadFragment dialog = new AlterMoodEventScreenPhotoUploadFragment();
        dialog.show(fm, "PhotoUploadDialog");
    }
}