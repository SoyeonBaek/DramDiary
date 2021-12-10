package com.example.dreamdiary.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Diary implements Parcelable {
    int emoji;
    ArrayList<String> keywords;


    public Diary() {
        this.keywords = new ArrayList<>();
    }

    protected Diary(Parcel in) {
        emoji = in.readInt();
        keywords= in.readArrayList(String.class.getClassLoader());
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

    }

    public int getEmoji() {
        return this.emoji;
    }
    public ArrayList<String> getKeywords() {
        return keywords;
    }
}
