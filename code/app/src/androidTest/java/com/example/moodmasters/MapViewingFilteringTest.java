package com.example.moodmasters;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.example.moodmasters.Views.MoodHistoryListActivity;

@RunWith(AndroidJUnit4.class)
public class MapViewingFilteringTest {

    @Rule
    public ActivityScenarioRule<MoodHistoryListActivity> activityRule =
            new ActivityScenarioRule<>(MoodHistoryListActivity.class);

    @Test
    public void testMapFragmentDisplayedAfterMapButtonClick() {
        // Assume that the button to show map has id R.id.user_mood_history_show_map_button.
        onView(withId(R.id.user_mood_history_show_map_button))
                .perform(click());
        // And that the map fragment has id R.id.map.
        onView(withId(R.id.mood_filter_location_recency_checkbox))
                .check(matches(isDisplayed()));
    }
}
