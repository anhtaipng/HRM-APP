package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.time.LocalDate;
import android.app.Activity;
import android.os.Bundle;


import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class tom_tat__diem_danh extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectDate;
    private RecyclerView lvitems;
    private Toolbar toolbar1;
    private diemdanh value;
//    private List<diemdanh> diemdanh=new ArrayList<diemdanh>();
    private Attendance item_day;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tom_tat__diem_danh);

        FirebaseApp.initializeApp(this);
        initWidgets();
        selectDate= LocalDate.now();
        setMonthView();
        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        toolbar1.setTitle("Tóm tắt điểm danh");
        setSupportActionBar(toolbar1);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Tóm tắt điểm danh"); //Thiết lập tiêu đề nếu muốn
//        String title = actionBar.getTitle().toString(); //Lấy tiêu đề nếu muốn
////        actionBar.hide(); //Ẩn ActionBar nếu muốn
//
//
//        actionBar.setDisplayShowHomeEnabled(true);
////        actionBar.setLogo(R.mipmap.back);    //Icon muốn hiện thị
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        diemdanh= new ArrayList<diemdanh>();
//        DatabaseReference item = FirebaseDatabase.getInstance().getReference("HR").child("diemdanh");
//        item.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                    value=postSnapshot.getValue(diemdanh.class);
//                    diemdanh.add(value);
//
//                }
//                lvitems=(RecyclerView) findViewById(R.id.recycle_result);
//                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
//                lvitems.setLayoutManager(layoutManager);
//                lvitems.setHasFixedSize(true);
//                lvitems.setAdapter(new RecycleViewData(getApplicationContext(),diemdanh));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        diemdanh.add(new diemdanh("2021-04-05","Làm việc 8 tiếng tại BKHCM College","8:00-16:00"));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonth(View view) {
        selectDate=selectDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void preMonth(View view) {
        selectDate=selectDate.minusMonths(1);
        setMonthView();
    }
    private void initWidgets() {
        calendarRecyclerView= findViewById((R.id.calendarRecycleView));
        monthYearText=findViewById((R.id.monthYearTV));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate selectDate) {
        ArrayList<String> daysInMonthArray= new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(selectDate);
        int daysInMonth= yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        for(int i=1;i<=42;i++)
        {
            if(i<=dayOfWeek||i> daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i-dayOfWeek));
            }
        }
        return daysInMonthArray;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText) {
        String message ="Selected Date " + dayText + " " + monthYearFromDate(selectDate);
        if (Integer.parseInt(dayText)<=9){
            dayText="0"+dayText;
        }
        String key=dayText+"-"+monthYearFromDate1(selectDate);
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        DatabaseReference day_in_month = FirebaseDatabase.getInstance().getReference("diemdanh").child(key);
//        day_in_month.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<diemdanh> diemdanh=new ArrayList<diemdanh>();
//                if(snapshot.exists()){
//                    for (DataSnapshot postSnapshot: snapshot.getChildren()){
//                        item_day=postSnapshot.getValue(Attendance.class);
//                        String time= item_day.getIn()+'-'+item_day.getOut();
//                        diemdanh.add(new diemdanh(key,"Đã làm việc",time));
//                    }
//                    lvitems=(RecyclerView) findViewById(R.id.recycle_result);
//                    LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
//                    lvitems.setLayoutManager(layoutManager);
//                    lvitems.setHasFixedSize(true);
//                    lvitems.setAdapter(new RecycleViewData(getApplicationContext(),diemdanh));
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"Không có ai làm việc ngày này",Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate selectDate) {
        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("MM-yyyy");
        return selectDate.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate1(LocalDate selectDate) {
        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("MM-yyyy");
        return selectDate.format(formatter);
    }
}