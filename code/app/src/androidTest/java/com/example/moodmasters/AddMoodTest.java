package com.example.moodmasters;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Views.SignupLoginScreenActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddMoodTest {
    @Rule
    public ActivityScenarioRule<SignupLoginScreenActivity> scenario = new
            ActivityScenarioRule<SignupLoginScreenActivity>(SignupLoginScreenActivity.class);

    @BeforeClass
    public static void setup(){
        // Specific address for emulated device to access our localHost
        String androidLocalhost = "10.0.2.2";
        int portNumber = 8080;
        FirebaseFirestore.getInstance().useEmulator(androidLocalhost, portNumber);
    }

    @Before
    public void seedDatabase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference participants_ref = db.collection("participants");
        Participant[] participants = {
                new Participant("user_1")
        };

        for (Participant participant : participants) {
            DocumentReference doc_ref = participants_ref.document();
            doc_ref.set(participant);
        }
    }
}
