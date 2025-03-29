package com.example.moodmasters.Views;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ProfileStatisticsActivity extends AppCompatActivity implements MVCView {
    private List<MoodEvent> mood_list;
    private List<Mood> moods;
    private String username;
    private PieChart pie_chart;
    private BarChart bar_chart;
    TextView display_label;
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

    public HashMap<String, Integer> getEmotionCounts() {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();

        for (int i = 0; i < mood_list.size(); i++) {
            MoodEvent mood_event = mood_list.get(i);
            counts.putIfAbsent(mood_event.getMood().getEmotionString(), 0);
            counts.put(mood_event.getMood().getEmotionString(), counts.get(mood_event.getMood().getEmotionString()) + 1);
        }

        return counts;
    }

    public HashMap<String, Integer> getSocialCounts() {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();

        for (int i = 0; i < mood_list.size(); i++) {
            MoodEvent mood_event = mood_list.get(i);
            counts.putIfAbsent(SocialSituation.getString(mood_event.getSituation()), 0);
            counts.put(SocialSituation.getString(mood_event.getSituation()), counts.get(SocialSituation.getString(mood_event.getSituation())) + 1);
        }

        return counts;
    }

    public HashMap<String, Integer> getTimeCounts() {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();

        for (int i = 0; i < mood_list.size(); i++) {
            MoodEvent mood_event = mood_list.get(i);
            String time_string = mood_event.getDatetime().split(" ")[4];
            String time_of_day;
            if (time_string.compareTo("05:00") >= 0 && time_string.compareTo("12:00") < 0) {
                time_of_day = "Morning";
            } else if (time_string.compareTo("17:00") < 0) {
                time_of_day = "Afternoon";
            } else if (time_string.compareTo("22:00") < 0) {
                time_of_day = "Evening";
            } else {
                time_of_day = "Night";
            }
            counts.putIfAbsent(time_of_day, 0);
            counts.put(time_of_day, counts.get(time_of_day) + 1);
        }

        return counts;
    }

    public HashMap<Integer, Integer> getDayCounts(Calendar calendar, long epoch_time) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        int this_month = calendar.get(Calendar.MONTH);
        int this_year = calendar.get(Calendar.YEAR);

        calendar.clear();
        calendar.set(this_year, this_month, 1);
        counts.put(calendar.get(Calendar.DATE), 0);

        while (calendar.get(Calendar.MONTH) == this_month) {
            calendar.add(Calendar.DATE, 1);
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

    public String getTop(HashMap<String, Integer> counts, String[] keys) {
        String top = keys[0];
        int count = 0;

        for (String mood : keys) {
            if (counts.get(mood) > count) {
                top = mood;
                count = counts.get(mood);
            }
        }

        return top;
    }

    public Integer getTopInt(HashMap<Integer, Integer> counts, Integer[] keys) {
        int count = 0;

        for (Integer mood : keys) {
            if (counts.get(mood) > count) {
                count = counts.get(mood);
            }
        }

        return count;
    }

    public void showBarGraph() {
        ArrayList<BarEntry> bar_values = new ArrayList<>();

        long epoch_time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(epoch_time);

        HashMap<Integer, Integer> day_counts = getDayCounts(calendar, epoch_time);

        Set<Integer> day_set = day_counts.keySet();
        Integer[] day_keys = new Integer[day_set.size()];
        day_set.toArray(day_keys);

        for (int i = 0; i < day_set.size(); i++) {
            bar_values.add(new BarEntry(day_keys[i], day_counts.get(day_keys[i])));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.button_color, getTheme()));

        BarDataSet bar_set = new BarDataSet(bar_values, "");
        bar_set.setColors(colors);
        BarData bar_data = new BarData(bar_set);
        bar_data.setValueTextSize(0);
        bar_chart.setData(bar_data);
        bar_chart.getAxisLeft().setLabelCount(getTopInt(day_counts, day_keys));
        bar_set.setFormSize(100);
    }

    public void showMoodStatistics() {
        ArrayList<PieEntry> pie_values = new ArrayList<>();
        HashMap<String, Integer> mood_counts = getEmotionCounts();

        Set<String> mood_set = mood_counts.keySet();
        String[] mood_keys = new String[mood_set.size()];
        mood_set.toArray(mood_keys);

        for (int i = 0; i < mood_set.size(); i++) {
            pie_values.add(new PieEntry(mood_counts.get(mood_keys[i]), mood_keys[i]));
        }

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.LIBERTY_COLORS) {
            colors.add(c);
        }

        String display_string = username + "'s top mood is " + getTop(mood_counts, mood_keys);

        display_label.setText(display_string);

        PieDataSet pie_set = new PieDataSet(pie_values, "");
        pie_set.setColors(colors);
        PieData pie_data = new PieData(pie_set);
        pie_data.setDrawValues(false);
        pie_chart.setData(pie_data);
    }

    public void showSocialStatistics() {
        ArrayList<PieEntry> pie_values = new ArrayList<>();
        HashMap<String, Integer> social_counts = getSocialCounts();

        Set<String> social_set = social_counts.keySet();
        String[] social_keys = new String[social_set.size()];
        social_set.toArray(social_keys);

        for (int i = 0; i < social_set.size(); i++) {
            pie_values.add(new PieEntry(social_counts.get(social_keys[i]), social_keys[i]));
        }

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.LIBERTY_COLORS) {
            colors.add(c);
        }

        String display_string = username + "'s top social situation is " + getTop(social_counts, social_keys);

        display_label.setText(display_string);

        PieDataSet pie_set = new PieDataSet(pie_values, "");
        pie_set.setColors(colors);
        PieData pie_data = new PieData(pie_set);
        pie_data.setDrawValues(false);
        pie_chart.setData(pie_data);
    }

    public void showTimeStatistics() {
        ArrayList<PieEntry> pie_values = new ArrayList<>();
        HashMap<String, Integer> time_counts = getTimeCounts();

        Set<String> time_set = time_counts.keySet();
        String[] time_keys = new String[time_set.size()];
        time_set.toArray(time_keys);

        for (int i = 0; i < time_set.size(); i++) {
            pie_values.add(new PieEntry(time_counts.get(time_keys[i]), time_keys[i]));
        }

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.LIBERTY_COLORS) {
            colors.add(c);
        }

        String display_string = username + "'s top time of day is " + getTop(time_counts, time_keys);

        display_label.setText(display_string);

        PieDataSet pie_set = new PieDataSet(pie_values, "");
        pie_set.setColors(colors);
        PieData pie_data = new PieData(pie_set);
        pie_data.setDrawValues(false);
        pie_chart.setData(pie_data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_statistics_screen);

        TextView username_label = findViewById(R.id.profile_statistics_username_label);
        display_label = findViewById(R.id.profile_statistics_display_label);

        ArrayList<String> selections = new ArrayList<>();
        selections.add("Mood");
        selections.add("Social Situation");
        selections.add("Time of Day");

        Spinner selection_spinner = findViewById(R.id.profile_statistics_selection_spinner);
        ArrayAdapter<String> selection_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, selections);
        selection_spinner.setAdapter(selection_adapter);
        selection_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Objects.equals(selection_adapter.getItem(position), "Mood")) {
                    showMoodStatistics();
                } else if (Objects.equals(selection_adapter.getItem(position), "Social Situation")) {
                    showSocialStatistics();
                } else if (Objects.equals(selection_adapter.getItem(position), "Time of Day")) {
                    showTimeStatistics();
                }
                pie_chart.notifyDataSetChanged();
                pie_chart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pie_chart = findViewById(R.id.profile_statistics_pie_chart);
        pie_chart.getDescription().setEnabled(false);
        pie_chart.setUsePercentValues(true);
        pie_chart.getLegend().setEnabled(false);
        pie_chart.setDrawEntryLabels(true);
        pie_chart.setEntryLabelTextSize(20);
        pie_chart.setEntryLabelColor(R.color.text_color);
        pie_chart.setMinimumHeight(1000);

        bar_chart = findViewById(R.id.profile_statistics_bar_chart);
        bar_chart.getDescription().setEnabled(false);
        bar_chart.setPinchZoom(false);
        bar_chart.setDoubleTapToZoomEnabled(false);
        bar_chart.getLegend().setEnabled(false);
        bar_chart.setMinimumHeight(900);
        bar_chart.getAxisLeft().setTextSize(20);
        bar_chart.getAxisLeft().setDrawZeroLine(true);
        bar_chart.getXAxis().setTextSize(20);
        bar_chart.getXAxis().setDrawGridLines(false);
        bar_chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        bar_chart.getAxisRight().setEnabled(false);

        Button back_button = findViewById(R.id.profile_statistics_back_button);
        back_button.setOnClickListener(v -> {
            controller.execute(new ProfileStatisticsBackEvent(), this);
        });

        username_label.setText(username + "'s Statistics");

        showBarGraph();
        showMoodStatistics();

    }
}
