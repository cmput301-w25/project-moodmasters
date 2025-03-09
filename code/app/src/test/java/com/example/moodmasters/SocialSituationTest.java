package com.example.moodmasters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SocialSituationTest {
    @Test(expected = IllegalArgumentException.class)
    public void testFromStringToSocialState() {
        assertEquals(SocialSituation.State.ALONE, SocialSituation.fromStringToSocialState("Alone"));
        assertEquals(SocialSituation.State.ALONE, SocialSituation.fromStringToSocialState("ALONE"));
        SocialSituation.fromStringToSocialState("abc");
    }

    @Test
    public void testGetStringList() {
        String[] array = {
                "None",
                "Alone",
                "Pair",
                "Several",
                "Crowd"
        };
        List<String> state_list = SocialSituation.getStringList();
        assertTrue(state_list.containsAll(Arrays.asList(array)));
    }
}
