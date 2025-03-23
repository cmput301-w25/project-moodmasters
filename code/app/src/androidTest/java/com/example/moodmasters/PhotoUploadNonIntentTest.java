package com.example.moodmasters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.moodmasters.Views.AlterMoodEventActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PhotoUploadNonIntentTest {

    @Rule
    public ActivityScenarioRule<AlterMoodEventActivity> activityRule =
            new ActivityScenarioRule<>(AlterMoodEventActivity.class);

    // Test 1: Verify that the placeholder image is visible and has the correct content description.
    @Test
    public void testPlaceholderImageIsDisplayed() {
        onView(withId(R.id.alter_mood_upload_photo))
                .check(matches(isDisplayed()));
        onView(withId(R.id.alter_mood_upload_photo))
                .check(matches(withContentDescription("upload photo")));
    }

    // Test 2: Verify that clicking the photo upload image displays the photo options dialog.
    @Test
    public void testPhotoOptionsDialogAppears() {
        onView(withId(R.id.alter_mood_upload_photo)).perform(click());
        // Check that the gallery and camera buttons in the dialog are displayed.
        onView(withId(R.id.photo_upload_gallery_button)).check(matches(isDisplayed()));
        onView(withId(R.id.photo_upload_camera_button)).check(matches(isDisplayed()));
    }

    // Test 3: Verify that clicking the gallery button in the dialog dismisses the dialog.
    @Test
    public void testDialogDismissesAfterGalleryClick() {
        onView(withId(R.id.alter_mood_upload_photo)).perform(click());
        onView(withId(R.id.photo_upload_gallery_button)).perform(click());
        // After clicking, the gallery button should no longer be displayed.
        onView(withId(R.id.photo_upload_gallery_button)).check(doesNotExist());
    }

    // Test 4: Verify that clicking the camera button in the dialog dismisses the dialog.
    @Test
    public void testDialogDismissesAfterCameraClick() {
        onView(withId(R.id.alter_mood_upload_photo)).perform(click());
        onView(withId(R.id.photo_upload_camera_button)).perform(click());
        onView(withId(R.id.photo_upload_camera_button)).check(doesNotExist());
    }

    // Test 5: Verify that clicking the cancel button finishes the activity.
    @Test
    public void testCancelButtonFinishesActivity() {
        ActivityScenario<AlterMoodEventActivity> scenario = ActivityScenario.launch(AlterMoodEventActivity.class);
        onView(withId(R.id.alter_mood_cancel_button)).perform(click());
        scenario.onActivity(activity -> {
            // Assert that the activity is finishing.
            assert activity.isFinishing();
        });
    }
}
