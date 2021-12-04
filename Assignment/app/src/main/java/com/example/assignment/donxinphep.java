package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class donxinphep extends AppCompatActivity {
    private Toolbar toolbar1;
    private EditText idSupervisor, dateStart, dateEnd, note;
    private String idApplication, idStaffLogin, reason, chucvu;
    private CheckBox yte, hangnam, khac;
    private Button btn_guidon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donxinphep);

        toolbar1 = (Toolbar) findViewById(R.id.toolbar2);
        toolbar1.setTitle("Đơn xin phép");
        setSupportActionBar(toolbar1);

        idSupervisor = findViewById(R.id.editTextIdSupervisor);
        dateStart = findViewById(R.id.editTextDateStart);
        dateEnd = findViewById(R.id.editTextDateEnd);
        note = findViewById(R.id.editTextNote);

        Intent intent = getIntent();
        idStaffLogin = intent.getStringExtra("idStaffLogin");

        yte = findViewById(R.id.checkbox_yte);
        hangnam = findViewById(R.id.checkbox_hangnam);
        khac = findViewById(R.id.checkbox_lydokhac);

        btn_guidon = findViewById(R.id.btn_gui_donxinphep);


        FirebaseApp.initializeApp(this);
        //load chuc vu de truyen vao mainactivity sau khi chay lai
        DatabaseReference passStaffRef = FirebaseDatabase.getInstance().getReference("staff").child(idStaffLogin);
        passStaffRef.addValueEventListener(new ValueEventListener() {
            @Override
            synchronized public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    Staff value = dataSnapshot.getValue(Staff.class);
                    chucvu = value.getchucvu();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        DatabaseReference maxIDRef = FirebaseDatabase.getInstance().getReference("max_ID_donxinphep");
        DatabaseReference donxinphepRef = FirebaseDatabase.getInstance().getReference("donxinphep");
        maxIDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                idApplication = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ThemNhanVienAcitivty", "Failed to read value.", error.toException());
            }
        });

        btn_guidon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = yte.isChecked() ? "yte" : hangnam.isChecked() ? "hangnam" : "khac";
                //tinh tong so ngay nghi
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date firstDate = null;
                try {
                    firstDate = sdf.parse(dateStart.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date secondDate = null;
                try {
                    secondDate = sdf.parse(dateEnd.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long diff = secondDate.getTime() - firstDate.getTime();
                TimeUnit time = TimeUnit.DAYS;
                long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);

                Application newApp = new Application(idApplication, idSupervisor.getText().toString(), idStaffLogin, dateStart.getText().toString(),
                        dateEnd.getText().toString(), reason, note.getText().toString(), "null", Long.toString(diffrence));
                donxinphepRef.child(idApplication).setValue(newApp);
                maxIDRef.setValue(Integer.parseInt(idApplication)+1);
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(v.getContext());
                dlgAlert.setMessage("Bạn đã nộp đơn xin phép thành công và vui lòng chờ xác nhận từ quản lý.");
                dlgAlert.setTitle("Thông báo");
                dlgAlert.setPositiveButton("Trở về Trang chủ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra("idStaffLogin", idStaffLogin);
                        intent.putExtra("chucvu", chucvu);
                        startActivity(intent);
                        //finish();
                    }
                });
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();
            }
        });





    }

    //handle mui ten back

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
}