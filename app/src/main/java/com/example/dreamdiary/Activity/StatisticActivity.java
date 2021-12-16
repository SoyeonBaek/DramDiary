package com.example.dreamdiary.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dreamdiary.Data.Diary;
import com.example.dreamdiary.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Diary diary = gson.fromJson(entry.getValue().toString(), Diary.class);
            diary.getEmoji();
            System.out.println(diary.getEmoji());
        }

        BarDataSet barDataSet = new BarDataSet(entries, "emoji");

    }
}
