package com.example.moodmasters.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.AddMoodEventConfirmEvent;
import com.example.moodmasters.Events.AlterMoodEventCancelEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.R;

import java.util.List;


public class AlterMoodEventActivity extends AppCompatActivity implements MVCView {
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 102;
    private final List<String> emotions_list;
    private final List<String> social_situations_list;

    private MoodEvent moodEvent;

    public AlterMoodEventActivity(){
        super();
        emotions_list = Emotion.getStringList();
        social_situations_list = SocialSituation.getStringList();
    }
    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        // skip for now
    }

    public void addMoodEventCode() {
        Spinner emotions_spinner = findViewById(R.id.alter_mood_emotion_spinner);
        ArrayAdapter<String> emotions_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, emotions_list);
        emotions_spinner.setAdapter(emotions_adapter);

        Spinner social_situations_spinner = findViewById(R.id.alter_social_situation_spinner);
        ArrayAdapter<String> social_situations_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, social_situations_list);
        social_situations_spinner.setAdapter(social_situations_adapter);


        EditText trigger_text = findViewById(R.id.alter_mood_enter_trigger);

        EditText reason_text = findViewById(R.id.alter_mood_enter_reason);

        Button confirm_button = findViewById(R.id.confirm_button);

        confirm_button.setOnClickListener(v -> {
            controller.execute(new AddMoodEventConfirmEvent(), this);
        });
    }

    public void editMoodEventCode(){
        // TODO: Add code for editing mood event
    }

    public boolean addDataVerification(String reason_string){
        if (reason_string.length() > 20){
            return false;
        }
        String regex = "\\W+";
        String[] words = reason_string.split(regex);
        if (words.length > 3){
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.alter_mood_screen);
        // TODO: Add view to model via controller if it is found necessary
        /* Will return type of event (add or edit) */

        ImageView selectPhotoButton = findViewById(R.id.select_photo_button);
        selectPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoPickerDialog();
            }
        });


        Intent i = getIntent();
        String event = i.getStringExtra("Event");
        assert event != null;
        if (event.equals("AddMoodEvent")) {
            addMoodEventCode();
        }
        else if (event.equals("EditMoodEvent")){
            editMoodEventCode();
        }
        else{
            throw new IllegalArgumentException("Error: Didn't provide the proper intent extra on activity creation");
        }

        Button cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(v -> {
            controller.execute(new AlterMoodEventCancelEvent(), this);
        });

    }

    private void openPhotoPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Photo")
                .setItems(new CharSequence[] {"Take Photo", "Choose from Gallery"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    openCamera();  // Open camera to take a photo
                                } else if (which == 1) {
                                    openGallery();  // Open gallery to choose a photo
                                }
                            }
                        });
        builder.show();
    }

    private void openGallery() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);

    }


    private void openCamera() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (requestCode == CAMERA_REQUEST_CODE) {
                setImage(selectedImageUri);
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                setImage(selectedImageUri);
            }
        }
    }

    private void setImage(Uri imageUri) {
        ImageView photoImageView = findViewById(R.id.select_photo_button);
        photoImageView.setImageURI(imageUri);

        // Set the URI to the mood event

        moodEvent.setPhotoUri(imageUri);  // Assuming 'moodEvent' is the current event object
    }

    private void displayMoodEvent(MoodEvent moodEvent) {
        Uri photoUri = moodEvent.getPhotoUri();
        if (photoUri != null) {
            ImageView photoImageView = findViewById(R.id.mood_event_photo);
            photoImageView.setImageURI(photoUri);
        }
    }




}