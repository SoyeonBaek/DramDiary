package com.example.dreamdiary.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dreamdiary.Data.Diary;
import com.example.dreamdiary.R;

public class TestActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //이거
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Diary diary = (Diary)intent.getParcelableExtra("diary");

        System.out.println("test");
        System.out.println(diary.getEmoji());

        //이거
        setContentView(R.layout.activity_test);
    }
}
