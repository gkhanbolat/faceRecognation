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

public class soyUpdateActivity extends AppCompatActivity {
    EditText et_soyisim1,et_soyisim2;
    Button btn_SoyUpdate;
    String güncelSoyisim,soyisimKontrol;
    private HashMap<String,Object> mData;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soy_update);

        et_soyisim1=findViewById(R.id.et_soyisim1);
        et_soyisim2=findViewById(R.id.et_soyisim2);
        btn_SoyUpdate=findViewById(R.id.btn_SoyUpdate);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private void veriyiGuncelle(HashMap<String,Object> hashMap,final String uid){
        mFirestore.collection("Kullanıcılar").document(uid).update(hashMap).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(soyUpdateActivity.this, ProfilActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            }
        });
    }

    public void soyisimGuncelle(View v){
        güncelSoyisim=et_soyisim1.getText().toString();
        soyisimKontrol=et_soyisim2.getText().toString();

        if (!TextUtils.isEmpty(güncelSoyisim)){
            if (güncelSoyisim.equals(soyisimKontrol)){
                mData=new HashMap<>();
                mData.put("kullaniciSoyisim",güncelSoyisim);

                assert mUser!=null;
                veriyiGuncelle(mData,mUser.getUid());

            }
            else{
                Toast.makeText(this,"Girilen iki soy isim aynı olmak zorunda",Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this,"Girilen soy isim boş olamaz",Toast.LENGTH_SHORT).show();
        }
    }
}
