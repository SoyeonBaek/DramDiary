package com.example.dreamdiary.Activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.dreamdiary.Data.Diary;
import com.example.dreamdiary.R;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarUtils;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.lang.reflect.GenericDeclaration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Diary diary;
    Gson gson = new Gson();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String date = now.format(formatter);
        String strContact = sp.getString(date, "");
        if(strContact.isEmpty()) {
            Intent intent = new Intent(this, EmojiActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);

        //Intent intent = getIntent();

        //일기 가져오기
        strContact = sp.getString("2021/12/10", "");
        if(!strContact.isEmpty()){
            diary = gson.fromJson(strContact, Diary.class);
            System.out.println(diary.getEmoji());
            for(int i = 0; i< diary.getKeywords().size(); i++){
                System.out.println(diary.getKeywords().get(i));
            }
        }

        //TestCode
        System.out.println("emoji"+diary.getEmoji());
        System.out.println("keyword"+diary.getKeywords());
        System.out.println("canvas"+diary.getCanvas());
        MaterialCalendarView calendar = (MaterialCalendarView) findViewById(R.id.calendarView);
        calendar.addDecorator(new myDeco(this));
    }
}

class myDeco implements DayViewDecorator{

    Drawable d;
    Bitmap drawable;

    myDeco(Activity context){
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        //CalendarDay date = new CalendarDay(new Date(2021, 12,10));
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {

    }
}



