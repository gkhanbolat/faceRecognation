package com.taeian.facedetection;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.taeian.facedetection.Model.Kullanici;

import java.util.HashMap;
import java.util.Map;

public class ProfilActivity extends AppCompatActivity {


    private TextView tv_Name,tv_Soyisim,tv_Mail,textView2,textView4,textView6;

    private Button btn_nameUpd,btn_lastNameUpd,btn_MailUpd;



    private FirebaseFirestore mFirestore;
    private DocumentReference docRef;
    private HashMap<String,Object> mData;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        tv_Name=findViewById(R.id.tv_Name);
        tv_Mail=findViewById(R.id.tv_Mail);
        tv_Soyisim=findViewById(R.id.tv_Soyisim);
        textView2=findViewById(R.id.textView2);
        textView4=findViewById(R.id.textView4);
        textView6=findViewById(R.id.textView6);
        btn_nameUpd=findViewById(R.id.btn_nameUpd);
        btn_lastNameUpd=findViewById(R.id.btn_lastNameUpd);
        btn_MailUpd=findViewById(R.id.btn_MailUpd);




        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();
        verileriGetir(mUser.getUid());

        btn_nameUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update=new Intent(ProfilActivity.this,isimUpdateActivity.class);
                startActivity(update);
            }
        });

        btn_lastNameUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update=new Intent(ProfilActivity.this,soyUpdateActivity.class);
                startActivity(update);
            }
        });

        btn_MailUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update=new Intent(ProfilActivity.this,mailUpdateActivity.class);
                startActivity(update);
            }
        });


    }

    private void verileriGetir(String uid){
        docRef = mFirestore.collection("Kullanıcılar").document(uid);
        docRef.get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    mData=(HashMap)documentSnapshot.getData();
                    for (Map.Entry data: mData.entrySet()){
                        System.out.println(data.getKey()+"="+data.getValue());
                        if (data.getKey().equals("kullaniciIsmi")){
                            textView2.setText(data.getValue().toString());
                        }
                        else if (data.getKey().equals("kullaniciSoyisim")){
                            textView4.setText(data.getValue().toString());
                        }
                        else if (data.getKey().equals("kullaniciMail")){
                            textView6.setText(data.getValue().toString());
                        }

                    }
                }

            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


}