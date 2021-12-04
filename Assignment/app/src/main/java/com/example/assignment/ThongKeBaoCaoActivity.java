package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThongKeBaoCaoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;

    RecyclerView recycleView;
    BaoCaoRecycleViewAdapter baoCaoRecycleViewAdapter;
    private List<Report> listReport;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_ke_bao_cao);

        toolbar = (Toolbar) findViewById(R.id.tkbaocao);
        toolbar.setTitle("Thống kê báo cáo");
        setSupportActionBar(toolbar);

        //Handle Report event click
        listReport = new ArrayList<Report>();
        recycleView = findViewById(R.id.tk_bao_cao);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        listReport.add(new Report("Thành phần nhân sự", R.drawable.pie, "Thành phần nhân viên trong công ty"));
        listReport.add(new Report("Số nhân viên đi làm", R.drawable.bar, "Số lượng nhân viên đi làm trong tháng gần nhất"));
        listReport.add(new Report("Số lượng nhân viên nghỉ", R.drawable.line, "Số lượng nhân viên nghỉ hàng tháng"));

        baoCaoRecycleViewAdapter = new BaoCaoRecycleViewAdapter(this, listReport);
        recycleView.setAdapter(baoCaoRecycleViewAdapter);
        recycleView.setNestedScrollingEnabled(false);
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
