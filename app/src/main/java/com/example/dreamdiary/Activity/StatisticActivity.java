package com.example.dreamdiary.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dreamdiary.Data.Diary;
import com.example.dreamdiary.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatisticActivity extends AppCompatActivity {

    private BarChart barChart;
    Gson gson = new Gson();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        barChart = (BarChart)findViewById(R.id.chart);

        List<BarEntry> entries = new ArrayList<>();

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);

        Map<String, ?> allEntries = sp.getAll();
        Comparator<Integer> comparator = (s1, s2)->s2.compareTo(s1);
        Map<Integer, Integer> cnt = new TreeMap<Integer, Integer>(comparator);

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Diary diary = gson.fromJson(entry.getValue().toString(), Diary.class);
            int emoji = diary.getEmoji();
            if(cnt.containsKey(emoji)) {
                cnt.put(emoji, cnt.get(emoji) + 1);
            } else {
                cnt.put(emoji, 1);
            }
        }

        int index = 0;
        for (Map.Entry<Integer, Integer> entry : cnt.entrySet()) {
            entries.add(new BarEntry(entry.getKey(), entry.getValue()));
            index++;
            if (index == 5) break;
        }

        BarDataSet barDataSet = new BarDataSet(entries, "emoji");

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

    }
}
