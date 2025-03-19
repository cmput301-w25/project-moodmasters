package com.example.moodmasters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.util.Log;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.moodmasters.Views.SignupLoginScreenActivity;
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
public class ProfileCreationTest {
    @Rule
    public ActivityScenarioRule<SignupLoginScreenActivity> scenario = new
            ActivityScenarioRule<SignupLoginScreenActivity>(SignupLoginScreenActivity.class);

    @BeforeClass
    public static void setup(){
        // Specific address for emulated device to access our localHost
        String androidLocalhost = "10.0.2.2";
        int portNumber = 8080;
        try{
            FirebaseFirestore.getInstance().useEmulator(androidLocalhost, portNumber);
        }
        catch (Exception ignore){

        }
    }

    @Test
    public void testProfileCreation() throws Exception{
        // New profile
        onView(withId(R.id.signup_login_enter_username)).perform(ViewActions.typeText("user_5"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.user_mood_history_label)).check(matches(withText("user_5")));

        // Try to create duplicate profile
        onView(withId(R.id.user_mood_history_menu_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.options_logout_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_enter_username)).perform(ViewActions.typeText("user_5"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(ViewActions.click());
        onView(withId(R.id.user_mood_history_label)).check(doesNotExist());

        // Login to created profile
        onView(withId(R.id.signup_login_change_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_ok_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.user_mood_history_label)).check(matches(withText("user_5")));

        // Try to login to non-existing profile
        onView(withId(R.id.user_mood_history_menu_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.options_logout_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_enter_username)).perform(ViewActions.typeText("user_6"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(ViewActions.click());
        onView(withId(R.id.user_mood_history_label)).check(doesNotExist());
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
