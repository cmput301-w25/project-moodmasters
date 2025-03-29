package com.example.moodmasters;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.moodmasters.Events.UserSearchOkEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class UserSearchActivityTest {

    @Mock
    private FirebaseFirestore mockFirestore;

    @Mock
    private CollectionReference mockUsersRef;

    @Mock
    private QueryDocumentSnapshot mockDocument1, mockDocument2, mockDocument3;

    @Mock
    private Activity mockActivity;

    @Mock
    private ListView mockListView;

    @Mock
    private ArrayAdapter<String> mockAdapter;

    private UserSearchOkEvent userSearchOkEvent;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Participant currentUser = new Participant("malek");
        userSearchOkEvent = new UserSearchOkEvent(currentUser);

        // Mock Firestore collection reference
        when(mockFirestore.collection("participants")).thenReturn(mockUsersRef);

        // Mock Firestore query results
        List<DocumentSnapshot> mockDocuments = Arrays.asList(mockDocument1, mockDocument2, mockDocument3);
        QuerySnapshot mockQuerySnapshot = Mockito.mock(QuerySnapshot.class);
        when(mockQuerySnapshot.getDocuments()).thenReturn(mockDocuments);
        when(mockUsersRef.get()).thenReturn(Tasks.forResult(mockQuerySnapshot));

        // Mock user documents
        when(mockDocument1.getString("username")).thenReturn("mona");
        when(mockDocument2.getString("username")).thenReturn("mohammed");
        when(mockDocument3.getString("username")).thenReturn("maryam");
    }

    @Test
    public void testUserSearchWithM() throws InterruptedException {
        //Test that the letter 'm' will display all participant's usernames that begin with the letter 'm'
        // Execute search
        userSearchOkEvent.executeSearch(mockActivity, "m", mockListView, mockAdapter);

        // Sleep for 3 seconds to allow Firestore query to complete
        Thread.sleep(3000);

        // Capture adapter updates
        ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockAdapter).clear();
        verify(mockAdapter).addAll(captor.capture());
        verify(mockAdapter).notifyDataSetChanged();
        // Verify expected results
        assertEquals(new HashSet<>(List.of("mona", "maryam", "mohammed")), new HashSet<>(captor.getValue()));
    }

    @Test
    public void testUserSearchWithMo() throws InterruptedException {
        //Test that 'mo' will display all participant's usernames that begin with 'mo'
        // Execute search
        userSearchOkEvent.executeSearch(mockActivity, "mo", mockListView, mockAdapter);

        // Sleep for 3 seconds to allow Firestore query to complete
        Thread.sleep(3000);

        // Capture adapter updates
        ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockAdapter).clear();
        verify(mockAdapter).addAll(captor.capture());
        verify(mockAdapter).notifyDataSetChanged();

        // Verify expected results
        assertEquals(new HashSet<>(List.of("mona", "mohammed")), new HashSet<>(captor.getValue()));
    }

    @Test
    public void testUserSearchWithMona() throws InterruptedException {
        //Test that when searching a specific username it will appear alone, like mona
        // Execute search
        userSearchOkEvent.executeSearch(mockActivity, "mona", mockListView, mockAdapter);

        // Sleep for 3 seconds to allow Firestore query to complete
        Thread.sleep(3000);

        // Capture adapter updates
        ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockAdapter).clear();
        verify(mockAdapter).addAll(captor.capture());
        verify(mockAdapter).notifyDataSetChanged();

        // Verify expected results
        assertEquals(new HashSet<>(List.of("mona")), new HashSet<>(captor.getValue()));
    }
}
