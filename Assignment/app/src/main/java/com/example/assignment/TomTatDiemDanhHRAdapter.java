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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class TomTatDiemDanhHRAdapter extends RecyclerView.Adapter <TomTatDiemDanhHRAdapter.MyViewHolderApplication>{
    private List<Attendance> attList;

    public TomTatDiemDanhHRAdapter(List<Attendance> attList){
       this.attList = attList;
    }
    @NonNull
    @Override
    public TomTatDiemDanhHRAdapter.MyViewHolderApplication onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tomtatdiemdanh_recycleview, parent, false);
        return new TomTatDiemDanhHRAdapter.MyViewHolderApplication(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TomTatDiemDanhHRAdapter.MyViewHolderApplication holder, int position) {
        Attendance attendance = attList.get(position);

        FirebaseApp.initializeApp(holder.thisContext);
        DatabaseReference staffNameRef = FirebaseDatabase.getInstance().getReference("staff").child(attendance.getIdStaff()).child("staffName");
        staffNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue().toString();
                holder.ten.setText(value + " - ID: " + attendance.getIdStaff());
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        holder.diemdanhvao.setText("Check in: " +attendance.getCheckin());
        holder.diemdanhra.setText("Check out: " +attendance.getCheckout());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("public/" + attendance.getIdStaff() + ".jpg");

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(holder.thisContext)
                .load(storageReference)
                .into(holder.avatar);
        // [END storage_load_with_glide]

    }

    @Override
    public int getItemCount() {
        return attList.size();
    }

    public class MyViewHolderApplication extends RecyclerView.ViewHolder {
        private TextView ten, diemdanhvao, diemdanhra;
        private ImageView avatar;
        private Context thisContext;
        public MyViewHolderApplication(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.ten);
            diemdanhvao = itemView.findViewById(R.id.diemdanhvao);
            diemdanhra = itemView.findViewById(R.id.diemdanhra);
            avatar = itemView.findViewById(R.id.avatar);
            thisContext = itemView.getContext();

        }
    }
}
