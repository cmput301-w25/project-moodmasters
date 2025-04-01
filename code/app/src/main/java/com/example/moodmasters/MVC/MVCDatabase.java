package com.example.moodmasters.MVC;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
/**
 * Class that contains all the important objects and interfaces related to the database queries
 * */
public class MVCDatabase {
    /**
     * Interface for fetching data from the database
     * */
    public interface Fetch {
        public interface OnSuccessFetchListener {
            void onSuccess(MVCBackend backend_object, boolean result);
        }
        /**
         * Interface for fetching database from the database
         * @param database
         *  Database object that stores documents and collections
         * @param model
         *  MVCModel that is needed to get other necessary data that might be needed in the function
         * @param listener
         *  Listener that will be executed after the query is done
         * */
        public void fetchDatabaseData(MVCDatabase database, MVCModel model, OnSuccessFetchListener listener); // necessary
    }
    /**
     * Interface for creating data on the database
     * */
    public interface Create {
        public interface OnSuccessCreateListener {
            void onSuccess(MVCBackend backend_object, boolean result);
        }
        /**
         * Interface for creating database on the database
         * @param database
         *  Database object that stores documents and collections
         * @param model
         *  MVCModel that is needed to get other necessary data that might be needed in the function
         * @param listener
         *  Listener that will be executed after the query is done
         * */
        public void createDatabaseData(MVCDatabase database, MVCModel model, OnSuccessCreateListener listener); // necessary
    }
    /**
     * Interface for adding some data to the database
     * */
    public interface Add{
        public interface OnSuccessAddListener {
            void onSuccess(MVCBackend backend_object, boolean result);
        }
        /**
         * Interface for adding database on the database
         * @param database
         *  Database object that stores documents and collections
         * @param object
         *  object to add to the database
         * @param listener
         *  Listener that will be executed after the query is done
         * */
        public <T> void addDatabaseData(MVCDatabase database, T object, OnSuccessAddListener listener);
    }
    /**
     * Interface for removing some data on the database
     * */
    public interface Remove{
        public interface OnSuccessRemoveListener {
            void onSuccess(MVCBackend backend_object, boolean result);
        }
        /**
         * Interface for remove database on the database
         * @param database
         *  Database object that stores documents and collections
         * @param object
         *  object to remove to the database
         * @param listener
         *  Listener that will be executed after the query is done
         * */
        public <T> void removeDatabaseData(MVCDatabase database, T object, OnSuccessRemoveListener listener);
    }
    private FirebaseFirestore db;
    private CollectionReference collection_ref;
    private Map<String, DocumentReference> doc_ref;

    public MVCDatabase(){
        db = FirebaseFirestore.getInstance();
        doc_ref = new HashMap<String, DocumentReference>();
    }
    /**
     * Creates and stores a collection reference
     * @param collection_name
     *  The name of the collection to add
     * */
    public void addCollection(String collection_name){
        if (collection_ref != null){
            return;
        }
        collection_ref = db.collection(collection_name);
    }
    /**
     * Creates and stores a document reference
     * @param document_name
     *  The name of the document to add
     * */
    public void addDocument(String document_name){
        if (doc_ref.containsKey(document_name)){
            return;
        }
        doc_ref.put(document_name, collection_ref.document(document_name));
    }
    /**
     * Gets the collection member stored in this class
     * @return
     *  Returns the collection member
     * */
    public CollectionReference getCollection(){
        return collection_ref;
    }
    /**
     * Gets the document member stored in this class
     * @param document_name
     *  The name of the document to return
     * @return
     *  Returns the document member
     * */
    public DocumentReference getDocument(String document_name){
        return doc_ref.get(document_name);
    }
}
