package com.example.moodmasters.Views.AlterMoodEventScreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.moodmasters.Events.AlterMoodEventScreen.AlterMoodEventScreenCameraUploadEvent;
import com.example.moodmasters.Events.AlterMoodEventScreen.AlterMoodEventScreenGalleryUploadEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.R;
/**
 * Class that is responsible for displaying a dialog fragment to give the user a choice between how they
 * want to select their photo
 * */
public class AlterMoodEventScreenPhotoUploadFragment extends DialogFragment implements MVCView {
    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        // skip for now
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.photo_upload_options, null);
        builder.setView(view);

        Button galleryButton = view.findViewById(R.id.photo_upload_gallery_button);
        Button cameraButton = view.findViewById(R.id.photo_upload_camera_button);

        galleryButton.setOnClickListener(v -> {
            controller.execute(new AlterMoodEventScreenGalleryUploadEvent(this), getContext());
        });

        cameraButton.setOnClickListener(v -> {
            controller.execute(new AlterMoodEventScreenCameraUploadEvent(this), getContext());
        });

        return builder.create();
    }
}