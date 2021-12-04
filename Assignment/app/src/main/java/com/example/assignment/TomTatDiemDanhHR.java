package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.List;

public class TomTatDiemDanhHR extends AppCompatActivity {
    private Toolbar toolbar;
    private DatePicker datePicker;
    private TextView ketquadiemdanh;
    private String idStaffLogin;
    private List<Attendance> attList;
    private RecyclerView recyclerView;
    private TomTatDiemDanhHRAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tom_tat_diem_danh);

        toolbar = (Toolbar) findViewById(R.id.toolbar_tomtatdiemdanh);
        toolbar.setTitle("Kết quả điểm danh");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        idStaffLogin = intent.getStringExtra("idStaffLogin");


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month  = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        FirebaseApp.initializeApp(this);

        datePicker = findViewById(R.id.datePicker);
        recyclerView = findViewById(R.id.recycler_view_tomtatdiemdanh);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            synchronized public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = (dayOfMonth<10?"0":"") + String.valueOf(dayOfMonth) + "-"
                        + (monthOfYear<10?"0":"") + String.valueOf(monthOfYear+1) + "-"
                        + String.valueOf(year);
                Intent newIntent = new Intent(view.getContext(), TomTatDiemDanhMoiNgay.class);
                newIntent.putExtra("date", date);
                startActivity(newIntent);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
