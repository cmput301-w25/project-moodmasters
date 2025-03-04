package com.example.moodmasters.MVC;

import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public abstract class MVCBackendList <T> implements MVCBackend{
    protected ArrayList<T> object_list;
    private FirebaseFirestore db;

    public MVCBackendList(List<T> init_array){
        object_list = new ArrayList<T>(init_array);
    }
    public MVCBackendList(){
        object_list = new ArrayList<T>();
    }
    public MVCBackendList(FirebaseFirestore db){
        object_list = new ArrayList<T>();
        this.db = db;
    }
    public MVCBackendList(ArrayList<Mood> list, FirebaseFirestore db) {
        object_list = new ArrayList<T>();
        this.db = db;
    }
    public void addObject(T object){
        object_list.add(object);
        updateDatabaseData(db);
    }
    public void removeObject(T object){
        object_list.remove(object);
        updateDatabaseData(db);
    }
    public void removeObject(int position){
        if (position >= object_list.size()){
            throw new InvalidParameterException("error");
        }
        object_list.remove(position);
        updateDatabaseData(db);
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
        updateDatabaseData(db);
    }
    public ArrayList<T> getList(){
        return object_list;
    }
}
