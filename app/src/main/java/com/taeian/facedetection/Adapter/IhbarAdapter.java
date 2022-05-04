package com.taeian.facedetection.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.taeian.facedetection.Model.Ihbarlar;
import com.taeian.facedetection.databinding.RecyclerRowBinding;

import java.util.ArrayList;

public class IhbarAdapter extends RecyclerView.Adapter<IhbarAdapter.IhbarHolder> {

    private ArrayList<Ihbarlar> ihbarlarArrayList;

    public IhbarAdapter(ArrayList<Ihbarlar> ihbarlarArrayList) {
        this.ihbarlarArrayList = ihbarlarArrayList;
    }
    class IhbarHolder extends RecyclerView.ViewHolder{

        RecyclerRowBinding recyclerRowBinding;

        public IhbarHolder(@NonNull RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding=recyclerRowBinding;

        }
    }

    @NonNull
    @Override
    public IhbarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new IhbarHolder(recyclerRowBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull IhbarHolder holder, int position) {
        holder.recyclerRowBinding.recyclerViewUserEmailText.setText(ihbarlarArrayList.get(position).email);
        holder.recyclerRowBinding.recyclerViewAciklamaText.setText(ihbarlarArrayList.get(position).aciklama);
        Picasso.get().load(ihbarlarArrayList.get(position).downloadUrl).into(holder.recyclerRowBinding.recyclerViewImageView);
    }

    @Override
    public int getItemCount() {
        return ihbarlarArrayList.size();
    }

}
