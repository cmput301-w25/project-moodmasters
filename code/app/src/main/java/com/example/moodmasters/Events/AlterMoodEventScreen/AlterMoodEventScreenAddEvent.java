package com.example.moodmasters.Events.AlterMoodEventScreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Calendar;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moodmasters.Objects.ObjectsApp.Comment;
import com.example.moodmasters.Objects.ObjectsApp.PhotoDecoderEncoder;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Views.AlterMoodEventScreen.AlterMoodEventScreenActivity;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;
import com.example.moodmasters.R;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AlterMoodEventScreenAddEvent implements MVCController.MVCEvent {
    private boolean photo_added;
    public AlterMoodEventScreenAddEvent(boolean init_photo_added){
        photo_added = init_photo_added;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        AlterMoodEventScreenActivity activity = (AlterMoodEventScreenActivity) context;

        Spinner emotions_spinner = activity.findViewById(R.id.alter_mood_emotion_spinner);
        String emotion_string = emotions_spinner.getSelectedItem().toString().trim();
        Emotion.State emotion = Emotion.fromStringToEmotionState(emotion_string);
        MoodList mood_list = (MoodList) model.getBackendObject(BackendObject.State.MOODLIST);

        Spinner social_situations_spinner = activity.findViewById(R.id.alter_mood_situation_spinner);
        String social_situation_string = social_situations_spinner.getSelectedItem().toString();
        SocialSituation.State social_situation = SocialSituation.fromStringToSocialState(social_situation_string);

        EditText reason_text = activity.findViewById(R.id.alter_mood_enter_reason);
        String reason_string = reason_text.getText().toString().trim();

        CheckBox check_public = activity.findViewById(R.id.alter_mood_public_checkbox);
        boolean is_public = check_public.isChecked();

        long epoch_time = System.currentTimeMillis();           /* Epoch time will be the time stored on the database for easy conversion to different time zones */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(epoch_time);
        DateFormat format = new SimpleDateFormat("MMM dd yyyy | HH:mm");
        String datetime = format.format(calendar.getTime());

        ImageView upload_photo_image = activity.findViewById(R.id.alter_mood_photo_button);
        Bitmap photo =((BitmapDrawable) upload_photo_image.getDrawable()).getBitmap();
        String photo_string;
        if (photo_added){
            photo_string = PhotoDecoderEncoder.photoEncoder(photo);
        }
        else{
            photo_string = null;
        }

        if (!activity.addDataVerificationReason(reason_string)){
            reason_text.setError("Must be less than 20 characters and only 3 words");
            return;
        }
        if (!activity.addDataVerificationPhoto(photo_string)){
            Toast.makeText(context, "Photo must be less than 65536 bytes", Toast.LENGTH_SHORT).show();
            return;
        }
        
        LatLng location = activity.getLocation(); // Get the location as LatLng

        Participant user = ((Participant) model.getBackendObject(BackendObject.State.USER));
        MoodEvent new_mood_event = new MoodEvent(datetime, epoch_time, mood_list.getMood(emotion),
                is_public, reason_string, social_situation, location, user.getUsername(), photo_string,
                new ArrayList<Comment>());
        model.addToBackendList(BackendObject.State.MOODHISTORYLIST, new_mood_event, (v,w) -> {});
        ((AlterMoodEventScreenActivity) context).finish();

    }
}