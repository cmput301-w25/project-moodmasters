package com.example.moodmasters.MVC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.moodmasters.Events.LoginSignupScreen.LoginSignupScreenOkEvent;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Counters;
import com.example.moodmasters.Objects.ObjectsBackend.FollowRequest;
import com.example.moodmasters.Objects.ObjectsBackend.FollowRequestList;
import com.example.moodmasters.Objects.ObjectsBackend.FollowerList;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Objects.ObjectsBackend.MoodFollowingList;
import com.example.moodmasters.Objects.ObjectsBackend.MoodHistoryList;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsBackend.UserSearch;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Objects.ObjectsBackend.MoodMap;
import com.example.moodmasters.R;

/**
 * Class that represents the Model part of the MVC framework, will be sent messages by the Controller to do certain requests like
 * data manipulation and as a result do that data manipulation and update the Views that depend on that data manipulation once it has
 * finished
 * */
public class MVCModel{
    private ArrayList<MVCView> views;
    private Map<BackendObject.State, MVCBackend> backend_objects;
    private Map<BackendObject.State, List<MVCView>> dependencies;
    private MVCDatabase database;

    /**
     * Empty constructor for the Model, initializes all members to empty
     * */
    public MVCModel(){
        views = new ArrayList<MVCView>();
        backend_objects = new HashMap<BackendObject.State, MVCBackend>();
        dependencies = new HashMap<BackendObject.State, List<MVCView>>();
        database = new MVCDatabase();

    }
    /**
     * Creates a backend object in the Model, currently there are 5 backend objects all represented as
     * elements of the enum BackendObject.State, some of these objects will only be created once and stay
     * alive over the course of the app till termination, others will change dynamically depending on the
     * users interaction with the app (ex. a USER backend object will be created on login and once the user logouts
     * the USER object will be destroyed and once the user re-logins the USER object will be created again)
     * @param backend_object
     *  Represents the backend object that should be created on a given call to this method
     * */
    public void createBackendObject(BackendObject.State backend_object){
        /*
        if (backend_objects.get(backend_object) != null){
            return;
            //throw new IllegalArgumentException("Error: Trying to add pre-existing backend object " + BackendObject.getString(backend_object));
        }
         */
        dependencies.putIfAbsent(backend_object, new ArrayList<MVCView>());
        if (backend_object == BackendObject.State.USER){
            Participant user = new Participant(LoginSignupScreenOkEvent.getUsername());
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
            FollowingList following_list = ((Participant) backend_objects.get(BackendObject.State.USER)).getFollowingList();
            backend_objects.put(backend_object, following_list);
        }else if (backend_object == BackendObject.State.FOLLOWERLIST){
            FollowerList follower_list = new FollowerList(((Participant) backend_objects.get(BackendObject.State.USER)).getUsername());
            backend_objects.put(backend_object, follower_list);
        }

        else if (backend_object == BackendObject.State.MOODHISTORYLIST){
            Participant user = ((Participant) this.getBackendObject(BackendObject.State.USER));
            MoodHistoryList mood_history_list = user.getMoodHistoryList();
            backend_objects.put(backend_object, mood_history_list);
        }
        else if (backend_object == BackendObject.State.MOODFOLLOWINGLIST){
            FollowingList following_list = (FollowingList) backend_objects.get(BackendObject.State.FOLLOWINGLIST);
            backend_objects.put(backend_object, following_list.getMoodFollowingList());
        }
        else if (backend_object == BackendObject.State.COUNTERS){
            Counters counters = new Counters(((Participant) backend_objects.get(BackendObject.State.USER)).getUsername());
            backend_objects.put(backend_object, counters);
        }
        else if (backend_object == BackendObject.State.USERSEARCH){
            UserSearch user_search = new UserSearch(((Participant) backend_objects.get(BackendObject.State.USER)).getUsername());
            backend_objects.put(backend_object, user_search);
        }
        else if (backend_object == BackendObject.State.FOLLOWREQUEST){
            FollowRequest follow_request = new FollowRequest(((Participant) backend_objects.get(BackendObject.State.USER)).getUsername());
            backend_objects.put(backend_object, follow_request);
        }
        else if (backend_object == BackendObject.State.FOLLOWREQUESTLIST){
            FollowRequestList follow_request_list = new FollowRequestList(((Participant) backend_objects.get(BackendObject.State.USER)).getUsername());
            backend_objects.put(backend_object, follow_request_list);
        }
        else if (backend_object == BackendObject.State.MOODMAP){
            String username = ((Participant) backend_objects.get(BackendObject.State.USER)).getUsername();
            List<MoodEvent> mood_history_list = new ArrayList<MoodEvent>((List<MoodEvent>) ((MoodHistoryList) backend_objects.get(BackendObject.State.MOODHISTORYLIST)).getList());
            List<MoodEvent> mood_following_list = new ArrayList<MoodEvent>((List<MoodEvent>) ((MoodFollowingList) backend_objects.get(BackendObject.State.MOODFOLLOWINGLIST)).getList());
            List<MoodEvent> init_mood_events = new ArrayList<MoodEvent>();

            List<MoodEvent> removables = new ArrayList<MoodEvent>();
            for (MoodEvent mood_event: mood_history_list){
                if (mood_event.getLocation() == null){
                    removables.add(mood_event);
                }
            }
            mood_history_list.removeAll(removables);

            removables = new ArrayList<MoodEvent>();
            for (MoodEvent mood_event: mood_following_list){
                if (mood_event.getLocation() == null){
                    removables.add(mood_event);
                }
            }
            mood_following_list.removeAll(removables);
            init_mood_events.addAll(mood_history_list);
            init_mood_events.addAll(mood_following_list);
            MoodMap mood_map = new MoodMap(init_mood_events, mood_history_list, mood_following_list, username);
            backend_objects.put(backend_object, mood_map);
        }
        System.out.println("CREATING BACKEND OBJECT " + BackendObject.getString(backend_object));
    }
    /**
     * Remove a pre-existing backend object from the Model, if the backend object does not exists throw an exception
     * @param backend_object
     *  The backend object to remove from the Model
     * */
    public void removeBackendObject(BackendObject.State backend_object){
        if (!backend_objects.containsKey(backend_object)){
            throw new IllegalArgumentException("Error: Trying to delete non-existent backend object " + BackendObject.getString(backend_object));
        }
        System.out.println("REMOVING BACKEND OBJECT " + BackendObject.getString(backend_object));
        backend_objects.remove(backend_object);
        dependencies.remove(backend_object);
    }
    /**
     * Checks if a backend object exists or not
     * @param backend_object
     *  The backend object to return from the Model
     * @return
     *  A boolean showing whether the backed object exists or not
     * */
    public boolean existsBackendObject(BackendObject.State backend_object){
        MVCBackend object = backend_objects.get(backend_object);
        return object != null;
    }
    /**
     * Return a pre-existing backend object to the caller of this method
     * @param backend_object
     *  The backend object to return from the Model
     * @return
     *  A backend object that will be returned depending on the argument
     * */
    public MVCBackend getBackendObject(BackendObject.State backend_object){
        return backend_objects.get(backend_object);
    }
    /**
     * Adds a View to the Model for storage, also calls it initialize method if there is some needed
     * code to be executed immediately on addition to the Model
     * @param new_view
     *  The new View that will be added to the Model
     * */
    public void addView(MVCView new_view){
        if (views.contains(new_view)){
            throw new IllegalArgumentException("Error: Trying to add pre-existing view");
        }
        views.add(new_view);
        new_view.initialize(this);
    }
    /**
     * Removes a View from storage in the Model, if the View does not exist in the Model already we
     * throw an exception
     * @param delete_view
     *  The view that should be deleted from the Model
     * */
    public void removeView(MVCView delete_view){
        if (!views.contains(delete_view)){
            throw new IllegalArgumentException("Error: Trying to delete non-existent view");
        }
        views.remove(delete_view);
        for (List<MVCView> l : dependencies.values()){
            l.remove(delete_view);
        }
    }
    /**
     * Adds a dependency between a backend object and a pre-existing View that is stored in the Model, this is
     * so the Model knows what Views to call update on during data manipulation of backend objects
     * @param new_view
     *  The View that depends on the backend object, will now have it's update method now called on changes
     *  to the backend object
     * @param backend_object
     *  The backend object the View depends on
     * */
    public void addDependency(MVCView new_view, BackendObject.State backend_object){
        if (!views.contains(new_view)){
            throw new IllegalArgumentException("Error: View does not exist in the backend");
        }
        dependencies.get(backend_object).add(new_view);
    }
    /**
     * Calls the update method on all the Views the Model knows of to notify them to update, this is only
     * done for Views that depend on the backend object given as argument
     * @param backend_object
     *  The backend object the View must depend on for it's update method to be called
     * */
    public void notifyViews(BackendObject.State backend_object){
        if (!dependencies.containsKey(backend_object)){
            throw new IllegalArgumentException("Error: Backend object " + BackendObject.getString(backend_object) + " does not exist yet");
        }
        List<MVCView> update_views = dependencies.get(backend_object);
        for (MVCView v : update_views){
            v.update( this );
        }
    }

    /**
     * Adds a new object to a backend object only if that backend object is a List, if it isn't a exception is
     * thrown
     * @param backend_object
     *  The backend object the object will be added to
     * @param object
     *  The object to add to the backend object
     * */
    public <T> void addToBackendList(BackendObject.State backend_object, T object, MVCDatabase.Add.OnSuccessAddListener listener){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        obj_list.addObject(object);
        obj_list.addDatabaseData(database, object, listener);
        notifyViews(backend_object);
    }
    /**
     * Removes a object from a backend object only if that backend object is a List, if it isn't a exception is
     * thrown
     * @param backend_object
     *  The backend object the object will be removed from
     * @param object
     *  The object to remove from the backend object
     * */
    public <T> void removeFromBackendList(BackendObject.State backend_object, T object, MVCDatabase.Remove.OnSuccessRemoveListener listener){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        obj_list.removeObject(object);
        obj_list.removeDatabaseData(database, object, listener);
        notifyViews(backend_object);
    }
    /**
     * Removes a object from a backend object only if that backend object is a List, if it isn't a exception is
     * thrown
     * @param backend_object
     *  The backend object the object will be removed from
     * @param position
     *  Position in the backend object List to remove an object
     * */
    public void removeFromBackendList(BackendObject.State backend_object, int position, MVCDatabase.Remove.OnSuccessRemoveListener listener){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList obj_list = (MVCBackendList) obj;
        Object object = obj_list.getObjectPosition(position);
        obj_list.removeObject(position);
        obj_list.removeDatabaseData(database, object, listener);
        notifyViews(backend_object);
    }
    /**
     * Gets a object from a backend object only if that backend object is a List, if it isn't a exception is
     * thrown
     * @param backend_object
     *  The backend object the object will be gotten from
     * @param position
     *  Position in the backend object List to get the object
     * */
    public <T> T getFromBackendList(BackendObject.State backend_object, int position){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        return obj_list.getObjectPosition(position);
    }
    /**
     * Replace a object from a backend object only if that backend object is a List, if it isn't a exception is
     * thrown
     * @param backend_object
     *  The backend object the object will replace another object in
     * @param position
     *  Position of the replacee object in the backend object
     * @param new_object
     *  The replacer object for the object in the backend object
     * */
    public <T> void replaceObjectBackendList(BackendObject.State backend_object, int position, T new_object, MVCDatabase.Add.OnSuccessAddListener add_listener, MVCDatabase.Remove.OnSuccessRemoveListener remove_listener){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to add object to non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        Object old_object = obj_list.getObjectPosition(position);
        obj_list.replaceObjectPosition(position, new_object);
        obj_list.removeDatabaseData(database, old_object, remove_listener);
        obj_list.addDatabaseData(database, new_object, add_listener);
        notifyViews(backend_object);
    }
    /**
     * Returns a backend object List, if the backend object is not a List then a exception will be thrown
     * @param backend_object
     *  The backend object List that will be returned
     * */
    public <T> ArrayList<T> getBackendList(BackendObject.State backend_object){
        MVCBackend obj = backend_objects.get(backend_object);
        if (!(obj instanceof MVCBackendList)){
            throw new IllegalArgumentException("Error: Trying to get non-list backend object " + BackendObject.getString(backend_object));
        }
        MVCBackendList<T> obj_list = (MVCBackendList <T>) obj;
        return obj_list.getList();
    }
    /**
     * Fetches the database to update the backend object
     * @param backend_object
     *  The backend object to execute a database fetch for
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    public void fetchDatabaseDataBackendObject(BackendObject.State backend_object, MVCDatabase.Fetch.OnSuccessFetchListener listener){
        MVCDatabase.Fetch fetch_object = (MVCDatabase.Fetch) backend_objects.get(backend_object);
        fetch_object.fetchDatabaseData(database, this, listener);
    }
    /**
     * Creates a new object in the database associated to the backend object
     * @param backend_object
     *  The backend object to execute a database create for
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    public void createDatabaseDataBackendObject(BackendObject.State backend_object, MVCDatabase.Create.OnSuccessCreateListener listener){
        MVCDatabase.Create create_object = (MVCDatabase.Create) backend_objects.get(backend_object);
        create_object.createDatabaseData(database, this, listener);
    }
    /**
     * Removes a object from the database associated to the backend object
     * @param backend_object
     *  The backend object to execute a database remove for
     * @param object
     *  Object that will be removed
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    public <T> void removeDatabaseDataBackendObject(BackendObject.State backend_object, T object, MVCDatabase.Remove.OnSuccessRemoveListener listener){
        MVCDatabase.Remove fetch_object = (MVCDatabase.Remove) backend_objects.get(backend_object);
        fetch_object.removeDatabaseData(database, object, listener);
    }
    /**
     * Adds a object from the database associated to the backend object
     * @param backend_object
     *  The backend object to execute a database add for
     * @param object
     *  Object that will be added
     * @param listener
     *  Listener that will be executed after the query is done
     * */
    public <T> void addDatabaseDataBackendObject(BackendObject.State backend_object, T object, MVCDatabase.Add.OnSuccessAddListener listener){
        MVCDatabase.Add fetch_object = (MVCDatabase.Add) backend_objects.get(backend_object);
        fetch_object.addDatabaseData(database, object, listener);
    }
    /**
     * Returns the MVCDatabase instance
     * @return
     *  MVCDatabase instance
     * */
    public MVCDatabase getDatabase(){
        return database;
    }

}
