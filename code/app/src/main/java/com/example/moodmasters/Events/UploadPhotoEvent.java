package com.example.moodmasters.Events;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Views.PhotoUploadFragment;

public class UploadPhotoEvent implements MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel backend, MVCController controller) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        PhotoUploadFragment dialog = new PhotoUploadFragment();
        dialog.show(fm, "PhotoUploadDialog");
    }
}
