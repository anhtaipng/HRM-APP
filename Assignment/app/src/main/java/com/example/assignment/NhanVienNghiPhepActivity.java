package com.example.assignment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NhanVienNghiPhepActivity extends AppCompatActivity {
    BarChart mpBarChart;
    ArrayList<BarEntry> visitors;
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhan_vien_nghi_phep);
        mpBarChart = findViewById(R.id.bar_chart);

        AttendData attendData[] = new AttendData[12];
        for (int i = 0; i < months.length; i++) {
            AttendData aData = new AttendData(months[i], 0);
            attendData[i] = aData;
        }

        TextView title = (TextView) findViewById(R.id.bar_title);
        title.setText("Tổng số ngày nghỉ");

        databaseReference = FirebaseDatabase.getInstance().getReference("donxinphep");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Application application = ds.getValue(Application.class);
                        assert application != null;
                        int startMonth = Integer.parseInt(application.getDateStart().substring(3, 5));
                        int endMonth = Integer.parseInt(application.getDateEnd().substring(3, 5));
                        if (startMonth == endMonth) {
                            //Nếu nghỉ cùng một tháng
                            attendData[startMonth - 1].setDayOff(attendData[startMonth - 1].getDayOff() + Integer.parseInt(application.getSumDay()));
//                            Log.d("Month:", attendData[startMonth - 1].getMonth() + " " + attendData[startMonth - 1].getDayOff());
                        } else {
                            //Nếu nghỉ sang tháng khác
                            int startDay = Integer.parseInt(application.getDateStart().substring(0, 2));
                            int endDay = Integer.parseInt(application.getDateEnd().substring(0, 2));
                            int year=Integer.parseInt(application.getDateEnd().substring(6, 10));
                            int dayOfffirst;
                            int dayOffSecond;
                            if (startMonth == 1 | startMonth == 3 | startMonth == 5 | startMonth == 7 | startMonth == 8 | startMonth == 10 | startMonth == 12) {
                                dayOfffirst = 31 - startDay+1;
                            } else if (startMonth == 2) {
                                if (year % 4 == 0 & year % 100 != 0)
                                    //Năm nhuận
                                    dayOfffirst = 29 - startDay+1;
                                else dayOfffirst = 28 - startDay+1;
                            }else{
                                dayOfffirst=30-startDay+1;
                            }
                            dayOffSecond=endDay-1;
                            attendData[startMonth - 1].setDayOff(attendData[startMonth - 1].getDayOff() + dayOfffirst);
                            attendData[endMonth - 1].setDayOff(attendData[endMonth - 1].getDayOff() + dayOffSecond);
                        }
                    }
                    visitors = new ArrayList<>();
                    for (int i = 0; i < attendData.length; i++) {
                        String month = attendData[i].getMonth();
                        int dayOff = attendData[i].getDayOff();
                        visitors.add(new BarEntry(i, dayOff));
                    }

                    BarDataSet barDataSet = new BarDataSet(visitors, "Tổng số ngày nghỉ");
                    barDataSet.setColor(Color.BLUE);
                    Description description = new Description();
                    description.setText("Months");
                    mpBarChart.setDescription(description);
                    BarData barData = new BarData(barDataSet);
                    mpBarChart.setData(barData);

                    XAxis xAxis = mpBarChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(months));

                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    xAxis.setDrawAxisLine(false);
                    xAxis.setGranularity(1f);
                    xAxis.setLabelCount(months.length);
                    xAxis.setLabelRotationAngle(270);
                    mpBarChart.animateY(1);
                    mpBarChart.invalidate();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
