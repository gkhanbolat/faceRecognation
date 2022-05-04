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

public class mailUpdateActivity extends AppCompatActivity {
    EditText et_mail1,et_mail2;
    Button btn_mailUpdate;
    String güncelMail,mailKontrol;
    private HashMap<String,Object> mData;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_update);
        et_mail1=findViewById(R.id.et_mail1);
        et_mail2=findViewById(R.id.et_mail2);
        btn_mailUpdate=findViewById(R.id.btn_mailUpdate);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private void veriyiGuncelle(HashMap<String,Object> hashMap,final String uid){
        mFirestore.collection("Kullanıcılar").document(uid).update(hashMap).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(mailUpdateActivity.this, ProfilActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            }
        });
    }

    public void mailGuncelle(View v){
        güncelMail=et_mail1.getText().toString();
        mailKontrol=et_mail2.getText().toString();

        if (!TextUtils.isEmpty(güncelMail)){
            if (güncelMail.equals(mailKontrol)){
                mData=new HashMap<>();
                mData.put("kullaniciMail",güncelMail);

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