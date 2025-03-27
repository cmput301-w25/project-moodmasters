package com.example.moodmasters.Views;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.ProfileStatisticsBackEvent;
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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ProfileStatisticsActivity extends AppCompatActivity implements MVCView {
    private List<MoodEvent> mood_list;
    private List<Mood> moods;
    private String username;
    private PieChart pie_chart;
    private BarChart bar_chart;
    final long day_millis = 86400000L;
    final long week_millis = 604800000L;

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

    public HashMap<Integer, Integer> getDayCounts(Calendar calendar, long epoch_time) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        int this_month = calendar.get(Calendar.MONTH);
        int this_year = calendar.get(Calendar.YEAR);

        for (int i = 0; calendar.get(Calendar.MONTH) == this_month; i++) {
            calendar.add(Calendar.DATE, -i);
            counts.put(calendar.get(Calendar.DATE), 0);
        }

        for (int i = 0; i < mood_list.size(); i++) {
            MoodEvent mood_event = mood_list.get(i);
            Calendar mood_calendar = Calendar.getInstance();
            long mood_epoch = mood_event.getEpochTime();
            mood_calendar.setTimeInMillis(mood_epoch);
            if (mood_calendar.get(Calendar.MONTH) == this_month &&
                    mood_calendar.get(Calendar.YEAR) == this_year) {
                counts.putIfAbsent(mood_calendar.get(Calendar.DATE), 0);
                counts.put(mood_calendar.get(Calendar.DATE), counts.get(mood_calendar.get(Calendar.DATE)) + 1);
            }
        }

        return counts;
    }

    public void showMoodStatistics() {
        ArrayList<PieEntry> pie_values = new ArrayList<>();
        ArrayList<BarEntry> bar_values = new ArrayList<>();

        long epoch_time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(epoch_time);

        Calendar mood_calendar = Calendar.getInstance();


//        for (int i = 0; i < mood_list.size(); i++) {
//            MoodEvent mood_event = mood_list.get(i);
//            long mood_epoch = mood_event.getEpochTime();
//            mood_calendar.setTimeInMillis(mood_epoch);
//            if (epoch_time - mood_epoch < week_millis) {
//                bar_values.add(new BarEntry(mood_calendar.get(7), 1));
//            }
//        }

        HashMap<Emotion.State, Integer> mood_counts = getEmotionCounts();
        HashMap<Integer, Integer> day_counts = getDayCounts(calendar, epoch_time);

        for (int i = 0; i < moods.size(); i++) {
            pie_values.add(new PieEntry(mood_counts.get(moods.get(i).getEmotion()), moods.get(i).getEmotionString()));
        }

        Set<Integer> key_set = day_counts.keySet();
        Integer[] keys = new Integer[7];
        key_set.toArray(keys);

        for (int i = 0; i < 7; i++) {
            bar_values.add(new BarEntry(keys[i], day_counts.get(keys[i])));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < moods.size(); i++) {
            colors.add(getResources().getColor(moods.get(i).getColor(), getTheme()));
        }

        PieDataSet pie_set = new PieDataSet(pie_values, "");
        pie_set.setColors(colors);
        PieData pie_data = new PieData(pie_set);
        pie_data.setDrawValues(false);
        pie_chart.setData(pie_data);


        BarDataSet bar_set = new BarDataSet(bar_values, "");
        //bar_set.setColors(colors);
        //bar_set.calcMinMax();
        BarData bar_data = new BarData(bar_set);
        bar_data.setValueTextSize(0);
        bar_chart.setData(bar_data);
        bar_set.setFormSize(100);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_statistics_screen);

        TextView username_label = findViewById(R.id.profile_statistics_username_label);
        TextView display_label = findViewById(R.id.profile_statistics_display_label);

        ArrayList<String> selections = new ArrayList<>();
        selections.add("Mood");
        selections.add("Social Situation");
        selections.add("Time of Day");

        Spinner selection_spinner = findViewById(R.id.profile_statistics_selection_spinner);
        ArrayAdapter<String> emotions_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, selections);
        selection_spinner.setAdapter(emotions_adapter);

        pie_chart = findViewById(R.id.profile_statistics_pie_chart);
        pie_chart.getDescription().setEnabled(false);
        pie_chart.setUsePercentValues(true);
        pie_chart.getLegend().setEnabled(false);
        pie_chart.setDrawEntryLabels(true);
        pie_chart.setEntryLabelTextSize(20);
        pie_chart.setEntryLabelColor(R.color.text_color);
        //pie_chart.setFitsSystemWindows(false);
        pie_chart.setMinimumHeight(1000);

        bar_chart = findViewById(R.id.profile_statistics_bar_chart);
        bar_chart.getDescription().setEnabled(false);
        bar_chart.setPinchZoom(false);
        bar_chart.setDoubleTapToZoomEnabled(false);
        bar_chart.getLegend().setEnabled(false);
        //bar_chart.setFitsSystemWindows(false);
        bar_chart.setMinimumHeight(1000);

        Button back_button = findViewById(R.id.profile_statistics_back_button);
        back_button.setOnClickListener(v -> {
            controller.execute(new ProfileStatisticsBackEvent(), this);
        });

        username_label.setText(username + "'s Statistics");

        showMoodStatistics();

    }
}
