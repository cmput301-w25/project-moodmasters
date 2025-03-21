package com.example.moodmasters.MVC;

import android.content.Context;

import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;

import java.util.List;

public class MVCController{
    private MVCModel model;

    public MVCController(MVCModel model){
        this.model = new MVCModel();
    }
    public void createBackendObject(BackendObject.State create){
        model.createBackendObject(create);
    }
    public void addBackendView(MVCView view){
        model.addView(view);
    }
    public void addBackendView(MVCView view, BackendObject.State view_dependency){
        model.addView(view);
        model.addDependency(view, view_dependency);
    }
    public void addBackendView(MVCView view, List<BackendObject.State> view_dependencies){
        model.addView(view);
        for (BackendObject.State view_dependency: view_dependencies){
            model.addDependency(view, view_dependency);
        }
    }
    public void removeBackendView(MVCView view){
        model.removeView(view);
    }
    public void execute(MVCEvent event, Context context){
        event.executeEvent(context, model, this);
    }
}
