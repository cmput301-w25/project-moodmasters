package com.example.moodmasters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.moodmasters.Views.LoginSignupScreen.LoginSignupScreenActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MoodPhotoTest {

    @Rule
    public ActivityScenarioRule<LoginSignupScreenActivity> scenarioRule =
            new ActivityScenarioRule<>(LoginSignupScreenActivity.class);

    @BeforeClass
    public static void setupFirestore() {
        FirebaseFirestore.getInstance().useEmulator("10.0.2.2", 8080);
    }

    @Before
    public void setup() {
        Intents.init();
    }

    @After
    public void teardown() {
        Intents.release();
        clearFirestore();
    }

    private void clearFirestore() {
        try {
            URL url = new URL("http://10.0.2.2:8080/emulator/v1/projects/moodmastersapp/databases/(default)/documents");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.getResponseCode();
            connection.disconnect();
        } catch (IOException ignored) {}
    }

    @Test
    public void testAddMoodWithGalleryPhoto() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_change_button)).perform(click());
        onView(withId(R.id.signup_login_enter_username)).perform(typeText("user_gallery"), closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(typeText("user_gallery"), closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.user_mood_history_add_button)).perform(click());
        Thread.sleep(500);

        onView(withId(R.id.alter_mood_photo_button)).perform(click());
        Thread.sleep(500);
        onView(withText("Upload from Gallery")).perform(click());

        Intent resultData = new Intent();
        Uri imageUri = Uri.parse("content://media/external/images/media/1");
        resultData.setData(imageUri);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        Intents.intending(IntentMatchers.hasAction(Intent.ACTION_PICK)).respondWith(result);

        Thread.sleep(500);
        onView(withId(R.id.alter_mood_photo_preview)).check(matches(isDisplayed()));

        onView(withId(R.id.alter_mood_emotion_spinner)).perform(click());
        onView(withText("Happy")).perform(click());
        onView(withId(R.id.alter_mood_ok_button)).perform(click());
        Thread.sleep(1000);

        onView(withText("Happy")).check(matches(isDisplayed()));
    }

    @Test
    public void testAddMoodWithCameraPhoto() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_change_button)).perform(click());
        onView(withId(R.id.signup_login_enter_username)).perform(typeText("user_camera"), closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(typeText("user_camera"), closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.user_mood_history_add_button)).perform(click());
        Thread.sleep(500);

        onView(withId(R.id.alter_mood_photo_button)).perform(click());
        Thread.sleep(500);
        onView(withText("Take Photo")).perform(click());

        Intent resultData = new Intent();
        Uri imageUri = Uri.parse("content://media/external/images/media/1");
        resultData.setData(imageUri);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        Intents.intending(IntentMatchers.hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);

        Thread.sleep(500);
        onView(withId(R.id.alter_mood_photo_preview)).check(matches(isDisplayed()));

        onView(withId(R.id.alter_mood_emotion_spinner)).perform(click());
        onView(withText("Sad")).perform(click());
        onView(withId(R.id.alter_mood_ok_button)).perform(click());
        Thread.sleep(1000);

        onView(withText("Sad")).check(matches(isDisplayed()));
    }
}
