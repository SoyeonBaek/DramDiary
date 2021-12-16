package com.example.dreamdiary.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dreamdiary.Data.Diary;
import com.example.dreamdiary.R;
import com.google.gson.Gson;

public class TestActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //이거
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        Gson gson = new Gson();
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        String date = intent.getStringExtra("date");
        String strContact = sp.getString(date, "");
        Diary diary = gson.fromJson(strContact, Diary.class);
        ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        Bitmap image = diary.getBitmapFromString(diary.getCanvas());
        imageView.setImageBitmap(image);

        //todo : 삭제버튼, 수정버튼
        //todo : 키워드, 텍스트 화면에 띄우기.



    }

}
