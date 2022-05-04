package com.taeian.facedetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class kullaniciGirisActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private LinearLayout mLinear;
    private TextInputLayout inputMail, inputSifre;
    private EditText etMail, etSifre;
    private String txtMail,txtSifre;

    private FirebaseFirestore mFirestore;
    private DocumentReference docRef;
    private FirebaseUser mUser;
    String adminss;

    private void init(){
        mLinear= (LinearLayout)findViewById(R.id.giris_yap_lineer);
        mAuth= FirebaseAuth.getInstance();

        inputMail=(TextInputLayout)findViewById(R.id.giris_inputMail);
        inputSifre=(TextInputLayout)findViewById(R.id.giris_inputSifre);

        etMail=(EditText)findViewById(R.id.giris_etMail);
        etSifre=(EditText)findViewById(R.id.giris_etSifre);
        mAuth= FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mUser=mAuth.getCurrentUser();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_giris);
        init();
    }

    public void girisbuton(View v) {
        txtMail = etMail.getText().toString();
        txtSifre = etSifre.getText().toString();

        if (!TextUtils.isEmpty(txtMail)) {
            if (!TextUtils.isEmpty((txtSifre))) {
                mAuth.signInWithEmailAndPassword(txtMail, txtSifre)
                        .addOnSuccessListener(kullaniciGirisActivity.this, new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {


                                    Toast.makeText(kullaniciGirisActivity.this, "Başarıyla giriş yaptınız", Toast.LENGTH_SHORT).show();
                                    chechIfAdmin(authResult.getUser().getUid());

                            }
                        });
            } else
                inputSifre.setError("Lütfen geçerli bir şifre giriniz.");
        } else
            inputMail.setError("Lütfen geçerli bir Email adresi giriniz.");
    }
    public void btn_GitKayitOl(View v){
        startActivity(new Intent(kullaniciGirisActivity.this, KayitActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }


    private void chechIfAdmin(String uid){
        docRef = mFirestore.collection("Kullanıcılar").document(uid);
        docRef.get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: "+documentSnapshot.getData());
                if (documentSnapshot.getString("admin")!=null){
                    startActivity(new Intent(getApplicationContext(),YoneticiActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(getApplicationContext(),UygulamaActivity.class));
                    finish();
                }

            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}