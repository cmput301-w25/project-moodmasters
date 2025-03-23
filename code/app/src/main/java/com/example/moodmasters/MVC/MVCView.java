package com.example.moodmasters.MVC;


/**
 * Class that represents the view part of the MVC framework, all UI classes will extend this interface if there exists
 * a UI element in that class that can be interacted with that brings up a different activity or fragment, or initiates
 * some sort of data manipulation in the backend. Once a UI interaction has taken place the View will request the controller
 * to handle it bringing up a new activity or fragement, or communicate with the backend for data manipulation (this will depend
 * on the UI element that was interacted with). If the UI interaction causes data manipulation in the backend the controller will
 * ask the model to change said backend data and the model will update the view telling it that the data manipulation is finished.
 *
 * */
public interface MVCView {
    public final MVCController controller = new MVCController();

    /**
     * The function that the model will call on all objects that implement MVCView once a data manipulation event
     * has occurred to update the view and notify it to possibly change its look
     * @param model
     *  The model part of the MVC framework, used for easy access to the backend objects so the View has easy access
     *  to the changed data
     */
    public abstract void update(MVCModel model);
    /**
     * The function that the model will call on initialization of view objects being inserted into the model, this is to account
     * for possibly different code being run on initialization of the view being inserted into the model than on normal updates
     * @param model
     *  The model part of the MVC framework, used for easy access to the backend objects so the View has easy access
     *  to the changed data
     */
    public abstract void initialize(MVCModel model);
}
