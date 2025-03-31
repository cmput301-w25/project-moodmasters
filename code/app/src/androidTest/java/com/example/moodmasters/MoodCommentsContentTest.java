package com.example.moodmasters;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.moodmasters.Views.MoodEventViewingActivity;

@RunWith(AndroidJUnit4.class)
public class MoodCommentsContentTest {

    @Rule
    public ActivityScenarioRule<MoodEventViewingActivity> activityRule =
            new ActivityScenarioRule<>(MoodEventViewingActivity.class);

    @Test
    public void testAddingAndViewingComment() {
        String testComment = "Great mood!";
        // Assume your comment input field has id R.id.comment_input and submit button has id R.id.comment_submit_button.
        onView(withId(R.id.comment_input))
                .perform(replaceText(testComment));
        onView(withId(R.id.comment_submit_button))
                .perform(click());
        // Verify that the comment text is now displayed somewhere in the view.
        onView(withText(testComment))
                .check(matches(withText(testComment)));
    }
}
