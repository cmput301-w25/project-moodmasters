package com.example.moodmasters.MVC;

import android.content.Context;

import java.util.List;

public class MVCController{
    private Context context;
    private MVCModel model;

    public MVCController(Context new_context){
        context = new_context;
        model = new MVCModel();
    }
    public MVCController(){
        model = new MVCModel();
    }
    public void setContext(Context new_context){
        context = new_context;
    }
    public void createBackendObject(MVCModel.BackendObject create){
        model.createBackendObject(create);
    }
    public void addBackendView(MVCView view){
        model.addView(view);
    }
    public void addBackendView(MVCView view, MVCModel.BackendObject view_dependency){
        model.addView(view);
        model.addDependency(view, view_dependency);
    }
    public void addBackendView(MVCView view, List<MVCModel.BackendObject> view_dependencies){
        model.addView(view);
        for (MVCModel.BackendObject view_dependency: view_dependencies){
            model.addDependency(view, view_dependency);
        }
    }
    public void removeBackendView(MVCView view){
        model.removeView(view);
    }
    public void execute(MVCEvent event){
        event.executeEvent(context, model, this);
    }
}
