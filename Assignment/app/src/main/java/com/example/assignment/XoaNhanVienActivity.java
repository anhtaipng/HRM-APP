package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.DanhBaRecycleViewAdapter;
import com.example.assignment.R;
import com.example.assignment.Staff;
import com.example.assignment.ThongTinNhanVienActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class XoaNhanVienActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private String idHR;

    //for recycle view

    ArrayList<Staff> listStaff;
    RecyclerView recycleView;
    XoaNhanVienAdapter danhBaRecycleViewAdapter;
    DatabaseReference databaseReference;

    //    String name[], danh_ba[],id[],sex[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xoa_nhan_vien);

        toolbar = (Toolbar) findViewById(R.id.toolbar_xoanhanvien);
        toolbar.setTitle("Xóa nhân viên");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        idHR = intent.getStringExtra("idStaffLogin");


        //for recycle view
//        setOnClickListener();
        recycleView = findViewById(R.id.recycler_view_xoanhanvien);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("staff");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listStaff = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Staff staff = ds.getValue(Staff.class);
                    listStaff.add(staff);
                }
                danhBaRecycleViewAdapter = new XoaNhanVienAdapter(listStaff, idHR);
                recycleView.setAdapter(danhBaRecycleViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
