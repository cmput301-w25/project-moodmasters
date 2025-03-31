package com.example.moodmasters;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.moodmasters.Views.MoodEventViewingActivity;

@RunWith(AndroidJUnit4.class)
public class MoodCommentsViewingTest {

    @Rule
    public ActivityScenarioRule<MoodEventViewingActivity> activityRule =
            new ActivityScenarioRule<>(MoodEventViewingActivity.class);

    @Test
    public void testCommentsContainerIsDisplayed() {
        // This assumes your MoodEventViewingActivity layout contains a comments container
        // with id "R.id.view_comment_list" (as in your provided XML).
        onView(withId(R.id.view_comment_list))
                .check(matches(isDisplayed()));
    }
}
