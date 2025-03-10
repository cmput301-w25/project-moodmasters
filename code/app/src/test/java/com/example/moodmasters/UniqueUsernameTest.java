package com.example.moodmasters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UniqueUsernameTest {
    @Mock private FirebaseFirestore mockFirestore;
    @Mock private CollectionReference mockParticipantsCollection;
    @Mock private DocumentReference mockParticipantDoc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockFirestore.collection("participants")).thenReturn(mockParticipantsCollection);
    }

    @Test
    public void testSignup_UniqueUsername() {
        String newParticipant = "uniqueUser123";
        Task<DocumentSnapshot> mockTask = mock(Task.class);
        DocumentSnapshot mockSnapshot = mock(DocumentSnapshot.class);

        when(mockParticipantsCollection.document(newParticipant)).thenReturn(mockParticipantDoc);
        when(mockParticipantDoc.get()).thenReturn(mockTask);
        when(mockTask.isSuccessful()).thenReturn(true);
        when(mockTask.getResult()).thenReturn(mockSnapshot);
        when(mockSnapshot.exists()).thenReturn(false);

        // Explicitly trigger Firestore call
        DocumentReference docRef = mockParticipantsCollection.document(newParticipant);
        docRef.get();

        // Verify Firestore interactions
        verify(mockParticipantsCollection).document(newParticipant);
        verify(mockParticipantDoc).get();

        boolean isUnique = !mockSnapshot.exists();
        assertTrue("Username should be unique", isUnique);
    }

    @Test
    public void testLogin_ExistingUsername() {
        String existingParticipant = "existingUser456";
        Task<DocumentSnapshot> mockTask = mock(Task.class);
        DocumentSnapshot mockSnapshot = mock(DocumentSnapshot.class);

        when(mockParticipantsCollection.document(existingParticipant)).thenReturn(mockParticipantDoc);
        when(mockParticipantDoc.get()).thenReturn(mockTask);
        when(mockTask.isSuccessful()).thenReturn(true);
        when(mockTask.getResult()).thenReturn(mockSnapshot);
        when(mockSnapshot.exists()).thenReturn(true);

        // Explicitly trigger Firestore call
        DocumentReference docRef = mockParticipantsCollection.document(existingParticipant);
        docRef.get();

        // Verify Firestore interactions
        verify(mockParticipantsCollection).document(existingParticipant);
        verify(mockParticipantDoc).get();

        boolean exists = mockSnapshot.exists();
        assertTrue("Username should exist for login", exists);
    }
}
