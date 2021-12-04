package com.example.assignment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TomTatDiemDanhMoiNgay extends AppCompatActivity {
    private Toolbar toolbar;
    private DatePicker datePicker;
    private TextView ketquadiemdanh;
    private String date;
    private List<Attendance> attList;
    private RecyclerView recyclerView;
    private TomTatDiemDanhHRAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tom_tat_diem_danh_moi_ngay);

        toolbar = (Toolbar) findViewById(R.id.toolbar_tomtatdiemdanh);
        toolbar.setTitle("Kết quả điểm danh");
        setSupportActionBar(toolbar);
        Context thisContext = this;

        Intent intent = getIntent();
        date = intent.getStringExtra("date");


        FirebaseApp.initializeApp(this);

        datePicker = findViewById(R.id.datePicker);
        recyclerView = findViewById(R.id.recycler_view_tomtatdiemdanh);

        attList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        FirebaseApp.initializeApp(this);
        DatabaseReference diemdanhRef = FirebaseDatabase.getInstance().getReference("diemdanh").child(date);
        diemdanhRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot diemdanhSnapshot : snapshot.getChildren()){
                    String id = diemdanhSnapshot.getKey().toString();
                    String checkin, checkout;
                    if (diemdanhSnapshot.child("in").exists())
                        checkin = diemdanhSnapshot.child("in").getValue().toString();
                    else
                        checkin = "Không có dữ liệu check in.";
                    if (diemdanhSnapshot.child("out").exists())
                        checkout = diemdanhSnapshot.child("out").getValue().toString();
                    else
                        checkout = "Không có dữ liệu check out.";
                    attList.add(new Attendance(id, checkin, checkout));
                }
                adapter = new TomTatDiemDanhHRAdapter(attList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setHasFixedSize(true);
                if (adapter.getItemCount()==0){
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(thisContext);
                    dlgAlert.setMessage("Không có nhân viên nào điểm danh vào ngày " + date);
                    dlgAlert.setTitle("Thông báo");
                    dlgAlert.setPositiveButton("Trở về", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    dlgAlert.setCancelable(false);
                    dlgAlert.create().show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
