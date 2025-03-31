package com.example.moodmasters.MVC;

import android.content.Context;

import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;

import java.util.List;

/**
 * Class representing the Model part of MVC and is responsible for facilitating communication between the View and the Model
 * when the View wants to manipulate data in the Model or when an object in the backend is needed to be created or a view needs to be
 * added to the backend
 *
 * */
public class MVCController{

    /**
     * This interface can be thought of a extension of the controller and for each UI interaction a corresponding class implementing
     * the MVCEvent interface will be created containing the code that will be executed for that UI interaction
     * */
    public interface MVCEvent {
        /**
         * This function contains all the code that should be executed on a UI interaction
         * @param context
         *  The app context that can be used to bring up new UI elements like fragments and activities
         * @param model
         *  The model that the controller can interact with for possible data manipulation
         * @param controller
         *  The controller responsible for executing the MVCEvent in the first place
         * */
        public void executeEvent(Context context, MVCModel model, MVCController controller);
    }

    private MVCModel model;

    /**
     * Empty constructor for the Controller, job is to just create the Model
     * */
    public MVCController(){
        model = new MVCModel();
    }
    /**
     * Creates the requested object in the backend
     * @param create
     *  An enum representing what object the model should create
     * */
    public void createBackendObject(BackendObject.State create){
        model.createBackendObject(create);
    }

    /**
     * Removes an object from the backend
     * @param remove
     *  The object to remove from the backend
     * */
    public void removeBackendObject(BackendObject.State remove){
        model.removeBackendObject(remove);
    }
    /**
     * Adds the given View to the backend and once the Model adds the object the Model will call the View's
     * corresponding initialize method
     * @param view
     *  The View that will be added to the backend
     *
     * */
    public void addBackendView(MVCView view){
        model.addView(view);
    }
    /**
     * Adds a View to the backend and also at the same time lets the Model know a dependency between the View and
     * one of the pre-existing backend objects so the Model knows what View objects to call initialize and update on
     * during updating data of a certain backend object
     * @param view
     *  The View that will be added to the backend
     * @param view_dependency
     *  The backend object the View depends on
     * */
    public void addBackendView(MVCView view, BackendObject.State view_dependency){
        model.addView(view);
        model.addDependency(view, view_dependency);
    }
    /**
     * Adds a View to the backend and also at the same time lets the Model know multiple dependencies between the View and
     * one of the multiple pre-existing backend objects so the Model knows what View objects to call initialize and update on
     * during updating data of a different backend object
     * @param view
     *  The View that will be added to the backend
     * @param view_dependencies
     *  The List of backend objects the View depends on
     * */
    public void addBackendView(MVCView view, List<BackendObject.State> view_dependencies){
        model.addView(view);
        for (BackendObject.State view_dependency: view_dependencies){
            model.addDependency(view, view_dependency);
        }
    }
    /**
     * Removes a View that already exists inside the Model, this will cause the Model not to call that View's corresponding update and
     * initialize methods
     * @param view
     *  The View to remove from the model
     * */
    public void removeBackendView(MVCView view){
        model.removeView(view);
    }

    /**
     * Checks whether the backend object exists or not
     * @param backend_object
     *  The backend object to check for existence
     * @return
     *  Returns a boolean representing whether the backend object exists
     * */
    public boolean existsBackendObject(BackendObject.State backend_object){
        return model.existsBackendObject(backend_object);
    }

    /**
     * Executes a event on UI interaction, takes in the MVCEvent that will contain the code to execute on the
     * UI interaction and also the context of the app for possible bringing up of new UI elements
     * @param event
     *  MVCEvent that contains code to execute for UI interaction
     * @param context
     *  Context of the app
     * */
    public void execute(MVCEvent event, Context context){
        event.executeEvent(context, model, this);
    }
}
