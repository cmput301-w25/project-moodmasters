package com.example.moodmasters;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import android.app.Activity;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.tasks.Task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.lang.reflect.Field;

public class FollowRequestsActivityTest {

    @Mock
    private FirebaseFirestore mockFirestore;

    @Mock
    private CollectionReference mockParticipantsRef;

    @Mock
    private DocumentReference mockTargetUserDoc;

    @Mock
    private CollectionReference mockFollowRequestsRef;

    @Mock
    private DocumentReference mockFollowRequestDoc;

    @Mock
    private QueryDocumentSnapshot mockDocument;

    private FollowingList followingList;
    private String currentUser = "malek";
    private String targetUser = "ahmed";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Create a real FollowingList object (which normally uses the real Firestore)
        followingList = new FollowingList(currentUser);

        // Use reflection to inject the mock Firestore into the private `db` field
        Field firestoreField = FollowingList.class.getDeclaredField("db");
        firestoreField.setAccessible(true);
        firestoreField.set(followingList, mockFirestore);

        // Mock Firestore collection structure
        when(mockFirestore.collection("participants")).thenReturn(mockParticipantsRef);
        when(mockParticipantsRef.document(targetUser)).thenReturn(mockTargetUserDoc);
        when(mockTargetUserDoc.collection("followRequests")).thenReturn(mockFollowRequestsRef);
        when(mockFollowRequestsRef.document(currentUser)).thenReturn(mockFollowRequestDoc);

        // Ensure Firestore allows the set() operation
        when(mockFollowRequestDoc.set(Mockito.anyMap())).thenReturn(Tasks.forResult(null));
    }

    @Test
    public void testFollowRequestAdded() throws InterruptedException {
        // Malek sends a follow request to Ahmad
//        followingList.sendFollowRequest(targetUser);

        // Sleep to allow Firestore to process the request
        Thread.sleep(2000);

        // Make sure firestore store the correct username
        ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);
        verify(mockFollowRequestDoc).set(captor.capture());

        assertEquals(currentUser, captor.getValue().get("userId"));

        Thread.sleep(2000);
        // Check Ahmad's follow request's to make sure malek is now added
//        followingList.fetchFollowRequests(requests -> {
//            assertEquals(List.of(currentUser), requests);
//        });
    }
}



