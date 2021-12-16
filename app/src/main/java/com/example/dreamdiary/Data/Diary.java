package com.example.dreamdiary.Data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.example.dreamdiary.Activity.DrawActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Diary implements Parcelable {

    //bitmap image
    Bitmap canvas;

    int emoji;
    ArrayList<String> keywords;

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    String context;


    public void setCanvas(Bitmap canvas) {
        this.canvas = canvas;
    }

    public Bitmap getCanvas() {
        return canvas;
    }



    public Diary() {
        this.keywords = new ArrayList<>();
    }

    protected Diary(Parcel in) {
        emoji = in.readInt();
        keywords= in.readArrayList(String.class.getClassLoader());
        canvas= (Bitmap) in.readValue(Bitmap.class.getClassLoader());

    }

    public void setEmoji(int emoji) {
        this.emoji = emoji;
    }
    public void setKeywords(ArrayList<String> keywords){
        this.keywords = keywords;
    }

    public static final Creator<Diary> CREATOR = new Creator<Diary>() {
        @Override
        public Diary createFromParcel(Parcel in) {
            return new Diary(in);
        }

        @Override
        public Diary[] newArray(int size) {
            return new Diary[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.emoji);
        parcel.writeArray(this.keywords.toArray());
        parcel.writeValue(this.canvas);
    }

    public int getEmoji() {
        return this.emoji;
    }
    public ArrayList<String> getKeywords() {
        return keywords;
    }
}
