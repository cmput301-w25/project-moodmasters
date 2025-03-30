package com.example.moodmasters.MVC;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MVCDatabase {
    public interface Update {
        public void updateDatabaseData(MVCDatabase database, MVCModel model);
    }
    public interface Set {
        public void setDatabaseData(MVCDatabase database, MVCModel model); // necessary
    }
    public interface Add{
        public <T> void addDatabaseData(MVCDatabase database, T object);
    }
    public interface Remove{
        public <T> void removeDatabaseData(MVCDatabase database, T object);
    }
    private FirebaseFirestore db;
    private CollectionReference collection_ref;
    private Map<String, DocumentReference> doc_ref;

    public MVCDatabase(){
        db = FirebaseFirestore.getInstance();
        doc_ref = new HashMap<String, DocumentReference>();
    }

    public void addCollection(String collection_name){
        collection_ref = db.collection(collection_name);
    }

    public void addDocument(String document_name){
        doc_ref.put(document_name, collection_ref.document(document_name));
    }

    public CollectionReference getCollection(){
        return collection_ref;
    }
    public DocumentReference getDocument(String document_name){
        return doc_ref.get(document_name);
    }
}
