package com.example.assignment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ThongBaoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Toolbar toolbar;
    private String[] items = {"Bảng tin Công Ty", "Bảng tin phòng IT", "Bảng tin phòng kinh doanh", "Bảng tin phòng nhân sự"};
    private Button button_taothongbao;
    private String idStaffLogin;
    private EditText chude;
    private EditText noidung;
    private String bangtin;
    private String idNotice;
    private String chucvu;
    private String tennguoitao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_bao);

        toolbar = (Toolbar) findViewById(R.id.ttnv);
        toolbar.setTitle("Tạo thông báo");
        setSupportActionBar(toolbar);

        //nhan du lieu id Staff Login tu fragment home;
        Intent intent = getIntent();
        idStaffLogin = intent.getStringExtra("idStaffLogin");

        //xu ly spinner
        Spinner spin = (Spinner) findViewById(R.id.spinner_thongbao);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items);
        // provide a particular design for the drop-down lines
        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        // associate GUI spinner and adapter
        spin.setAdapter(aa);


        //
        chude = (EditText) findViewById(R.id.chu_de_thong_bao);
        noidung = (EditText) findViewById(R.id.noi_dung_thong_bao);


        FirebaseApp.initializeApp(this);
        DatabaseReference maxIDNoticeRef = FirebaseDatabase.getInstance().getReference("max_ID_Notice");
        DatabaseReference noticeRef = FirebaseDatabase.getInstance().getReference("notice");
        DatabaseReference tennguoitaoRef = FirebaseDatabase.getInstance().getReference("staff").child(idStaffLogin);
        //Lay ten nguoi tao
        tennguoitaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tennguoitao = snapshot.getValue(Staff.class).getStaffName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //read max ID from database
        maxIDNoticeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                idNotice = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ThongBaoAcitivty", "Failed to read value.", error.toException());
            }
        });

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
                // Failed to read value
                Log.w("thongBaoAcitivty", "Failed to read value.", error.toException());
            }
        });

        //xuly handle button dang tin
        button_taothongbao = (Button) findViewById(R.id.button_taothongbao);
        View.OnClickListener listenerToMainActivity = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bangtin = spin.getSelectedItem().toString();
                String currentTime = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault()).format(new Date());
                Notice newNotice = new Notice(idNotice, idStaffLogin, tennguoitao, bangtin, chude.getText().toString(), noidung.getText().toString(),
                        currentTime);
                noticeRef.child(idNotice).setValue(newNotice);
                maxIDNoticeRef.setValue(Integer.parseInt(idNotice)+1);
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(view.getContext());
                dlgAlert.setMessage("Bạn đã đăng thông báo thành công.");
                dlgAlert.setTitle("Thông báo");
                dlgAlert.setPositiveButton("Trở về Trang chủ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        intent.putExtra("idStaffLogin", idStaffLogin);
                        intent.putExtra("chucvu", chucvu);
                        startActivity(intent);
                        //finish();
                    }
                });
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();
            }

        };

        button_taothongbao.setOnClickListener(listenerToMainActivity);
    }

    //xu ly spinner
    @Override
    public void onItemSelected(
            AdapterView<?> parent, View v, int position, long id) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }




    //handle mui ten back

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
