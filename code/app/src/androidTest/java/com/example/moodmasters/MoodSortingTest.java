package com.example.moodmasters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Views.SignupLoginScreenActivity;
import com.google.firebase.firestore.FirebaseFirestore;


public class MoodSortingTest {
    // Tests that Mood list Sorting is sorted by date and time, in reverse chronological order (most recent coming first)

    @Rule
    public ActivityScenarioRule<SignupLoginScreenActivity> scenario = new
            ActivityScenarioRule<SignupLoginScreenActivity>(SignupLoginScreenActivity.class);

    @BeforeClass
    public static void setup(){
        // Specific address for emulated device to access our localHost
        String androidLocalhost = "10.0.2.2";
        int portNumber = 8080;
        FirebaseFirestore.getInstance().useEmulator(androidLocalhost, portNumber);
    }


    @Before
        public void navigateToMainScreen() {
            onView(withId(R.id.signup_login_enter_username)).perform(ViewActions.typeText("user_3"));
            onView(withId(R.id.signup_login_ok_button)).perform(ViewActions.click());
    }

    @Test
    public void testMoodsareSorted() {
        long sadTime = System.currentTimeMillis();
        onView(withId(R.id.user_mood_history_add_button)).perform(ViewActions.click());
        onView(withId(R.id.alter_mood_emotion_spinner)).perform(ViewActions.click());
        onView(withText("Sad")).perform(ViewActions.click());
        onView(withId(R.id.alter_mood_ok_button)).perform(ViewActions.click());
        onView(withText("Sad")).check(matches(isDisplayed()));


        // Wait 2 seconds before adding happy
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long happyTimestamp = System.currentTimeMillis(); // This should be different since
        onView(withId(R.id.user_mood_history_add_button)).perform(ViewActions.click());
        onView(withId(R.id.alter_mood_emotion_spinner)).perform(ViewActions.click());
        onView(withText("Happy")).perform(ViewActions.click());
        onView(withId(R.id.alter_mood_ok_button)).perform(ViewActions.click());
        onView(withText("Happy")).check(matches(isDisplayed()));
        onView(withText("Sad")).check(matches(isDisplayed()));
        // Check that there are 2 items in listView
        onView(withId(R.id.mood_history_list)).check(matches(hasChildCount(2)));
        // Check that the first item in the list is sad
        onView(withText("Sad")).check(matches(isDisplayed()));
        onView(withText("Happy")).check(matches(isDisplayed()));
        }


    }




