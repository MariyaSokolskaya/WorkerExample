package com.example.workerexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PicturesLoader extends Worker {

    Bitmap image;
    URL url;
    public PicturesLoader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            //извлечение строки с данными потока и преобразование строки в правильный адрес
            url = new URL(getInputData().getString("address"));
            //открытие потока и скачивание картинки
            image = BitmapFactory.decodeStream(url.openStream());

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
