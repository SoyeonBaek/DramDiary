package com.example.dreamdiary.Data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Diary implements Parcelable {



    //Field
    String canvas; // 사용할 때 getBitmapFromString() 이용하기. -> return Bitmap
    int emoji;
    String context;
    ArrayList<String> keywords;






    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }




    public void setCanvas(String canvas) {
        this.canvas = canvas;
    }

    public String getCanvas() {
        return canvas;
    }



    public Diary() {
        this.keywords = new ArrayList<>();
    }

    protected Diary(Parcel in) {
        emoji = in.readInt();
        keywords= in.readArrayList(String.class.getClassLoader());
        canvas= in.readString();

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
        parcel.writeString(this.canvas);
    }

    public int getEmoji() {
        return this.emoji;
    }
    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public String getStringFromBitmap(Bitmap bitmapPicture) {
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }
}
