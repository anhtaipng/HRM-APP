package com.example.assignment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ThongTinNhanVienActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_tin_nhan_vien);

        toolbar = (Toolbar) findViewById(R.id.ttnv);
        toolbar.setTitle("Thông tin nhân viên");
        setSupportActionBar(toolbar);

        TextView nameTxt=findViewById(R.id.s_name);
        TextView idTxt=findViewById(R.id.s_id);
        TextView emailTxt=findViewById(R.id.s_mail);
        TextView phoneTxt=findViewById(R.id.s_phone);
        TextView positionTxt=findViewById(R.id.s_pos);
        TextView deptTxt=findViewById(R.id.s_dept);


        String s_name="Staff don't have profile";
        String s_id ="NaN";
        String s_email="Staff don't have email";
        String s_phone="Staff don't have phone number";
        String s_position="Staff don't have position";
        String s_dept="Staff don't have department";

        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            s_name=extras.getString("s_name");
            s_id=extras.getString("s_id");
            s_email=extras.getString("s_email");
            s_phone=extras.getString("s_phone");
            s_position=extras.getString("s_position");
            s_dept=extras.getString("s_dept");
        }
        nameTxt.setText(s_name);
        idTxt.setText(s_id);
        phoneTxt.setText(s_phone);
        emailTxt.setText(s_email);
        positionTxt.setText(s_position);
        deptTxt.setText(s_dept);
        FirebaseApp.initializeApp(this);
        // [START storage_load_with_glide]
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("public/" + idTxt.getText().toString() + ".jpg");

        // ImageView in your Activity
        ImageView imgProfile = findViewById(R.id.img_profile_thongtinnhanvien);

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(this)
                .load(storageReference)
                .into(imgProfile);
        // [END storage_load_with_glide]
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
