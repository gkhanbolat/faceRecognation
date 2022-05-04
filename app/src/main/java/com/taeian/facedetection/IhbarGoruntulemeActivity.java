package com.taeian.facedetection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Map;

import com.taeian.facedetection.Adapter.IhbarAdapter;
import com.taeian.facedetection.Model.Ihbarlar;
import com.taeian.facedetection.databinding.ActivityIhbarBinding;


public class IhbarGoruntulemeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Ihbarlar> ihbarlarArrayList;
    IhbarAdapter ihbarAdapter;
    private ActivityIhbarBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIhbarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ihbarlarArrayList=new ArrayList<Ihbarlar>();

        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


        getData();


        //internetten buldum
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //binding.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ihbarAdapter=new IhbarAdapter(ihbarlarArrayList);
        recyclerView.setAdapter(ihbarAdapter);

    }
    private void getData(){

        CollectionReference collectionReference = firebaseFirestore.collection("GelenIhbar");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null){
                    Toast.makeText(IhbarGoruntulemeActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
                if (value!=null){
                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String , Object> data = snapshot.getData();

                        //Casting
                        String useremail=(String) data.get("useremail");
                        String aciklama=(String) data.get("aciklama");
                        String downloadUrl=(String) data.get("downloadUrl");

                        Ihbarlar ihbarlar = new Ihbarlar(useremail,aciklama,downloadUrl);
                        ihbarlarArrayList.add(ihbarlar);

                    }
                    ihbarAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}