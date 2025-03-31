package com.example.moodmasters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;


import android.util.Log;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import com.example.moodmasters.Views.SignupLoginScreenActivity;
import com.example.moodmasters.R;
import com.google.firebase.firestore.FirebaseFirestore;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddMoodTest {

    @Rule
    public ActivityScenarioRule<SignupLoginScreenActivity> scenario =
            new ActivityScenarioRule<>(SignupLoginScreenActivity.class);

    @BeforeClass
    public static void setup() {
        // Use the Firestore emulator on 10.0.2.2:8080 for testing.
        try {
            FirebaseFirestore.getInstance().useEmulator("10.0.2.2", 8080);
        } catch (Exception ignore) {
        }
    }

    @Test
    public void testAddMood() throws Exception {
        // Navigate to main screen via login/signup
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_change_button)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_enter_username)).perform(typeText("user_1"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(typeText("user_1"));
        onView(withId(R.id.signup_login_enter_password)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(click());
        Thread.sleep(1000);

        // Add first mood
        onView(withId(R.id.user_mood_history_add_button)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.alter_mood_emotion_spinner)).perform(click());
        onView(withText("Sad")).perform(click());
        onView(withId(R.id.alter_mood_ok_button)).perform(click());
        Thread.sleep(1000);
        onView(withText("Sad")).check(matches(isDisplayed()));

        // Add a second mood
        onView(withId(R.id.user_mood_history_add_button)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.alter_mood_emotion_spinner)).perform(click());
        onView(withText("Happy")).perform(click());
        onView(withId(R.id.alter_mood_ok_button)).perform(click());
        Thread.sleep(1000);
        onView(withText("Happy")).check(matches(isDisplayed()));
        onView(withText("Sad")).check(matches(isDisplayed()));

        // Logout and login again
        onView(withId(R.id.options_logout_button)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_enter_username)).perform(typeText("user_1"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(typeText("user_1"));
        onView(withId(R.id.signup_login_enter_password)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(click());
        Thread.sleep(1000);
        onView(withText("Happy")).check(matches(isDisplayed()));
        onView(withText("Sad")).check(matches(isDisplayed()));

        // Switch to a new user and verify no moods are shown.
        onView(withId(R.id.options_logout_button)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_change_button)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_enter_username)).perform(typeText("user_2"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(typeText("user_2"));
        onView(withId(R.id.signup_login_enter_password)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(click());
        Thread.sleep(1000);
        onView(withText("Happy")).check(doesNotExist());
        onView(withText("Sad")).check(doesNotExist());

        // Logout and login back as user_1 again.
        onView(withId(R.id.options_logout_button)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_enter_username)).perform(typeText("user_1"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(typeText("user_1"));
        onView(withId(R.id.signup_login_enter_password)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(click());
        Thread.sleep(1000);
        onView(withText("Happy")).check(matches(isDisplayed()));
        onView(withText("Sad")).check(matches(isDisplayed()));

        // Test a mood with an invalid (4-word) reason (which should fail)
        onView(withId(R.id.user_mood_history_add_button)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.alter_mood_emotion_spinner)).perform(click());
        onView(withText("Scared")).perform(click());
        onView(withId(R.id.alter_mood_enter_reason)).perform(typeText("a b c d"));
        onView(withId(R.id.alter_mood_enter_reason)).perform(closeSoftKeyboard());
        onView(withId(R.id.alter_mood_ok_button)).perform(click());
        Thread.sleep(1000);
        onView(withText(R.string.mood_emoji_scared)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() {
        String projectId = "moodmastersapp";
        URL url = null;
        try {
            url = new URL("http://10.0.2.2:8080/emulator/v1/projects/" + projectId + "/databases/(default)/documents");
        } catch (MalformedURLException exception) {
            Log.e("URL Error", Objects.requireNonNull(exception.getMessage()));
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            int response = urlConnection.getResponseCode();
            Log.i("Response Code", "Response Code: " + response);
        } catch (IOException exception) {
            Log.e("IO Error", Objects.requireNonNull(exception.getMessage()));
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
