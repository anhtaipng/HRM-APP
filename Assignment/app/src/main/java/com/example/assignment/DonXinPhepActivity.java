package com.example.assignment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DonXinPhepActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String idStaffLogin, idDonXinPhep;
    private TextView nguoiguidon, dateStart, dateEnd, note, sumDay;
    private CheckBox yte, hangnam, khac;
    private Button chapnhan, tuchoi;
    private ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.don_xin_phep_alone);



        Intent intent = getIntent();
        idStaffLogin = intent.getStringExtra("idStaffLogin");
        idDonXinPhep = intent.getStringExtra("idDonXinPhep");

        toolbar = (Toolbar) findViewById(R.id.toolbar_donxinphepalone);
        toolbar.setTitle("Duyệt đơn xin phép");
        setSupportActionBar(toolbar);

        imgProfile = findViewById(R.id.avatar);
        nguoiguidon = findViewById(R.id.textView_nguoiguidon_donxinphepalone);
        dateEnd = findViewById(R.id.textView_dateEnd_donxinphepalone);
        dateStart = findViewById(R.id.textView_dateStart_donxinphepalone);
        note = findViewById(R.id.textView_note_donxinphepalone);
        yte = findViewById(R.id.checkbox_yte_donxinphepalone);
        hangnam = findViewById(R.id.checkbox_hangnam_donxinphepalone);
        khac = findViewById(R.id.checkbox_lydokhac_donxinphepalone);
        sumDay = findViewById(R.id.textView_sumDay_donxinphepalone);

        FirebaseApp.initializeApp(this);
        DatabaseReference donxinphepRef = FirebaseDatabase.getInstance().getReference("donxinphep").child(idDonXinPhep);

        Context mcontext = this;
        donxinphepRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Application application = snapshot.getValue(Application.class);
                DatabaseReference staffRef = FirebaseDatabase.getInstance().getReference("staff").child(application.getIdStaffLogin());
                staffRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Staff staff = snapshot.getValue(Staff.class);
                        nguoiguidon.setText(staff.getStaffName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                // [START storage_load_with_glide]
                // Reference to an image file in Cloud Storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("public/" + application.getIdStaffLogin() + ".jpg");

                // Download directly from StorageReference using Glide
                // (See MyAppGlideModule for Loader registration)
                Glide.with(mcontext)
                        .load(storageReference)
                        .into(imgProfile);
                // [END storage_load_with_glide]
                dateEnd.setText(application.getDateEnd());
                dateStart.setText(application.getDateStart());
                note.setText(application.getNote());
                sumDay.setText("Tổng số ngày nghỉ: " + application.getSumDay() + " ngày");
                String reason = application.getReason();
                if (reason.equals("yte"))
                    yte.setChecked(true);
                else if (reason.equals("hangnam"))
                    hangnam.setChecked(true);
                else
                    khac.setChecked(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chapnhan = findViewById(R.id.btn_chapnhan_donxinphep);
        tuchoi = findViewById(R.id.btn_tuchoi_donxinphep);

        chapnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donxinphepRef.child("state").setValue("accept");
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(v.getContext());
                dlgAlert.setMessage("Bạn đã xác nhận thành công đơn nghỉ phép.");
                dlgAlert.setTitle("Thông báo");
                dlgAlert.setPositiveButton("Trở về Trang chủ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra("idStaffLogin", idStaffLogin);
                        intent.putExtra("chucvu", "Quản lý");
                        startActivity(intent);
                        finish();
                    }
                });
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();
            }
        });

        tuchoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donxinphepRef.child("state").setValue("reject");
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(v.getContext());
                dlgAlert.setMessage("Bạn đã từ chối thành công đơn nghỉ phép.");
                dlgAlert.setTitle("Thông báo");
                dlgAlert.setPositiveButton("Trở về Trang chủ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra("idStaffLogin", idStaffLogin);
                        intent.putExtra("chucvu", "Quản lý");
                        startActivity(intent);
                        finish();
                    }
                });
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();
            }
        });




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
