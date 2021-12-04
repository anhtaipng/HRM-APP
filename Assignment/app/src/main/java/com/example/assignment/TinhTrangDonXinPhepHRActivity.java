package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

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
import java.util.Collections;
import java.util.List;

public class TinhTrangDonXinPhepHRActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private String idStaffLogin;
    private List<Application> appList;
    private Application2Adapter adapter;
    private Application temp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tinh_trang_don_xin_phep_hr);

        Context thisContext = this;


        Intent intent = getIntent();
        idStaffLogin = intent.getStringExtra("idStaffLogin");

        toolbar = (Toolbar) findViewById(R.id.toobar_tinhtrangdonxinphep);
        toolbar.setTitle("Tình trạng đơn xin phép");
        setSupportActionBar(toolbar);

        appList = new ArrayList<Application>();

        FirebaseApp.initializeApp(this);
        DatabaseReference donxinphepRef = FirebaseDatabase.getInstance().getReference("donxinphep");
        donxinphepRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot donxinphepSnapshot : snapshot.getChildren()){
                    temp = donxinphepSnapshot.getValue(Application.class);
                    appList.add(temp);
                }
                Collections.reverse(appList);
                recyclerView = findViewById(R.id.recycler_view_tinhtrangdonxinphep);
                adapter = new Application2Adapter(appList);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(thisContext);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setHasFixedSize(true);
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
