package com.example.moodmasters.Views.AlterMoodEventScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.AlterMoodEventScreen.AlterMoodEventScreenAddEvent;
import com.example.moodmasters.Events.AlterMoodEventScreen.AlterMoodEventScreenCancelEvent;
import com.example.moodmasters.Events.AlterMoodEventScreen.AlterMoodEventScreenSetLocationEvent;
import com.example.moodmasters.Events.AlterMoodEventScreen.AlterMoodEventScreenUnsetLocationEvent;
import com.example.moodmasters.Events.AlterMoodEventScreen.AlterMoodEventScreenEditEvent;
import com.example.moodmasters.Events.MoodEventViewingScreen.MoodEventViewingScreenEditEvent;
import com.example.moodmasters.Events.AlterMoodEventScreen.AlterMoodEventScreenShowPhotoDialogEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.PhotoDecoderEncoder;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.R;
import com.google.android.gms.maps.model.LatLng;


import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.List;
/**
 * Activity that is responsible for giving a UI for editing the details of a mood event to either add
 * or replace pre-existing mood event
 * */
public class AlterMoodEventScreenActivity extends AppCompatActivity implements MVCView {
    private static final int PICK_IMAGE_REQUEST = 102;
    private static final int REQUEST_IMAGE_CAPTURE = 103;
    private final List<String> emotions_list;
    private final List<String> social_situations_list;
    private boolean photo_added;
    private LatLng userLocation = null;
    private static final int MAX_IMAGE_SIZE = 65536;

    public AlterMoodEventScreenActivity(){
        super();
        emotions_list = Emotion.getStringList();
        social_situations_list = SocialSituation.getStringList();
        photo_added = false;
    }
    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        // skip for now
    }
    public LatLng getLocation() {
        return userLocation;  // Return the location string
    }
    public void addMoodEventCode() {
        TextView label_view = findViewById(R.id.alter_mood_main_label);
        label_view.setText("Add MoodEvent");

        Spinner emotions_spinner = findViewById(R.id.alter_mood_emotion_spinner);
        ArrayAdapter<String> emotions_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, emotions_list);
        emotions_spinner.setAdapter(emotions_adapter);

        Spinner social_situations_spinner = findViewById(R.id.alter_mood_situation_spinner);
        ArrayAdapter<String> social_situations_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, social_situations_list);
        social_situations_spinner.setAdapter(social_situations_adapter);

        Button confirm_button = findViewById(R.id.alter_mood_ok_button);

        // Get the Get Location button
        Button location_button = findViewById(R.id.alter_mood_location_button);
        location_button.setOnClickListener(v -> {
            // Start the MoodLocationActivity to get the user's location
            controller.execute(new AlterMoodEventScreenSetLocationEvent(), this);
        });

        confirm_button.setOnClickListener(v -> {
            controller.execute(new AlterMoodEventScreenAddEvent(photo_added), this);
        });
    }

    public void editMoodEventCode(){
        System.out.println("AT EDIT MOOD CODE");
        TextView label_view = findViewById(R.id.alter_mood_main_label);
        label_view.setText("Edit MoodEvent");

        MoodEvent current_mood_event = MoodEventViewingScreenEditEvent.getMoodEvent();
        int position = MoodEventViewingScreenEditEvent.getPosition();

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
        check_public.setChecked(current_mood_event.getIsPublic());

        Button confirm_button = findViewById(R.id.alter_mood_ok_button);

        ImageView photo_button = findViewById(R.id.alter_mood_photo_button);
        if (current_mood_event.getPhotoString() != null){
            Bitmap photo = PhotoDecoderEncoder.photoDecoder(current_mood_event.getPhotoString());
            photo_button.setImageBitmap(photo);
        }
        TextView location_text = findViewById(R.id.alter_mood_location_text);
        Button location_button = findViewById(R.id.alter_mood_location_button);
        LatLng location = current_mood_event.getLocation();
        if (location == null){
            location_button.setOnClickListener(v -> {
                // Start the MoodLocationActivity to get the user's location
                controller.execute(new AlterMoodEventScreenSetLocationEvent(), this);
            });
        }
        else{
            String locationText = "Lat: " + String.format("%.2f", location.latitude) +
                    ", Long: " + String.format("%.2f", location.longitude);
            location_text.setText(locationText);
            location_button.setText("Remove Location");
            location_button.setOnClickListener(v -> {
                // Start the MoodLocationActivity to get the user's location
                controller.execute(new AlterMoodEventScreenUnsetLocationEvent(), this);
            });
        }


        confirm_button.setOnClickListener(v -> {
            controller.execute(new AlterMoodEventScreenEditEvent(current_mood_event, position, photo_added), this);
        });
    }

    public boolean addDataVerificationReason(String reason_string){
        return reason_string.length() <= 200;
    }

    public boolean addDataVerificationPhoto(String photo_string){
        return photo_string == null || photo_string.getBytes().length < 65536;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.alter_mood_screen);
        /* Will return type of event (add or edit) */

        Intent i = getIntent();
        String event = i.getStringExtra("Event");
        if (event == null) {
            event = "AddMoodEvent";
        }

        if (event.equals("AddMoodEvent")) {
            addMoodEventCode();
        }
        else if (event.equals("EditMoodEvent")){
            editMoodEventCode();
        }
        else{
            throw new IllegalArgumentException("Error: Didn't provide the proper intent extra on activity creation");
        }

        Button cancel_button = findViewById(R.id.alter_mood_cancel_button);
        cancel_button.setOnClickListener(v -> {
            controller.execute(new AlterMoodEventScreenCancelEvent(), this);
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView uploadPhotoImage = findViewById(R.id.alter_mood_photo_button);
        uploadPhotoImage.setOnClickListener(v -> {
            // Show dialog to let user choose between camera or gallery.
            controller.execute(new AlterMoodEventScreenShowPhotoDialogEvent(), this);
        });

    }

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
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("HERE I AM");
        super.onActivityResult(requestCode, resultCode, data);
        ImageView uploadPhotoImage = findViewById(R.id.alter_mood_photo_button);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            Bitmap bitmap;
            try{
                bitmap = getBitmapFromUri(imageUri);
            }
            catch (Exception e){
                return;
            }

            int originalSize = getBitmapSize(bitmap);
            Log.d("ImageSize", "Original image size: " + originalSize + " bytes");

            if (getBitmapSize(bitmap) > MAX_IMAGE_SIZE) {
                // Compress image
                bitmap = compressImage(bitmap);
            }
            Log.d("ImageSize", "New image size: " + getBitmapSize(bitmap) + " bytes");

            // Replace the photo icon with the selected image.
            uploadPhotoImage.setImageBitmap(bitmap);
            photo_added = true;
        }
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            uploadPhotoImage.setImageBitmap(photo);
            photo_added = true;
        }
        else if (requestCode == 1001 && resultCode == RESULT_OK) {
            // Retrieve the LatLng object from the result intent
            LatLng location = data.getParcelableExtra("location");

            if (location != null) {
                userLocation = location; // Store the location as LatLng object
                double fLatitude = Math.round(location.latitude * 100.0) / 100.0;
                double fLongitude = Math.round(location.longitude * 100.0) / 100.0;


                // Update the TextView with the location (you can display lat and long)
                TextView locationTextView = findViewById(R.id.alter_mood_location_text);
                locationTextView.setText("Lat: " + fLatitude + ", Long: " + fLongitude);
            }
        }
        else {
            // Handle failure (if location fetching failed)
            TextView locationTextView = findViewById(R.id.alter_mood_location_text);
            locationTextView.setText("Location fetch failed.");
            if (userLocation == null || (userLocation.latitude == 0.0 && userLocation.longitude == 0.0)) {
                locationTextView.setText("Unknown"); }
        }
    }
    private int getBitmapSize(Bitmap bitmap) {
        // Calculate size of the bitmap (in bytes)
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray.length;
    }
    // Compress the image to reduce its size if it's over the limit
    private Bitmap compressImage(Bitmap original) {
        Log.d("ImageSize", "Being compressed");

        int maxWidth = 800;  // Set the max width you want
        int maxHeight = 800; // Set the max height you want

        // Calculate aspect ratio and determine new width/height
        float aspectRatio = original.getWidth() / (float) original.getHeight();
        int newWidth = maxWidth;
        int newHeight = (int) (maxWidth / aspectRatio);

        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (int) (maxHeight * aspectRatio);
        }

        // Resize the image
        Bitmap resized = Bitmap.createScaledBitmap(original, newWidth, newHeight, true);

        // Start with a high compression quality (e.g., 80%)
        int quality = 80;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resized.compress(Bitmap.CompressFormat.JPEG, quality, outputStream); // Compress with initial quality
        byte[] byteArray = outputStream.toByteArray();

        // Check if the image size exceeds the MAX_IMAGE_SIZE
        while (byteArray.length > MAX_IMAGE_SIZE && quality > 10) {
            Log.d("ImageSize", "Image size too large: " + byteArray.length + " bytes. Reducing quality...");

            // Reduce the quality by 5 instead of 10 to make it more aggressive in reducing size
            outputStream.reset();  // Reset the stream
            quality -= 5;  // Decrease the quality in smaller steps for better control
            resized.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);

            byteArray = outputStream.toByteArray();

            if (quality <= 10) {
                // If quality drops below 10%, stop further reduction
                Log.d("ImageSize", "Quality dropped too low, stopping compression.");
                break;
            }
        }

        Log.d("ImageSize", "Final image size: " + byteArray.length + " bytes");

        // Return the compressed Bitmap
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

}

