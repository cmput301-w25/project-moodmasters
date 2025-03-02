package com.example.moodmasters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.moodmasters.Events.LogOutEvent;
import com.example.moodmasters.Events.MoodHistoryListMenuEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;

public class MenuScreenFragment extends DialogFragment implements MVCView {
    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        // skip for now
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.options_menu, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Button logout_button = view.findViewById(R.id.options_logout_button);
        logout_button.setOnClickListener(v ->{
            controller.execute(new LogOutEvent(), getContext());
        });
        return builder.setView(view).create();
    }
}
