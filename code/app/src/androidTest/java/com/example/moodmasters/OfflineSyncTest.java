package com.example.moodmasters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;
import android.util.Log;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.moodmasters.Views.LoginSignupScreen.LoginSignupScreenActivity;
import com.example.moodmasters.R;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OfflineSyncTest {

    @Rule
    public ActivityScenarioRule<LoginSignupScreenActivity> scenario =
            new ActivityScenarioRule<>(LoginSignupScreenActivity.class);

    @BeforeClass
    public static void setup() {
        // Use the Firestore emulator on 10.0.2.2:8080 for testing.
        try {
            FirebaseFirestore.getInstance().useEmulator("10.0.2.2", 8080);
        } catch (Exception ignore) {}
    }

    @Test
    public void testOfflineMoodEventSync() throws Exception {
        // Log in as a user
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_change_button)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_enter_username)).perform(typeText("user_offline"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(typeText("user_offline"));
        onView(withId(R.id.signup_login_enter_password)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(click());
        Thread.sleep(2000);

        // Disable WiFi (simulate offline)
        InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("svc wifi disable");
        SystemClock.sleep(2000);

        // Add a mood event while offline
        onView(withId(R.id.user_mood_history_add_button)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.alter_mood_emotion_spinner)).perform(click());
        onView(withText("Sad")).perform(click());
        onView(withId(R.id.alter_mood_ok_button)).perform(click());
        Thread.sleep(2000);

        // Re-enable WiFi
        InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("svc wifi enable");
        SystemClock.sleep(5000);

        // Verify that the added mood event appears
        onView(withText("Sad")).check(matches(withText("Sad")));
    }

    @After
    public void tearDown() {
        // Clean up Firestore emulator data
        String projectId = "moodmastersapp";
        URL url = null;
        try {
            url = new URL("http://10.0.2.2:8080/emulator/v1/projects/" + projectId + "/databases/(default)/documents");
        } catch (Exception e) {
            Log.e("URL Error", Objects.requireNonNull(e.getMessage()));
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) Objects.requireNonNull(url).openConnection();
            urlConnection.setRequestMethod("DELETE");
            int response = urlConnection.getResponseCode();
            Log.i("Response Code", "Response Code: " + response);
        } catch (Exception e) {
            Log.e("IO Error", Objects.requireNonNull(e.getMessage()));
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
