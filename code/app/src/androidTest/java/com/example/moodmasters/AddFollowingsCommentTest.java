package com.example.moodmasters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.util.Log;
import android.view.View;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.moodmasters.Views.SignupLoginScreenActivity;
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
public class AddFollowingsCommentTest {
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

    @Before
    public void navigateToMainScreen() {
        onView(withId(R.id.signup_login_change_button)).perform(ViewActions.click());
        //Thread.sleep(1000);
        onView(withId(R.id.signup_login_enter_username)).perform(ViewActions.typeText("user_10"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(ViewActions.typeText("user_10"));
        onView(withId(R.id.signup_login_enter_password)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(ViewActions.click());
    }

    @Test
    public void testEditMood() throws Exception{
        Thread.sleep(1000);
        // 1 mood
        onView(withId(R.id.user_mood_history_add_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.alter_mood_emotion_spinner)).perform(ViewActions.click());
        onView(withText("Sad")).perform(ViewActions.click());
        onView(withId(R.id.alter_mood_public_checkbox)).perform(ViewActions.click()); // Make mood public so followers can see it
        onView(withId(R.id.alter_mood_ok_button)).perform(ViewActions.click());
        Thread.sleep(1000);

        onView(withText("Sad")).check(matches(isDisplayed()));

        // Logout and login into another user to request user_7
        onView(withId(R.id.options_logout_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_change_button)).perform(ViewActions.click()); // Change to sign up user_8
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_enter_username)).perform(ViewActions.typeText("user_11"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(ViewActions.typeText("user_11"));
        onView(withId(R.id.signup_login_enter_password)).perform(closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_ok_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        // Request to follow user_7
        onView(withId(R.id.mood_following_list_button)).perform(ViewActions.click());
        onView(withId(R.id.user_search_button)).perform(ViewActions.click());
        onView(withId(R.id.searchInput)).perform(ViewActions.typeText("user_10"));
        onView(withId(R.id.searchInput)).perform(closeSoftKeyboard());
        onView(withId(R.id.searchButton)).perform(ViewActions.click());
        onView(withText("user_10")).check(matches(isDisplayed()));
        onView(withText("user_10")).perform(ViewActions.click());
        // Logout of user_8
        onView(withId(R.id.followButton)).perform(ViewActions.click());
        onView(withId(R.id.backButton)).perform(ViewActions.click());
        onView(withId(R.id.backButton)).perform(ViewActions.click());
        onView(withId(R.id.options_logout_button)).perform(ViewActions.click());
        // Log back into user_10
        onView(withId(R.id.signup_login_enter_username)).perform(ViewActions.typeText("user_10"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(ViewActions.typeText("user_10"));
        onView(withId(R.id.signup_login_enter_password)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(ViewActions.click());
        // Accept follow request from user_8
        onView(withId(R.id.options_follow_requests_button)).perform(ViewActions.click());
        onView(withText("user_8")).check(matches(isDisplayed()));
        onView(withId(R.id.accept_button)).perform(ViewActions.click());
        // Log back into user_8 to view user_7's moods
        onView(withId(R.id.options_logout_button)).perform(ViewActions.click());
        onView(withId(R.id.signup_login_enter_username)).perform(ViewActions.typeText("user_11"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(ViewActions.typeText("user_11"));
        onView(withId(R.id.signup_login_enter_password)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_ok_button)).perform(ViewActions.click());
        onView(withId(R.id.mood_following_list_button)).perform(ViewActions.click());
        onView(withText("Sad")).check(matches(isDisplayed()));
        onView(withText("Sad")).perform(ViewActions.longClick());
        Thread.sleep(1000);
        onView(withId(R.id.comment_edit_text)).perform(typeText("TestComment"));
        onView(withId(R.id.comment_edit_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.ok_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.view_comment_list)).check(matches(hasDescendant(withText("TestComment"))));

        // Logout of user_8
        onView(withId(R.id.view_mood_x_button)).perform(ViewActions.click());
        onView(withId(R.id.view_mood_x_button)).perform(ViewActions.click());
        onView(withId(R.id.options_logout_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        // Log back into user_7 to view commnent on mood
        onView(withId(R.id.signup_login_enter_username)).perform(ViewActions.typeText("user_10"));
        onView(withId(R.id.signup_login_enter_username)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_login_enter_password)).perform(ViewActions.typeText("user_10"));
        onView(withId(R.id.signup_login_enter_password)).perform(closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.signup_login_ok_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withText("Sad")).perform(ViewActions.longClick());
        Thread.sleep(1000);
        onView(withId(R.id.view_mood_comments_button)).perform(ViewActions.click());
        Thread.sleep(1000);
        // Check if the listview still contains the comment
        onView(withId(R.id.view_comment_list)).check(matches(hasDescendant(withText("TestComment"))));

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
