package com.example.dreamdiary.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dreamdiary.Data.Diary;
import com.example.dreamdiary.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class KeywordActivity extends AppCompatActivity {


    String[] keywords = {"행복한 꿈", "즐거운 꿈", "슬픈 꿈", "무서운 꿈", "신기한 꿈", "추락하는 꿈", "졸림"};
    Diary diary;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword);

        Intent intent = getIntent();
        diary = (Diary)intent.getParcelableExtra("diary");



        LinearLayout checkboxs = (LinearLayout) findViewById(R.id.checkboxList);
        for(int i = 0; i< keywords.length; i++){
            CheckBox check = new CheckBox(this);
            check.setText(keywords[i]);
            check.setId(i);
            checkboxs.addView(check);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void buttonClick(View view){

        ArrayList<String> keywordList = new ArrayList<>();
        for(int i = 0; i< keywords.length; i++){
            CheckBox checkBox = (CheckBox) findViewById(i);
            if(checkBox.isChecked()){
                keywordList.add((String) checkBox.getText());
            }
        }
        diary.setKeywords(keywordList);

        /*
        Gson gson = new GsonBuilder().create();
        String strContact = gson.toJson(diary);
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String date = now.format(formatter);
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(date, strContact);
        editor.commit();
        */


        Intent intent = new Intent(KeywordActivity.this, DrawActivity.class);
        if(diary != null){
            intent.putExtra("diary", diary);
        }
        startActivity(intent);
    }
}