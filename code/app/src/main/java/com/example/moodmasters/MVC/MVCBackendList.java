package com.example.moodmasters.MVC;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public abstract class MVCBackendList <T> implements MVCBackend{
    protected ArrayList<T> object_list;
    private DocumentReference doc_ref;
    private DocumentSnapshot snapshot;

    public MVCBackendList(List<T> init_array){
        object_list = new ArrayList<T>(init_array);
    }
    public MVCBackendList(){
        object_list = new ArrayList<T>();
    }
    public MVCBackendList(DocumentReference doc_ref, DocumentSnapshot snapshot){
        object_list = new ArrayList<T>();
        this.doc_ref = doc_ref;
        this.snapshot = snapshot;
    }
    public MVCBackendList(ArrayList<T> list, DocumentReference doc_ref, DocumentSnapshot snapshot) {
        object_list = list;
        this.doc_ref = doc_ref;
        this.snapshot = snapshot;
    }
    public void addObject(T object){
        object_list.add(object);
        updateDatabaseData(doc_ref);
    }
    public void removeObject(T object){
        object_list.remove(object);
        updateDatabaseData(doc_ref);
    }
    public void removeObject(int position){
        if (position >= object_list.size()){
            throw new InvalidParameterException("error");
        }
        object_list.remove(position);
        updateDatabaseData(doc_ref);
    }
    public T getObjectPosition(int position){
        if (position >= object_list.size()){
            throw new InvalidParameterException("error");
        }
        return object_list.get(position);
    }

    public void replaceObjectPosition(int position, T new_object){
        if (position >= object_list.size()){
            throw new InvalidParameterException("error");
        }
        object_list.set(position, new_object);
        updateDatabaseData(doc_ref);
    }
    public ArrayList<T> getList(){
        return object_list;
    }
}
