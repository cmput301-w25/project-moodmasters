package com.example.moodmasters;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MoodHistoryListTest {
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
        MockitoAnnotations.openMocks(this);
        when(mock_firestore.collection("participants")).thenReturn(mock_participant_collection);
        when(mock_participant_collection.document()).thenReturn(mock_doc_ref);
        when(mock_participant_collection.document(anyString())).thenReturn(mock_doc_ref);
        mood_history_list = new MoodHistoryList();
    }

    @Test
    public void testUpdateDatabaseData() {
        //mood_history_list.updateDatabaseData(mock_doc_ref);
        verify(mock_doc_ref).set(mood_history_list);
    }
}
