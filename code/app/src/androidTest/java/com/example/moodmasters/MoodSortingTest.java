package com.example.moodmasters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.util.Log;

import androidx.test.espresso.action.ViewActions;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MoodSortingTest {
    // Tests that Mood list Sorting is sorted by date and time, in reverse chronological order (most recent coming first)

    @Rule
    public ActivityScenarioRule<LoginSignupScreenActivity> scenario = new
            ActivityScenarioRule<LoginSignupScreenActivity>(LoginSignupScreenActivity.class);

    @BeforeClass
    public static void setup() {
        // Specific address for emulated device to access our localHost
        String androidLocalhost = "10.0.2.2";
        int portNumber = 8080;
        try{
            FirebaseFirestore.getInstance().useEmulator(androidLocalhost, portNumber);
        }
        catch (Exception ignore){

        }
    }


    @Before
    public void navigateToMainScreen() {
        onView(withId(R.id.signup_login_change_button)).perform(ViewActions.click());
        //Thread.sleep(1000);
        onView(withId(R.id.signup_login_enter_username)).perform(ViewActions.typeText("user_7"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(ViewActions.typeText("user_7"));
        onView(withId(R.id.signup_login_enter_password)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(ViewActions.click());
    }

    @Test
    public void testMoodsAreSorted() throws Exception{
        Thread.sleep(1000);
        long sadTime = System.currentTimeMillis();
        onView(withId(R.id.user_mood_history_add_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.alter_mood_emotion_spinner)).perform(ViewActions.click());
        onView(withText("Sad")).perform(ViewActions.click());
        onView(withId(R.id.alter_mood_ok_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withText("Sad")).check(matches(isDisplayed()));


        // Wait 2 seconds before adding happy
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long happyTimestamp = System.currentTimeMillis(); // This should be different since
        onView(withId(R.id.user_mood_history_add_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.alter_mood_emotion_spinner)).perform(ViewActions.click());
        onView(withText("Happy")).perform(ViewActions.click());
        onView(withId(R.id.alter_mood_ok_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withText("Happy")).check(isCompletelyBelow(withText("Sad")));
        onView(withText("Sad")).check(isCompletelyAbove(withText("Happy")));

        onView(withId(R.id.user_mood_following_sort_button)).perform(ViewActions.click());

        onView(withText("Sad")).check(isCompletelyBelow(withText("Happy")));
        onView(withText("Happy")).check(isCompletelyAbove(withText("Sad")));
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