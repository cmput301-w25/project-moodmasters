package com.example.moodmasters;

import static org.junit.Assert.assertEquals;

import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.Mood;

import org.junit.Before;
import org.junit.Test;

public class MoodTest {

    private Mood mood;

    @Before
    public void setUp() {
        mood = new Mood(Emotion.State.HAPPY, R.color.mood_happy_color, R.string.mood_emoji_happy);
    }

    @Test
    public void testGetters() {
        assertEquals(Emotion.State.HAPPY, mood.getEmotion());
        assertEquals("Happy", mood.getEmotionString());
        assertEquals(R.color.mood_happy_color, mood.getColor());
        assertEquals(R.string.mood_emoji_happy, mood.getEmoticon());
    }

    @Test
    public void testSetters() {
        mood.setEmotion(Emotion.State.SAD);
        mood.setColor(R.color.mood_sad_color);
        mood.setEmoticon(R.string.mood_emoji_sad);

        assertEquals(Emotion.State.SAD, mood.getEmotion());
        assertEquals("Sad", mood.getEmotionString());
        assertEquals(R.color.mood_sad_color, mood.getColor());
        assertEquals(R.string.mood_emoji_sad, mood.getEmoticon());
    }

}
