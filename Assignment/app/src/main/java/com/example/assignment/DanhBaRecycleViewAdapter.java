package com.example.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DanhBaRecycleViewAdapter extends RecyclerView.Adapter<DanhBaRecycleViewAdapter.MyViewHolder> {
    private ArrayList<Staff> staffList;
    private RecyclerViewClickListener listener;

    public DanhBaRecycleViewAdapter( ArrayList<Staff> staffList,RecyclerViewClickListener listener) {
        this.staffList = staffList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.danh_ba_nhan_su_row, parent, false);

        return new DanhBaRecycleViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Staff staff = staffList.get(position);

        holder.id.setText(String.valueOf(staff.getStaffId()));
        holder.name.setText(staff.getStaffName());
        holder.phong_ban.setText(staff.getPhongBan());
        holder.chucvu.setText(staff.getchucvu());
        holder.email.setText(staff.getEmail());
        // [START storage_load_with_glide]
        // Reference to an image file in Cloud Storage
        FirebaseApp.initializeApp(holder.thisContext);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("public/" + staff.getStaffId() + ".jpg");

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(holder.thisContext)
                .load(storageReference)
                .into(holder.imgProfile);
        // [END storage_load_with_glide]
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, phong_ban, id, ssn, chucvu, email;
        ImageView imgProfile;
        Context thisContext;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thisContext = itemView.getContext();
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            phong_ban = itemView.findViewById(R.id.position);
            imgProfile = itemView.findViewById(R.id.avatar);
            chucvu = itemView.findViewById(R.id.chucvu);
            email = itemView.findViewById(R.id.email);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }


}
