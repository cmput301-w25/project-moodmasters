package com.example.moodmasters.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.moodmasters.R;

import java.util.Objects;

public class PhotoUploadFragment extends DialogFragment {
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
            dismiss();
            if (getActivity() instanceof AlterMoodEventActivity) {
                ((AlterMoodEventActivity) getActivity()).openImagePicker();
            }
        });

        cameraButton.setOnClickListener(v -> {
            dismiss();
            if (getActivity() instanceof AlterMoodEventActivity) {
                ((AlterMoodEventActivity) getActivity()).openCamera();
            }
        });

        return builder.create();
    }
}
