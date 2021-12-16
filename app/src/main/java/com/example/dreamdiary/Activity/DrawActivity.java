package com.example.dreamdiary.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import com.example.dreamdiary.Data.Diary;
import com.example.dreamdiary.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DrawActivity extends AppCompatActivity {

    class Point{
        float x;
        float y;
        boolean check;
        int color;

        public Point(float x, float y, boolean check,int color)
        {
            this.x = x;
            this.y = y;
            this.check = check;
            this.color = color;
        }
    }

    class MyView extends View
    {
        public MyView(Context context) { super(context); }

        @Override
        protected void onDraw(Canvas canvas) {
            Paint p = new Paint();
            p.setStrokeWidth(15);
            for(int i=1 ; i<points.size() ; i++)
            {
                p.setColor(points.get(i).color);
                if(!points.get(i).check)
                    continue;
                canvas.drawLine(points.get(i-1).x,points.get(i-1).y,points.get(i).x,points.get(i).y,p);
            }
        }
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    points.add(new Point(x,y,false , color));
                case MotionEvent.ACTION_MOVE :
                    points.add(new Point(x,y,true , color));
                    break;
                case MotionEvent.ACTION_UP :
                    break;
            }
            invalidate();
            return true;
        }
    }

    Diary diary;

    ArrayList<Point> points = new ArrayList<Point>();
    Button draw_red_btn,draw_blue_btn,draw_black_btn,clearbtn;
    LinearLayout drawlinear;
    int color = Color.BLACK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        Intent intent = getIntent();
        diary = (Diary)intent.getParcelableExtra("diary");

        final MyView m = new MyView(this);

        findViewById(R.id.Red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.RED ;
            }
        });
        findViewById(R.id.Blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.BLUE ;
            }
        });
        findViewById(R.id.Black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.BLACK ;
            }
        });

        clearbtn = findViewById(R.id.Clear);
        drawlinear = findViewById(R.id.draw_linear);
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                points.clear();
                m.invalidate();
            }
        });
        drawlinear.addView(m);

        Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                EditText editText = (EditText)findViewById(R.id.context);
                diary.setContext(editText.getText().toString());

                Gson gson = new GsonBuilder().create();
                Bitmap b = getBitmapFromView(m);
                b = compressBitmap(b);
                diary.setCanvas(b);

                String strContact = gson.toJson(diary);
                LocalDate now = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                String date = now.format(formatter);
                SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(date, strContact);
                editor.commit();

                Intent intent = new Intent(DrawActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
    private Bitmap compressBitmap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40, stream);
        byte[] byteArray = stream.toByteArray();
        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        return compressedBitmap;
    }

}
