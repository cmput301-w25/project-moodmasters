package com.example.moodmasters;

import static org.junit.Assert.assertEquals;

import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MoodListTest {

    private MoodList mood_list;
    private Mood happy;

    @Before
    public void setUp() {
        happy = new Mood(Emotion.State.HAPPY, R.color.mood_happy_color, R.string.mood_emoji_happy);
        Mood sad = new Mood(Emotion.State.SAD, R.color.mood_sad_color, R.string.mood_emoji_sad);
        Mood angry = new Mood(Emotion.State.ANGRY, R.color.mood_angry_color, R.string.mood_emoji_angry);
        Mood scared = new Mood(Emotion.State.SCARED, R.color.mood_scared_color, R.string.mood_emoji_scared);
        Mood disgusted = new Mood(Emotion.State.DISGUSTED, R.color.mood_disgusted_color, R.string.mood_emoji_disgusted);
        Mood confused = new Mood(Emotion.State.CONFUSED, R.color.mood_confused_color, R.string.mood_emoji_confused);
        Mood ashamed = new Mood(Emotion.State.ASHAMED, R.color.mood_ashamed_color, R.string.mood_emoji_ashamed);
        Mood surprised = new Mood(Emotion.State.SURPRISED, R.color.mood_surprised_color, R.string.mood_emoji_surprised);
        List<Mood> init_list = Arrays.asList(happy, sad, angry, scared, disgusted, confused, ashamed, surprised);
        mood_list = new MoodList(init_list);
    }

    @Test
    public void testGetMood() {
        assertEquals(happy, mood_list.getMood(Emotion.State.HAPPY));
    }
}
