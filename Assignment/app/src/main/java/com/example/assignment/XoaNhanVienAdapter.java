
package com.example.assignment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class XoaNhanVienAdapter extends RecyclerView.Adapter<XoaNhanVienAdapter.MyViewHolder> {
    private ArrayList<Staff> staffList;
    private String idHR;

    public XoaNhanVienAdapter( ArrayList<Staff> staffList, String idHR) {
        this.staffList = staffList;
        this.idHR = idHR;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_xoanhavien_recycleview, parent, false);

        return new XoaNhanVienAdapter.MyViewHolder(itemView);
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

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, phong_ban, id, ssn, chucvu, email;
        ImageView imgProfile;
        Button btnXoa;
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
            btnXoa = itemView.findViewById(R.id.btn_xoanhanvien);
            btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseApp.initializeApp(itemView.getContext());
                    DatabaseReference staffRef = FirebaseDatabase.getInstance().getReference("staff").child(id.getText().toString());
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(itemView.getContext());
                    dlgAlert.setMessage("Bạn có chắc chắn muốn xóa nhân viên này? Dữ liệu khi đã xóa sẽ không thể khôi phục.");
                    dlgAlert.setTitle("Cảnh báo");
                    dlgAlert.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            staffRef.removeValue();
                            dialog.cancel();
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), XoaNhanVienActivity.class);
                            intent.putExtra("idStaffLogin", idHR);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    });
                    dlgAlert.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
            });
        }

    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }


}
