package com.example.moodmasters.MVC;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.firestore.FirebaseFirestore;

public class MVCModel {
    private ArrayList<MVCView> views;
    private Map<BackendObject, MVCBackend> backend_objects;
    private Map<BackendObject, List<MVCView>> dependencies;
    private FirebaseFirestore db;

    public enum BackendObject{
        // TODO: Add enums for backend objects here
    }

    public MVCModel(){
        views = new ArrayList<MVCView>();
        backend_objects = new HashMap<BackendObject, MVCBackend>();
        dependencies = new HashMap<BackendObject, List<MVCView>>();
    }
    public void createBackendObject(BackendObject backend_object){
        if (backend_objects.get(backend_object) != null){
            throw new IllegalArgumentException("Error: Trying to add pre-existing backend object");
        }
        dependencies.put(backend_object, new ArrayList<MVCView>());
        // TODO: Add if statements going over BackendObjects enums here to add backend objects
    }
    public MVCBackend getBackendObject(BackendObject backend_object){
        return backend_objects.get(backend_object);
    }
    public void addView(MVCView new_view){
        if (views.contains(new_view)){
            throw new IllegalArgumentException("Error: Trying to add pre-existing view");
        }
        views.add(new_view);
        new_view.initialize(this);
    }
    public void removeView(MVCView delete_view){
        if (!views.contains(delete_view)){
            throw new IllegalArgumentException("Error: Trying to delete non-existent view");
        }
        views.remove(delete_view);
        for (List<MVCView> l : dependencies.values()){
            l.remove(delete_view);
        }
    }
    public void addDependency(MVCView new_view, BackendObject backend_object){
        if (!views.contains(new_view)){
            throw new IllegalArgumentException("Error: View does not exist in the backend");
        }
        dependencies.get(backend_object).add(new_view);
    }
    public void notifyViews(BackendObject backend_object){
        if (!dependencies.containsKey(backend_object)){
            throw new IllegalArgumentException("Error: Backend object does not exist yet");
        }
        List<MVCView> update_views = dependencies.get(backend_object);
        for (MVCView v : update_views){
            v.update( this );
        }
    }
    public <T> void addToBackendList(BackendObject backend_object, T object){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object");
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        obj_list.addObject(object);
        // TODO: update database
        notifyViews(backend_object);
    }

    public <T> void removeFromBackendList(BackendObject backend_object, T object){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object");
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        obj_list.removeObject(object);
        // TODO: update database
        notifyViews(backend_object);
    }
    public void removeFromBackendList(BackendObject backend_object, int position){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object");
        }
        MVCBackendList obj_list = (MVCBackendList) obj;
        obj_list.removeObject(position);
        // TODO: update database
        notifyViews(backend_object);
    }

    public <T> T getFromBackendList(BackendObject backend_object, int position){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object");
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        return obj_list.getObjectPosition(position);
    }

    public <T> void replaceObjectBackendList(BackendObject backend_object, int position, T new_object){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object");
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        obj_list.replaceObjectPosition(position, new_object);
        // TODO: update database
        notifyViews(backend_object);
    }

    public <T> ArrayList<T> getBackendList(BackendObject backend_object){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object");
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        return obj_list.getList();
    }

}
