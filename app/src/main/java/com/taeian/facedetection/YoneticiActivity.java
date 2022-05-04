package com.taeian.facedetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.taeian.facedetection.databinding.ActivityIhbarBinding;

import com.google.firebase.auth.FirebaseAuth;

import org.opencv.android.OpenCVLoader;

public class YoneticiActivity extends AppCompatActivity {

    Button btn_ihbarlar,btn_ihbar,btn_Suc,btn_profil2;
    private FirebaseAuth firebaseAuth;
    private ActivityIhbarBinding binding;

    static{
        if(OpenCVLoader.initDebug()){
            Log.d("YoneticiActivity: ","Opencv is loaded.12345");
        }
        else{
            Log.d("YoneticiActivity: ","Opencv failed to load.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonetici);

        btn_ihbarlar=findViewById(R.id.btn_Profil);
        btn_ihbar=findViewById(R.id.btn_passChange);
        btn_Suc=findViewById(R.id.btn_Suc);
        btn_profil2=findViewById(R.id.btn_profil2);



        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        btn_profil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YoneticiActivity.this,ProfilActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        btn_Suc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YoneticiActivity.this,CameraActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        btn_ihbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YoneticiActivity.this,IhbarActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
        btn_ihbarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YoneticiActivity.this,IhbarGoruntulemeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

    }
}