package com.example.workerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String[] imageAddresses= {"https://www.fonstola.ru/pic/202003/1920x1080/fonstola.ru_376107.jpg",
            "https://i.ytimg.com/vi/4PUhM01PZf8/maxresdefault.jpg",
            "https://i.etsystatic.com/24419106/r/il/e571b0/2440722344/il_1588xN.2440722344_gi2c.jpg"};

    TableRow imagesRow;
    AppCompatButton loadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagesRow = findViewById(R.id.images_row);
        loadButton = findViewById(R.id.load_button);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //данные для потока
                Data data = new Data.Builder()
                        .putString("address", imageAddresses[0])
                        .build();
                //задача потока
                OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(PicturesLoader.class)
                        .setInputData(data)
                        .build();
                //менеджер потока
                WorkManager workManager = WorkManager.getInstance();
                workManager.enqueue(workRequest);
                workManager.getWorkInfoByIdLiveData(workRequest.getId())
                        .observe(MainActivity.this, new Observer<WorkInfo>() {
                            @Override
                            public void onChanged(WorkInfo workInfo) {
                                String imageJson = workInfo.getOutputData().getString("image");
                                if(imageJson != null) {
                                    Bitmap image = ImageHelper.jsonToImage(imageJson);
                                    ((ImageView) imagesRow.getChildAt(0)).setImageBitmap(image);
                                }else{
                                    String error = workInfo.getOutputData().getString("ioexception");
                                    Toast.makeText(getApplicationContext(), "Ошибка " + error,
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                        });
            }
        });
    }
}