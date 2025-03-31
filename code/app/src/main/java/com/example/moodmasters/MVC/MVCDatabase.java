package com.example.moodmasters.MVC;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MVCDatabase {
    public interface Update {
        public void updateDatabaseData(MVCDatabase database, MVCModel model);
    }
    public interface Fetch {
        public interface OnSuccessFetchListener {
            void onSuccess(MVCBackend backend_object, boolean result);
        }
        public void fetchDatabaseData(MVCDatabase database, MVCModel model, OnSuccessFetchListener listener); // necessary
    }
    public interface Create {
        public interface OnSuccessCreateListener {
            void onSuccess(MVCBackend backend_object, boolean result);
        }
        public void createDatabaseData(MVCDatabase database, MVCModel model, OnSuccessCreateListener listener); // necessary
    }
    public interface Add{
        public interface OnSuccessAddListener {
            void onSuccess(MVCBackend backend_object, boolean result);
        }
        public <T> void addDatabaseData(MVCDatabase database, T object, OnSuccessAddListener listener);
    }
    public interface Remove{
        public interface OnSuccessRemoveListener {
            void onSuccess(MVCBackend backend_object, boolean result);
        }
        public <T> void removeDatabaseData(MVCDatabase database, T object, OnSuccessRemoveListener listener);
    }
    private FirebaseFirestore db;
    private CollectionReference collection_ref;
    private Map<String, DocumentReference> doc_ref;

    public MVCDatabase(){
        db = FirebaseFirestore.getInstance();
        doc_ref = new HashMap<String, DocumentReference>();
    }

    public void addCollection(String collection_name){
        if (collection_ref != null){
            return;
        }
        collection_ref = db.collection(collection_name);
    }

    public void addDocument(String document_name){
        if (doc_ref.containsKey(document_name)){
            return;
        }
        doc_ref.put(document_name, collection_ref.document(document_name));
    }

    public CollectionReference getCollection(){
        return collection_ref;
    }
    public DocumentReference getDocument(String document_name){
        return doc_ref.get(document_name);
    }
}
