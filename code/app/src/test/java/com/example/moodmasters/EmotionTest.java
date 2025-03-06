package com.example.moodmasters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.moodmasters.Objects.ObjectsApp.Emotion;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class EmotionTest {

    @Test
    public void testGetString() {
        assertEquals("Happy", Emotion.getString(Emotion.State.HAPPY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringToEmotionState() {
        assertEquals(Emotion.State.HAPPY, Emotion.fromStringToEmotionState("Happy"));
        assertEquals(Emotion.State.HAPPY, Emotion.fromStringToEmotionState("HAPPY"));
        Emotion.fromStringToEmotionState("abc");
    }

    @Test
    public void testGetStringList() {
        String[] array = {
                "Happy",
                "Sad",
                "Angry",
                "Scared",
                "Disgusted",
                "Confused",
                "Ashamed",
                "Surprised"
        };
        List<String> emotion_list = Emotion.getStringList();
        assertTrue(emotion_list.containsAll(Arrays.asList(array)));
    }

}
