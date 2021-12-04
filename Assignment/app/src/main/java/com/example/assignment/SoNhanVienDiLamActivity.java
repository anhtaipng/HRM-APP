package com.example.assignment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SoNhanVienDiLamActivity extends AppCompatActivity {
    //    ArrayList<Integer> listDayAttendance;
    DatabaseReference databaseRef;
    LineChart mpLineChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_nhan_vien_di_lam);

        mpLineChart = (LineChart) findViewById(R.id.diem_danh_line_chart);
//        listDayAttendance = new ArrayList<>();
        databaseRef = FirebaseDatabase.getInstance().getReference("diemdanh");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mpLineChart.setDragEnabled(true);
//                mpLineChart.setScaleEnabled(false);
                //String date = java.time.LocalDate.now().toString();
                String date = new SimpleDateFormat("yyyy:MM:dd", Locale.getDefault()).format(new Date());
                Log.d("Date", date);
                ArrayList<Entry> yValues = new ArrayList<>();
                if (snapshot.exists()) {
                    for (int i = 1; i <= Integer.parseInt(date.substring(8, 10)); i++) {
                        yValues.add(new Entry(i, 0));
                    }
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //Kiem tra du lieu co phai la thang gan nhat khong
                        if (date.substring(0, 4).equals(ds.getKey().substring(6, 10)) & date.substring(5, 7).equals(ds.getKey().substring(3, 5))) {
//                        if (Integer.parseInt(ds.getKey().substring(0, 2)) - 1 == 0 || Integer.parseInt(ds.getKey().substring(0, 2)) - 1 == 31)
                            yValues.set(Integer.parseInt(ds.getKey().substring(0, 2)) - 1, new Entry(Integer.parseInt(ds.getKey().substring(0, 2)), (int) ds.getChildrenCount()));
//                        else
//                        yValues.add(new Entry(Integer.parseInt(ds.getKey().substring(0, 2)), (int) ds.getChildrenCount()));
                        }
                        Log.d("Data from Firebase", ds.getKey().substring(0, 2) + " " + ds.getKey().substring(3, 5) + " " + ds.getKey().substring(6, 10) + " " + ds.getChildrenCount());

                    }
                    TextView txt = findViewById(R.id.bar_txt);
                    if (yValues.isEmpty())
                        txt.setText("Chưa có dữ liệu");
                    else
                        txt.setText("Số người điểm danh");

                    LineDataSet set1 = new LineDataSet(yValues, "Số nhân viên vắng");
                    set1.setFillAlpha(110);
                    set1.setDrawCircles(true);
                    set1.setCircleHoleRadius(10);

                    Legend legend = mpLineChart.getLegend();
                    legend.setEnabled(true);
                    legend.setForm(Legend.LegendForm.LINE);

                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1);
                    LineData data = new LineData(dataSets);
                    //Set description
                    mpLineChart.getDescription().setText("");
                    mpLineChart.setDrawBorders(true);
                    mpLineChart.setDrawGridBackground(true);

                    mpLineChart.setData(data);
                    mpLineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
