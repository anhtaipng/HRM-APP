package com.example.assignment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class TomTatDiemDanh extends AppCompatActivity {
    private Toolbar toolbar;
    private DatePicker datePicker;
    private TextView ketquain, ketquaout, ketqua;
    private String  idStaffLogin;

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

        ketqua = findViewById(R.id.textView_ketquadiemdanh);
        ketquain = findViewById(R.id.textView_ketquain);
        ketquaout = findViewById(R.id.textView_ketquaout);
        datePicker = findViewById(R.id.datePicker);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            synchronized public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = (dayOfMonth<10?"0":"") + String.valueOf(dayOfMonth) + "-"
                        + (monthOfYear<10?"0":"") + String.valueOf(monthOfYear+1) + "-"
                        + String.valueOf(year);
                DatabaseReference diemdanhInRef = FirebaseDatabase.getInstance().getReference("diemdanh").child(date)
                        .child(idStaffLogin);
                diemdanhInRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    synchronized public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        if (dataSnapshot.child("in").exists()) {
                            ketquain.setText("Check in: " +   dataSnapshot.child("in").getValue().toString());
                        }
                        else{
                           ketquain.setText("Không có dữ liệu check in.");
                        }
                        if (dataSnapshot.child("out").exists()) {
                            ketquaout.setText("Check out: " +   dataSnapshot.child("out").getValue().toString());
                        }
                        else{
                            ketquaout.setText("Không có dữ liệu check out.");
                        }
                        ketqua.setText("Kết quả điểm danh của bạn ngày " + date + ": ");
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });

            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
