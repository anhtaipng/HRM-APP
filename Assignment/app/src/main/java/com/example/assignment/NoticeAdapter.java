package com.example.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter <NoticeAdapter.MyViewHolderBangTin>{
    private List<Notice> notices;
    private MyViewHolderBangTin holder;
    private int position;

    public NoticeAdapter(List<Notice> notices){
        this.notices = notices;
    }

    @Override
    public MyViewHolderBangTin onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_newsfeed_recycleview, parent, false);

        return new MyViewHolderBangTin(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolderBangTin holder, int position) {
        Notice notice = notices.get(position);
        holder.chude.setText(notice.getChude());
        holder.nguoi.setText(notice.getTennguoitao() + " - " +notice.getIdStaffCreate());
        holder.noidung.setText(notice.getNoidung());
        holder.ngaygiotao.setText("Đã đăng vào lúc " + notice.getNgaygiotao());
        // [START storage_load_with_glide]
        // Reference to an image file in Cloud Storage
        FirebaseApp.initializeApp(holder.thisContext);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("public/" + notice.getIdStaffCreate() + ".jpg");

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(holder.thisContext)
                .load(storageReference)
                .into(holder.imgProfile);
        // [END storage_load_with_glide]
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public class MyViewHolderBangTin extends RecyclerView.ViewHolder {

        public TextView nguoi;
        public TextView chude;
        public TextView noidung;
        public TextView ngaygiotao;
        public ImageView imgProfile;
        public Context thisContext;

        public MyViewHolderBangTin(View itemView) {
            super(itemView);
            thisContext = itemView.getContext();
            nguoi = itemView.findViewById(R.id.nguoitaothongbao);
            chude = itemView.findViewById(R.id.chudethongbao);
            noidung = itemView.findViewById(R.id.noidungthongbao);
            ngaygiotao = itemView.findViewById(R.id.ngaygiotao);
            imgProfile = itemView.findViewById(R.id.img_profile_notice);
        }
    }
}
