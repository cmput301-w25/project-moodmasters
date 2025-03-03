package com.example.moodmasters.MVC;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.api.Backend;
import com.google.firebase.firestore.FirebaseFirestore;

public class MVCModel {
    private ArrayList<MVCView> views;
    private Map<BackendObject.State, MVCBackend> backend_objects;
    private Map<BackendObject.State, List<MVCView>> dependencies;
    private FirebaseFirestore db;


    public MVCModel(){
        views = new ArrayList<MVCView>();
        backend_objects = new HashMap<BackendObject.State, MVCBackend>();
        dependencies = new HashMap<BackendObject.State, List<MVCView>>();
    }
    public void createBackendObject(BackendObject.State backend_object){
        if (backend_objects.get(backend_object) != null){
            throw new IllegalArgumentException("Error: Trying to add pre-existing backend object " + BackendObject.getString(backend_object));
        }
        dependencies.put(backend_object, new ArrayList<MVCView>());
        if (backend_object == BackendObject.State.USER){
            Participant user = new Participant(LoginScreenOkEvent.getUsername());
            backend_objects.put(backend_object, user);
        }
        else if (backend_object == BackendObject.State.MOODLIST){
            Mood happy = new Mood(Emotion.State.HAPPY, R.color.mood_happy_color, R.string.mood_emoji_happy);
            Mood sad = new Mood(Emotion.State.SAD, R.color.mood_sad_color, R.string.mood_emoji_sad);
            Mood angry = new Mood(Emotion.State.ANGRY, R.color.mood_angry_color, R.string.mood_emoji_angry);
            Mood scared = new Mood(Emotion.State.SCARED, R.color.mood_scared_color, R.string.mood_emoji_scared);
            Mood disgusted = new Mood(Emotion.State.DISGUSTED, R.color.mood_disgusted_color, R.string.mood_emoji_disgusted);
            Mood confused = new Mood(Emotion.State.CONFUSED, R.color.mood_confused_color, R.string.mood_emoji_confused);
            Mood ashamed = new Mood(Emotion.State.ASHAMED, R.color.mood_ashamed_color, R.string.mood_emoji_ashamed);
            Mood surprised = new Mood(Emotion.State.SURPRISED, R.color.mood_surprised_color, R.string.mood_emoji_surprised);
            List<Mood> init_list = Arrays.asList(happy, sad, angry, scared, disgusted, confused, ashamed, surprised);
            MoodList mood_list = new MoodList(init_list);
            backend_objects.put(backend_object, mood_list);
        }
        else if (backend_object == BackendObject.State.FOLLOWINGLIST){

        }
        else if (backend_object == BackendObject.State.MOODHISTORYLIST){
            MoodHistoryList mood_history_list = new MoodHistoryList();          /* should create mood history list via user object but this is fine for now */
            backend_objects.put(backend_object, mood_history_list);
        }
        else if (backend_object == BackendObject.State.MOODFOLLOWINGLIST){
            // make sure following list is generated first and use that to generate mood following list
        }
    }
    public void removeBackendObject(BackendObject.State backend_object){
        if (!backend_objects.containsKey(backend_object)){
            throw new IllegalArgumentException("Error: Trying to delete non-existent backend object " + BackendObject.getString(backend_object));
        }
        backend_objects.remove(backend_object);
        dependencies.remove(backend_object);
    }
    public MVCBackend getBackendObject(BackendObject.State backend_object){
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
    public void addDependency(MVCView new_view, BackendObject.State backend_object){
        if (!views.contains(new_view)){
            throw new IllegalArgumentException("Error: View does not exist in the backend");
        }
        dependencies.get(backend_object).add(new_view);
    }
    public void notifyViews(BackendObject.State backend_object){
        if (!dependencies.containsKey(backend_object)){
            throw new IllegalArgumentException("Error: Backend object " + BackendObject.getString(backend_object) + " does not exist yet");
        }
        List<MVCView> update_views = dependencies.get(backend_object);
        for (MVCView v : update_views){
            v.update( this );
        }
    }
    public <T> void addToBackendList(BackendObject.State backend_object, T object){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        obj_list.addObject(object);
        // TODO: update database
        notifyViews(backend_object);
    }

    public <T> void removeFromBackendList(BackendObject.State backend_object, T object){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        obj_list.removeObject(object);
        // TODO: update database
        notifyViews(backend_object);
    }
    public void removeFromBackendList(BackendObject.State backend_object, int position){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList obj_list = (MVCBackendList) obj;
        obj_list.removeObject(position);
        // TODO: update database
        notifyViews(backend_object);
    }

    public <T> T getFromBackendList(BackendObject.State backend_object, int position){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        return obj_list.getObjectPosition(position);
    }

    public <T> void replaceObjectBackendList(BackendObject.State backend_object, int position, T new_object){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        obj_list.replaceObjectPosition(position, new_object);
        // TODO: update database
        notifyViews(backend_object);
    }

    public <T> ArrayList<T> getBackendList(BackendObject.State backend_object){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        return obj_list.getList();
    }

}
