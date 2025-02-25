package com.example.moodmasters.MVC;
import android.content.Context;

public interface MVCEvent {
    public void executeEvent(Context context, MVCModel backend, MVCController controller);
}
