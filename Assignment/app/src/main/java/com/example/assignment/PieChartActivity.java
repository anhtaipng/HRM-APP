package com.example.assignment;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {
    ArrayList<Staff> listStaff;
    DatabaseReference databaseRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart);

        final PieChart pieChart = findViewById(R.id.button_piechart);
        final ArrayList<PieEntry> visitors = new ArrayList<>();
        listStaff = new ArrayList<>();
        final int[] ITDeptCount = {0};
        final int[] KDDeptCount = {0};
        final int[] NSDeptCount = {0};

        databaseRef = FirebaseDatabase.getInstance().getReference("staff");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listStaff.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Staff staff = ds.getValue(Staff.class);
                        listStaff.add(staff);
//                    Log.d("Luu Van Tien",staff.getPhongBan());

                    }
                    TextView txt = findViewById(R.id.pie_txt);
                    if (listStaff == null) {
                        txt.setText("Chưa có báo cáo");
                    } else {
                        txt.setText("Thành phần nhân sự");
                    }
                    for (Staff i : listStaff) {
                        switch (i.getPhongBan()) {
                            case "Phòng IT": {
                                ITDeptCount[0]++;
                                break;
                            }
                            case "Phòng kinh doanh": {
                                KDDeptCount[0]++;
                                break;
                            }
                            case "Phòng nhân sự": {
                                NSDeptCount[0]++;
                                break;
                            }
                            default:
                                ITDeptCount[0]++;
                        }
                    }
                    visitors.add(new PieEntry(ITDeptCount[0], "Phòng IT"));
                    visitors.add(new PieEntry(NSDeptCount[0], "Phòng NS"));
                    visitors.add(new PieEntry(KDDeptCount[0], "Phòng KD"));

                    PieDataSet pieDataSet = new PieDataSet(visitors, "Thành phần nhân sự");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData = new PieData(pieDataSet);

                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("Nhân sự");
                    pieChart.animate();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
