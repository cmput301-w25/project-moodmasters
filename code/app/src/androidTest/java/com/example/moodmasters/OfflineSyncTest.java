package com.example.moodmasters;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import com.example.moodmasters.Views.AlterMoodEventActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class OfflineSyncTest {

    @Rule
    public ActivityScenarioRule<AlterMoodEventActivity> activityRule =
            new ActivityScenarioRule<>(AlterMoodEventActivity.class);

    @Test
    public void testOfflineMoodEventSync() {
        // Disable Wi-Fi to simulate offline mode.
        try {
            InstrumentationRegistry.getInstrumentation()
                    .getUiAutomation()
                    .executeShellCommand("svc wifi disable");
            // Wait briefly to allow the device to disable Wi-Fi.
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Enter a mood event reason.
        onView(withId(R.id.alter_mood_enter_reason))
                .perform(replaceText("Offline Test"));

        // Submit the mood event.
        onView(withId(R.id.alter_mood_ok_button))
                .perform(click());

        // Optionally re-enable Wi-Fi after testing.
        try {
            InstrumentationRegistry.getInstrumentation()
                    .getUiAutomation()
                    .executeShellCommand("svc wifi enable");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Verify that the mood event "Offline Test" appears in the UI.
        onView(withText("Offline Test"))
                .check(matches(withText("Offline Test")));
    }
}
