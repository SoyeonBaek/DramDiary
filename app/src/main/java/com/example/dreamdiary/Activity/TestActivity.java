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
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dreamdiary.Data.Diary;
import com.example.dreamdiary.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    String date;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //이거
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        Gson gson = new Gson();
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);

        date = intent.getStringExtra("date");

        String strContact = sp.getString(date, "");

        Diary diary = gson.fromJson(strContact, Diary.class);
        ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        Bitmap image = diary.getBitmapFromString(diary.getCanvas());
        imageView.setImageBitmap(image);


        //todo : 키워드, 텍스트 화면에 띄우기
        ArrayList<String> keywords = diary.getKeywords();
        int size = keywords.size();
        String str = "";
        for (int i = 0; i < size; i++) {
            str += keywords.get(i);
        }
        TextView tv = (TextView)findViewById(R.id.kewordView);
        tv.setText(str);

        String text = diary.getContext();
        TextView tv2 = (TextView)findViewById(R.id.textView);
        tv2.setText(text);


    }

    //todo : 수정버튼
    public void modifyButtonClick(View v){
        Intent intent = new Intent(TestActivity.this, EmojiActivity.class);
        intent.putExtra("date", date);
        startActivity(intent);
    }
    //todo : 삭제버튼
    public void deleteButtonClick(View v){
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        Intent intent = new Intent(TestActivity.this, MainActivity.class);
        intent.putExtra("date", date);
        sp.edit().remove(date).commit();
        startActivity(intent);
    }


}
