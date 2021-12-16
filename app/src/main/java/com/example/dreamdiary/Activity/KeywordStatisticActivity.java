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
import com.github.mikephil.charting.utils.ColorTemplate;
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

public class KeywordStatisticActivity extends AppCompatActivity {

    private BarChart barChart;
    Gson gson = new Gson();

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        barChart = (BarChart)findViewById(R.id.chart);

        List<BarEntry> entries = new ArrayList<>();

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);

        Map<String, LinkedList<String>> allEntries = (Map<String, LinkedList<String>>) sp.getAll();
        Map<String, Integer> cnt = new HashMap<String, Integer>();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Diary diary = gson.fromJson(entry.getValue().toString(), Diary.class);
            ArrayList<String> keywords = diary.getKeywords();
            for (String keyword : keywords) {
                if (cnt.containsKey(keyword)) {
                    cnt.put(keyword, cnt.get(keyword) + 1);
                } else {
                    cnt.put(keyword, 1);
                }
            }
        }

        List<String> keySetList = new ArrayList<String>(cnt.keySet());
        Collections.sort(keySetList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return cnt.get(o2).compareTo(cnt.get(o1));
            }
        });

        ArrayList<String> labels = new ArrayList<String>();
        int index = 0;
        for (Map.Entry<String, Integer> entry : cnt.entrySet()) {
            labels.add(entry.getKey());
            entries.add(new BarEntry(entry.getValue(), index));
            index++;
            if (index == 5) break;
        }
        BarDataSet barDataSet = new BarDataSet(entries, "emoji");
        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        BarData barData = new BarData(labels, barDataSet);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.setData(barData);

    }
}