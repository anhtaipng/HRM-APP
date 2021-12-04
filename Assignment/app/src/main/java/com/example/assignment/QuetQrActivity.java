package com.example.assignment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// implements onClickListener for the onclick behaviour of button
public class QuetQrActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnCheckOut, btnCheckIn;
    //private TextView messageText;
    private TextClock clock;
    private String typeCheck, idStaffLogin;
    private Activity acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diemdanh_qrcode);
        acc = this;

        Intent intent = getIntent();
        idStaffLogin = intent.getStringExtra("idStaffLogin");

        toolbar = (Toolbar) findViewById(R.id.quetQR);
        toolbar.setTitle("Quét QR Code");
        setSupportActionBar(toolbar);

        // referencing and initializing
        // the button and textviews
        btnCheckOut = findViewById(R.id.btn_checkout);
        btnCheckIn = findViewById(R.id.btn_checkin);
        //messageText = findViewById(R.id.textContent);
        clock = findViewById(R.id.clock);



        // adding listener to the button
        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeCheck = "in";
                IntentIntegrator intentIntegrator = new IntentIntegrator(acc);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.setPrompt("Scan QR Code để điểm danh Check in");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.initiateScan();
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeCheck = "out";
                IntentIntegrator intentIntegrator = new IntentIntegrator(acc);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.setPrompt("Scan QR Code để điểm danh Check out");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Điểm danh thất bại.");
                dlgAlert.setTitle("Thông báo");
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                String date = intentResult.getContents();
                //messageText.setText(date + "/" + currentTime + "/" +typeCheck);
                FirebaseApp.initializeApp(this);
                DatabaseReference diemDanhRef = FirebaseDatabase.getInstance().getReference("diemdanh");
                diemDanhRef.child(date).child(idStaffLogin).child(typeCheck).setValue(currentTime);
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Điểm danh thành công.");
                dlgAlert.setTitle("Thông báo");
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}