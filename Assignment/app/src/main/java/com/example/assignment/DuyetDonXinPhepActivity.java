package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

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

public class DuyetDonXinPhepActivity  extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Toolbar toolbar;
    private String idStaffLogin;
    private RecyclerView recyclerView;
    private ApplicationAdapter adapter;
    private ArrayList<Application> appList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duyet_don_xin_phep);

        Context thisContext = this;
        recyclerView = findViewById(R.id.recycler_view_duyetdonxinphep);

        Intent intent = getIntent();
        idStaffLogin = intent.getStringExtra("idStaffLogin");

        toolbar = (Toolbar) findViewById(R.id.toobar_duyetdonxinphep);
        toolbar.setTitle("Duyệt đơn xin phép");
        setSupportActionBar(toolbar);



        FirebaseApp.initializeApp(this);
        DatabaseReference donxinphepRef = FirebaseDatabase.getInstance().getReference("donxinphep");
        donxinphepRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appList = new ArrayList<Application>();
                for (DataSnapshot donxinphepSnapshot : snapshot.getChildren()){
                    Application temp = donxinphepSnapshot.getValue(Application.class);
                    if (temp.getIdSupervisor().equals(idStaffLogin) && temp.getState().equals("null"))
                        appList.add(temp);
                }
                adapter = new ApplicationAdapter(appList);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(thisContext);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(mLayoutManager);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
