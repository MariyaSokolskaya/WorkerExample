package com.example.workerexample;

import android.graphics.Bitmap;

import com.google.gson.Gson;

public class ImageHelper {
    public static String imageToJson(Bitmap bitmap){
        Gson gson = new Gson();
        return gson.toJson(bitmap);
    }
    public static Bitmap jsonToImage(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Bitmap.class);
    }
}
