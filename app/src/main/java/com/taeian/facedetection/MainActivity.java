package com.taeian.facedetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {



    public Button btn_kullanici,btn_yonetici,btn_kayit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_kullanici=findViewById(R.id.btn_kullanici);

        btn_kayit=findViewById(R.id.btn_kayit);

        btn_kullanici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kullanici=new Intent(MainActivity.this,kullaniciGirisActivity.class);
                startActivity(kullanici);
            }
        });

        btn_kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kayit=new Intent(MainActivity.this,KayitActivity.class);
                startActivity(kayit);
            }
        });


    }
    }