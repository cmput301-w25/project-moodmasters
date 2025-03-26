package com.example.moodmasters.Views;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.MoodHistoryScreenShowMapEvent;
import com.example.moodmasters.Events.ShowProfileStatisticsEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class ProfileStatisticsActivity extends AppCompatActivity implements MVCView {
    private List<MoodEvent> mood_list;
    private String username;

    public ProfileStatisticsActivity() {
        super();
        mood_list = ShowProfileStatisticsEvent.getMoodEvents();
        username = ShowProfileStatisticsEvent.getUsername();
    }

    @Override
    public void update(MVCModel model) {

    }

    @Override
    public void initialize(MVCModel model) {

    }

    public void showMoodStatistics() {
        final long week_millis = 604800000L;
        ArrayList<PieEntry> pie_values = new ArrayList<>();
        ArrayList<BarEntry> bar_values = new ArrayList<>();

        long epoch_time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(epoch_time);

        Calendar mood_calendar = Calendar.getInstance();

        for (int i = 0; i < mood_list.size(); i++) {
            MoodEvent mood_event = mood_list.get(i);
            long mood_epoch = mood_event.getEpochTime();
            mood_calendar.setTimeInMillis(mood_epoch);

            pie_values.add(new PieEntry(1, mood_event.getMood()));

            if (epoch_time - mood_epoch < week_millis) {
                bar_values.add(new BarEntry(mood_calendar.get(7), 1));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_statistics_screen);

        TextView username_label = findViewById(R.id.profile_statistics_username_label);
        TextView display_label = findViewById(R.id.profile_statistics_display_label);
        Spinner selection_spinner = findViewById(R.id.profile_statistics_selection_spinner);
        PieChart pie_chart = findViewById(R.id.profile_statistics_pie_chart);
        BarChart bar_chart = findViewById(R.id.profile_statistics_bar_chart);
        Button back_button = findViewById(R.id.profile_statistics_back_button);

        username_label.setText(username + "'s Statistics");

    }
}
