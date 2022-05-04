package com.taeian.facedetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class isimUpdateActivity extends AppCompatActivity {
    EditText et_isim1,et_isim2;
    Button btn_Update;
    String güncelisim,isimKontrol;
    private HashMap<String,Object> mData;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isim_update);
        et_isim1=findViewById(R.id.et_isim1);
        et_isim2=findViewById(R.id.et_isim2);
        btn_Update=findViewById(R.id.btn_Update);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private void veriyiGuncelle(HashMap<String,Object> hashMap,final String uid){
        mFirestore.collection("Kullanıcılar").document(uid).update(hashMap).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(isimUpdateActivity.this, ProfilActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            }
        });
    }

    public void isimGuncelle(View v){
        güncelisim=et_isim1.getText().toString();
        isimKontrol=et_isim2.getText().toString();

        if (!TextUtils.isEmpty(güncelisim)){
            if (güncelisim.equals(isimKontrol)){
                mData=new HashMap<>();
                mData.put("kullaniciIsmi",güncelisim);

                assert mUser!=null;
                veriyiGuncelle(mData,mUser.getUid());

            }
            else{
                Toast.makeText(this,"Girilen iki isim aynı olmak zorunda",Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this,"Girilen değer boş olamaz",Toast.LENGTH_SHORT).show();
        }
    }
}