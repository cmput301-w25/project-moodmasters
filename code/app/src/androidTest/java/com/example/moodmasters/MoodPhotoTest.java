package com.example.moodmasters;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import androidx.test.espresso.action.ViewActions;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.moodmasters.R;
import com.example.moodmasters.Views.AlterMoodEventActivity;

@RunWith(AndroidJUnit4.class)
public class MoodPhotoTest {

    @Rule
    public ActivityScenarioRule<AlterMoodEventActivity> activityRule =
            new ActivityScenarioRule<>(AlterMoodEventActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testPhotoUploadDialogAppears() {
        // Simulate a click on the photo upload image view.
        onView(withId(R.id.alter_mood_photo_button))
                .perform(ViewActions.click());
        // Verify that the gallery and camera buttons in the resulting dialog are displayed.
        onView(withId(R.id.photo_upload_gallery_button))
                .check(matches(isDisplayed()));
        onView(withId(R.id.photo_upload_camera_button))
                .check(matches(isDisplayed()));
    }
}
