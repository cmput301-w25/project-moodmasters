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
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class ProfileStatisticsActivity extends AppCompatActivity implements MVCView {
    private List<MoodEvent> mood_list;
    private List<Mood> moods;
    private String username;
    private PieChart pie_chart;
    private BarChart bar_chart;

    public ProfileStatisticsActivity() {
        super();
        mood_list = ShowProfileStatisticsEvent.getMoodEvents();
        username = ShowProfileStatisticsEvent.getUsername();
        controller.addBackendView(this);
    }

    @Override
    public void update(MVCModel model) {

    }

    @Override
    public void initialize(MVCModel model) {
        moods = model.getBackendList(BackendObject.State.MOODLIST);
    }

    public HashMap<Emotion.State, Integer> getEmotionCounts() {
        HashMap<Emotion.State, Integer> counts = new HashMap<Emotion.State, Integer>();
        for (int i = 0; i < moods.size(); i++) {
            counts.put(moods.get(i).getEmotion(), 0);
        }

        for (int i = 0; i < mood_list.size(); i++) {
            MoodEvent mood_event = mood_list.get(i);
            counts.put(mood_event.getMood().getEmotion(), counts.get(mood_event.getMood().getEmotion()) + 1);
        }

        return counts;
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
            if (epoch_time - mood_epoch < week_millis) {
                bar_values.add(new BarEntry(mood_calendar.get(7), 1));
            }
        }

        HashMap<Emotion.State, Integer> mood_counts = getEmotionCounts();

        for (int i = 0; i < moods.size(); i++) {
            pie_values.add(new PieEntry(mood_counts.get(moods.get(i).getEmotion()), moods.get(i).getEmotion()));
        }

        PieDataSet pie_set = new PieDataSet(pie_values, "Total Moods");
        PieData pie_data = new PieData(pie_set);
        pie_chart.setData(pie_data);

        BarDataSet bar_set = new BarDataSet(bar_values, "Last 7 Days");
        BarData bar_data = new BarData(bar_set);
        bar_chart.setData(bar_data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_statistics_screen);

        TextView username_label = findViewById(R.id.profile_statistics_username_label);
        TextView display_label = findViewById(R.id.profile_statistics_display_label);
        Spinner selection_spinner = findViewById(R.id.profile_statistics_selection_spinner);
        pie_chart = findViewById(R.id.profile_statistics_pie_chart);
        bar_chart = findViewById(R.id.profile_statistics_bar_chart);
        Button back_button = findViewById(R.id.profile_statistics_back_button);

        username_label.setText(username + "'s Statistics");

        showMoodStatistics();

    }
}
