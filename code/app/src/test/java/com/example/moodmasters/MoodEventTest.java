package com.example.moodmasters;

import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;

import org.junit.Before;

public class MoodEventTest {

    MoodEvent mood_event;

    @Before
    public void setUp() {
        long epoch_time = Long.parseLong("1741155652020");
        Mood mood = new Mood(Emotion.State.HAPPY, R.color.mood_happy_color, R.string.mood_emoji_happy);
        mood_event = new MoodEvent(
                "Mar 01 2025 | 12:00",
                epoch_time,
                mood,
                "reason",
                "trigger",
                SocialSituation.State.ALONE
        );
    }

}
