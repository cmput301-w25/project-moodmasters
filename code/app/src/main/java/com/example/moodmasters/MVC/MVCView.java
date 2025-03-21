package com.example.moodmasters.MVC;


/**
 * Want to make this a abstract class but java is a shit language that only allows single inherritance
 *
 * */
public interface MVCView {
    MVCModel model = new MVCModel();
    public final MVCController controller = new MVCController(model);
    public abstract void update(MVCModel model);
    public abstract void initialize(MVCModel model);
}
