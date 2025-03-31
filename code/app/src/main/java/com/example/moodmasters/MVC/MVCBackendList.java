package com.example.moodmasters.MVC;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
/**
 * An abstract class for backend objects that are Lists aswell
 * */
public abstract class MVCBackendList <T> extends MVCBackend implements MVCDatabase.Add, MVCDatabase.Remove{
    protected ArrayList<T> object_list;

    /**
     * Constructor for List using a pre-existing List
     * @param init_array
     *  List that contains all the elements to initialize backend list with
     * */
    public MVCBackendList(List<T> init_array){
        object_list = new ArrayList<T>(init_array);
    }
    /**
     * Empty constructor for backend List creating an empty list
     * */
    public MVCBackendList(){
        object_list = new ArrayList<T>();
    }

    /**
     * Adds a object to the list
     * @param object
     *  The object to add to the list
     * */
    public void addObject(T object){
        object_list.add(object);
    }
    /**
     * Removes a object from the list
     * @param object
     *  The object to remove from the list
     * */
    public void removeObject(T object){
        object_list.remove(object);
    }
    /**
     * Removes a object from the list
     * @param position
     *  The position to remove the object from the list
     * */
    public void removeObject(int position){
        if (position >= object_list.size()){
            throw new InvalidParameterException("error");
        }
        object_list.remove(position);
    }
    /**
     * Gets a object from the list
     * @param position
     *  Specifies the position to get the object
     * @return
     *  Object at position specified by the argument
     * */
    public T getObjectPosition(int position){
        if (position >= object_list.size()){
            throw new InvalidParameterException("error");
        }
        return object_list.get(position);
    }
    /**
     * Replaces a object at the specified position in the list
     * @param position
     *  Specifies the position of the object to be replaced
     * @param new_object
     *  The object to replace corresponding object at position
     * */
    public void replaceObjectPosition(int position, T new_object){
        if (position >= object_list.size()){
            throw new InvalidParameterException("error");
        }
        object_list.set(position, new_object);
    }
    public void addAllObjects(List<T> other_object_list){
        object_list.addAll(other_object_list);
    }
    public void clearAllObjects(){
        object_list.clear();
    }
    public ArrayList<T> getList(){
        return object_list;
    }
}
