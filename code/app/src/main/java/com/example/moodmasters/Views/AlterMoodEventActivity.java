package com.example.moodmasters.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.AddMoodEventConfirmEvent;
import com.example.moodmasters.Events.AlterMoodEventCancelEvent;
import com.example.moodmasters.Events.UploadPhotoEvent;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.Events.EditMoodEventConfirmEvent;
import com.example.moodmasters.Events.MoodEventViewingEditEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

import java.util.List;
import java.util.Objects;

public class AlterMoodEventActivity extends AppCompatActivity implements MVCView {
    private static final int PICK_IMAGE_REQUEST = 102;
    private static final int REQUEST_IMAGE_CAPTURE = 103;
    private final List<String> emotions_list;
    private final List<String> social_situations_list;

    // Declare the controller field.
    private MVCController controller;

    public AlterMoodEventActivity() {
        super();
        emotions_list = Emotion.getStringList();
        social_situations_list = SocialSituation.getStringList();
    }

    @Override
    public void update(MVCModel model) {
        // skip for now
    }

    @Override
    public void initialize(MVCModel model) {
        // skip for now
    }

    public void addMoodEventCode() {
        TextView label_view = findViewById(R.id.alter_mood_main_label);
        label_view.setText("Add MoodEvent");

        Spinner emotions_spinner = findViewById(R.id.alter_mood_emotion_spinner);
        ArrayAdapter<String> emotions_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, emotions_list);
        emotions_spinner.setAdapter(emotions_adapter);

        Spinner social_situations_spinner = findViewById(R.id.alter_mood_situation_spinner);
        ArrayAdapter<String> social_situations_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, social_situations_list);
        social_situations_spinner.setAdapter(social_situations_adapter);

        EditText reason_text = findViewById(R.id.alter_mood_enter_reason);

        Button confirm_button = findViewById(R.id.alter_mood_ok_button);

        confirm_button.setOnClickListener(v -> {
            controller.execute(new AddMoodEventConfirmEvent(), this);
        });
    }

    public void editMoodEventCode(){
        TextView label_view = findViewById(R.id.alter_mood_main_label);
        label_view.setText("Edit MoodEvent");

        MoodEvent current_mood_event = MoodEventViewingEditEvent.getMoodEvent();
        int position = MoodEventViewingEditEvent.getPosition();

        Spinner emotions_spinner = findViewById(R.id.alter_mood_emotion_spinner);
        ArrayAdapter<String> emotions_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, emotions_list);
        emotions_spinner.setAdapter(emotions_adapter);
        int emotion_default_pos = emotions_adapter.getPosition(current_mood_event.getMood().getEmotionString());
        emotions_spinner.setSelection(emotion_default_pos);

        Spinner social_situations_spinner = findViewById(R.id.alter_mood_situation_spinner);
        ArrayAdapter<String> social_situations_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, social_situations_list);
        social_situations_spinner.setAdapter(social_situations_adapter);
        int social_default_pos = social_situations_adapter.getPosition(SocialSituation.getString(current_mood_event.getSituation()));
        social_situations_spinner.setSelection(social_default_pos);

        EditText reason_text = findViewById(R.id.alter_mood_enter_reason);
        reason_text.setText(current_mood_event.getReason());

        CheckBox check_public = findViewById(R.id.alter_mood_public_checkbox);
        check_public.setChecked(current_mood_event.isIs_public());

        Button confirm_button = findViewById(R.id.alter_mood_ok_button);

        confirm_button.setOnClickListener(v -> {
            controller.execute(new EditMoodEventConfirmEvent(current_mood_event, position), this);
        });
    }

    public boolean addDataVerification(String reason_string){
        return reason_string.length() > 200;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.alter_mood_screen);

        // Initialize the controller with a new model.
        MVCModel model = new MVCModel();
        controller = new MVCController(model);

        // Create the backend object for MOODHISTORYLIST as a list.
        controller.createBackendObject(BackendObject.State.MOODHISTORYLIST);

        // Determine mode (add or edit) from intent extras.
        if (Objects.equals(getIntent().getStringExtra("Event"), "AddMoodEvent")) {
            addMoodEventCode();
        } else {
            editMoodEventCode();
        }

        Button cancel_button = findViewById(R.id.alter_mood_cancel_button);
        cancel_button.setOnClickListener(v -> {
            controller.execute(new AlterMoodEventCancelEvent(), this);
        });

        // Set listener for the upload photo image.
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView uploadPhotoImage = findViewById(R.id.alter_mood_upload_photo);
        uploadPhotoImage.setOnClickListener(v -> {
            // Show dialog to let user choose between camera or gallery.
            controller.execute(new UploadPhotoEvent(), this);
        });
    }

    // Method to open the gallery.
    public void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Method to open the camera.
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView uploadPhotoImage = findViewById(R.id.alter_mood_upload_photo);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            // Replace the photo icon with the selected image.
            uploadPhotoImage.setImageURI(imageUri);
            // Optionally, upload the photo:
            new com.example.moodmasters.Events.SavePhotoEvent().savePhoto(imageUri);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            // For camera capture, get the thumbnail Bitmap.
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            uploadPhotoImage.setImageBitmap(photo);
            // Optionally, convert the Bitmap to a file/URI and upload it.
        }
    }
}
