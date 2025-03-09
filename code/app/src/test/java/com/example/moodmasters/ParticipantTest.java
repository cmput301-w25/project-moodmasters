package com.example.moodmasters;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;

public class ParticipantTest {
    private Participant participant;
    private HashMap list_map;
    private HashMap mood_map;
    private MoodHistoryList mood_history_list;

    @Mock
    private FirebaseFirestore mock_firestore;

    @Mock
    private CollectionReference mock_participant_collection;

    @Mock
    private DocumentReference mock_doc_ref;

    @Mock
    private DocumentSnapshot mock_snapshot;

    @Before
    public void setUp() {
        participant = new Participant("user_1");

        mood_map = new HashMap();
        mood_map.put("color", 2131034826L);
        mood_map.put("emoticon", 2131755130L);
        mood_map.put("emotion", "HAPPY");
        mood_map.put("emotionString", "Happy");

        list_map = new HashMap();
        list_map.put("datetime", "Mar 04 2025 | 17:33");
        list_map.put("epochTime", 1741134810487L);
        list_map.put("mood", mood_map);
        list_map.put("reason", "no reason");
        list_map.put("situation", "NONE");
        list_map.put("trigger", "nothing");

        ArrayList array_list = new ArrayList<>();
        array_list.add(list_map);

        MockitoAnnotations.openMocks(this);
        when(mock_firestore.collection("participants")).thenReturn(mock_participant_collection);
        when(mock_participant_collection.document()).thenReturn(mock_doc_ref);
        when(mock_participant_collection.document(anyString())).thenReturn(mock_doc_ref);
        when(mock_snapshot.get("list")).thenReturn(array_list);
        when(mock_snapshot.exists()).thenReturn(true);
    }

    @Test
    public void testSetDatabaseData() {
        participant.setDatabaseData(mock_doc_ref, mock_snapshot);
        verify(mock_snapshot).get("list");
        assert participant.getMoodHistoryList().getList().get(0).getMood().getEmotion().equals(Emotion.State.HAPPY);
    }

    @Test
    public void testUpdateDatabaseData() {
        participant.updateDatabaseData(mock_doc_ref);
        verify(mock_doc_ref).set(participant.getMoodHistoryList());
    }
}
