package com.taeian.facedetection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taeian.facedetection.Model.Kullanici;

public class KayitActivity extends AppCompatActivity {

    private ProgressDialog mProgress;

    private Kullanici mKullanici;

    private EditText kayiteditMail, kayiteditSifre ,editTelefon, kayiteditIsim, kayiteditSoyisim, kayiteditSifreTekrar ;
    private String txtMail, txtSifre, txtSoyisim, txtTelefon, txtIsim, txtSifreTekrar ;
    private TextInputLayout inputIsim , inputSoyisim, inputMail , inputSifre , inputTelefon, inputSifreTekrar;

    private LinearLayout mLinear;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FirebaseUser mUser;

    private void init(){
        mLinear=(LinearLayout)findViewById(R.id.kayit_ol);

        kayiteditMail = (EditText)findViewById(R.id.et_Mail);
        kayiteditSifre = (EditText)findViewById(R.id.et_sifre);
        kayiteditSifreTekrar=(EditText)findViewById(R.id.et_sifreTekrar);
        kayiteditSoyisim = (EditText)findViewById(R.id.et_Soyisim);
        kayiteditIsim = (EditText)findViewById(R.id.et_Name4);
        //editTelefon = (EditText)findViewById(R.id.et_Telefon);

        inputMail = (TextInputLayout)findViewById(R.id.kayit_inputMail);
        inputSifre = (TextInputLayout)findViewById(R.id.kayit_inputSifre);
        inputSoyisim = (TextInputLayout)findViewById(R.id.kayit_inputSoyisim);
        inputIsim = (TextInputLayout)findViewById(R.id.kayit_inputIsim);
        inputSifreTekrar = (TextInputLayout)findViewById(R.id.kayit_inputSifreTekrar);
        //inputTelefon = (TextInputLayout) findViewById(R.id.et_Telefon);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        init();
        mAuth=FirebaseAuth.getInstance();
        mFirestore= FirebaseFirestore.getInstance();

    }
    public void btn_KayitOl(View v){
        txtMail= kayiteditMail.getText().toString();
        txtSifre=kayiteditSifre.getText().toString();
        txtSoyisim=kayiteditSoyisim.getText().toString();
        txtIsim=kayiteditIsim.getText().toString();
        txtSifreTekrar=kayiteditSifreTekrar.getText().toString();


        //txtTelefon=editTelefon.getText().toString();

        if (!TextUtils.isEmpty(txtIsim)){
            if (!TextUtils.isEmpty(txtSoyisim)){
                if (!TextUtils.isEmpty(txtMail)){
                    if (!TextUtils.isEmpty(txtSifre)){
                        if (!TextUtils.isEmpty(txtSifreTekrar)){
                            if (txtSifre.equals(txtSifreTekrar)){
                                mProgress= new ProgressDialog(this);
                                mProgress.setTitle("Kayıt olunuyor...");
                                mProgress.show();

                                mAuth.createUserWithEmailAndPassword(txtMail,txtSifre)
                                        .addOnCompleteListener(KayitActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    mUser = mAuth.getCurrentUser();
                                                    if (mUser != null) {
                                                        mKullanici = new Kullanici(txtIsim, txtSoyisim, txtMail,txtSifre, mUser.getUid());
                                                        mFirestore.collection("Kullanıcılar").document(mUser.getUid())
                                                                .set(mKullanici)
                                                                .addOnCompleteListener(KayitActivity.this, new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            progressAyar();
                                                                            finish();
                                                                            startActivity(new Intent(KayitActivity.this, kullaniciGirisActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                                        } else {
                                                                            progressAyar();
                                                                            Snackbar.make(mLinear, task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                } else{
                                                    progressAyar();
                                                    Snackbar.make(mLinear, task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }else
                                Toast.makeText(this,"Şifreler uyuşmuyor!", Toast.LENGTH_SHORT).show();
                        }else
                            inputSifreTekrar.setError("Lütfen şifrenizi tekrar giriniz!");
                    }else
                        inputSifre.setError("Şifre kısmı boş bırakılamaz!");
                }else
                    inputMail.setError("Mail kısmı boş bırakılamaz!");
            }else
                inputSoyisim.setError("Soyisim kısmı boş bırakılamaz!");
        }else
            inputIsim.setError("İsim kısmı boş bırakılamaz!");
    }

    private void progressAyar(){
        if (mProgress.isShowing())
            mProgress.dismiss();
    }
}