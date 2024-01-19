package com.example.workerexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PicturesLoader extends Worker {

    Bitmap image;
    URL url;
    Data outputData;
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
            String jsonImage = ImageHelper.imageToJson(image);
            outputData = new Data.Builder().putString("image", jsonImage).build();
        } catch (MalformedURLException e) {
            Toast.makeText(getApplicationContext(), "Адрес неправильный", Toast.LENGTH_LONG)
                    .show();
        } catch (IOException e) {
            outputData = new Data.Builder().putString("ioexception", "Ошибка соединения").build();
            return Result.failure(outputData);
        }
        return Result.success(outputData);
    }
}
