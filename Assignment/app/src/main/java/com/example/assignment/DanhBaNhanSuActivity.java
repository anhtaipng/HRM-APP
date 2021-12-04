package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
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
import java.util.List;

public class DanhBaNhanSuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private String[] phong_ban = {"Toàn bộ nhân viên", "Phòng IT", "Phòng kinh doanh", "Phòng nhân sự"};
    private DanhBaRecycleViewAdapter.RecyclerViewClickListener listener;

    Spinner spinner;

    //for recycle view

    ArrayList<Staff> listStaff;
    RecyclerView recycleView;
    DanhBaRecycleViewAdapter danhBaRecycleViewAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_ba_nhan_su);

        toolbar = (Toolbar) findViewById(R.id.ttnv);
        toolbar.setTitle("Thông tin nhân viên");
        setSupportActionBar(toolbar);


        spinner = findViewById(R.id.spinner_danhbanhansu);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            synchronized public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listStaff = new ArrayList<Staff>();
                FirebaseApp.initializeApp(view.getContext());
                databaseReference = FirebaseDatabase.getInstance().getReference("staff");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Staff staff = ds.getValue(Staff.class);
                            if (spinner.getSelectedItem().toString().equals("Toàn bộ nhân viên"))
                                listStaff.add(staff);
                            else if (spinner.getSelectedItem().toString().equals(staff.getPhongBan()))
                                listStaff.add(staff);
                        }
                        danhBaRecycleViewAdapter = new DanhBaRecycleViewAdapter(listStaff, listener);
                        recycleView.setAdapter(danhBaRecycleViewAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                setOnClickListener();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phong_ban);
        // provide a particular design for the drop-down lines
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // associate GUI spinner and adapter
        spinner.setAdapter(aa);

        recycleView = findViewById(R.id.danhba);
        recycleView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Intent intent = new Intent(getApplicationContext(), ThongTinNhanVienActivity.class);
            intent.putExtra("s_name", listStaff.get(position).getStaffName());
            intent.putExtra("s_id", listStaff.get(position).getStaffId());
            intent.putExtra("s_phone", listStaff.get(position).getPhone());
            intent.putExtra("s_email", listStaff.get(position).getEmail());
            intent.putExtra("s_position", listStaff.get(position).getchucvu());
            intent.putExtra("s_dept", listStaff.get(position).getPhongBan());
            startActivity(intent);
        };
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
