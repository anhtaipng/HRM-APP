package com.example.assignment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


public class ThemNhanVienActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar;
    private ImageView logo;
    private String[] items = {"Phòng IT", "Phòng kinh doanh", "Phòng nhân sự"};
    Button luunhanhvien;
    private TextView manhavien_tudong;
    private EditText matkhaunhanvien;
    private EditText tennhanvien;
    private EditText emailnhanvien;
    private EditText sdtnhanvien;
    private EditText chucvunhanvien;
    private String phongban, idStaffLogin;


    private ImageView imgProfile;

    private static final int REQUEST_IMAGE = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_nhan_vien);

        toolbar = (Toolbar) findViewById(R.id.themnv);
        toolbar.setTitle("Thêm nhân viên");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        idStaffLogin = intent.getStringExtra("idStaffLogin");

        imgProfile = findViewById(R.id.img_profile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerOptions();
            }
        });


        //xu ly spinner
        Spinner spin = (Spinner) findViewById(R.id.spinner_phongban_themnhanvien);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items);
        // provide a particular design for the drop-down lines
        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        // associate GUI spinner and adapter
        spin.setAdapter(aa);

        //them database
        manhavien_tudong = (TextView) findViewById(R.id.textView_manhanvien_tudong);
        FirebaseApp.initializeApp(this);
        DatabaseReference maxIDRef = FirebaseDatabase.getInstance().getReference("max_ID_Staff");
        DatabaseReference staffRef = FirebaseDatabase.getInstance().getReference("staff");
        //Read max ID from the database
        maxIDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue().toString();
                manhavien_tudong.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ThemNhanVienAcitivty", "Failed to read value.", error.toException());
            }
        });

        //add new user
        luunhanhvien = (Button) findViewById(R.id.button_luu_themnhanvien);
        matkhaunhanvien = (EditText) findViewById(R.id.editText_matkhaunhanvien);
        tennhanvien = (EditText) findViewById(R.id.editText_tennhanvien);
        emailnhanvien = (EditText) findViewById(R.id.editText_email);
        sdtnhanvien = (EditText) findViewById(R.id.editText_sdt);
        chucvunhanvien = (EditText) findViewById(R.id.editText_chucvu);

        View.OnClickListener listenerToLuuNhanVien = view -> {
            phongban = spin.getSelectedItem().toString();
            Staff newStaff = new Staff(manhavien_tudong.getText().toString(), tennhanvien.getText().toString(), matkhaunhanvien.getText().toString(), phongban,
                    emailnhanvien.getText().toString(), sdtnhanvien.getText().toString(), chucvunhanvien.getText().toString() );
            staffRef.child(manhavien_tudong.getText().toString()).setValue(newStaff);
            maxIDRef.setValue(Integer.parseInt(manhavien_tudong.getText().toString())+1);
            //tai anh len
            StorageReference avaRef = FirebaseStorage.getInstance().getReference().child("public/"+manhavien_tudong.getText().toString()+".jpg");
            imgProfile.setDrawingCacheEnabled(true);
            imgProfile.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
            byte[] data = baos.toByteArray();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Đang tải avatar lên...");
            progressDialog.show();
            UploadTask uploadTask = avaRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.setTitle(e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(view.getContext());
                    dlgAlert.setMessage("Bạn đã thêm nhân viên mới thành công.");
                    dlgAlert.setTitle("Thông báo");
                    dlgAlert.setPositiveButton("Trở về Trang chủ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            intent.putExtra("idStaffLogin", idStaffLogin);
                            intent.putExtra("chucvu", "HR");
                            startActivity(intent);
                            //finish();
                        }
                    });
                    dlgAlert.setCancelable(false);
                    dlgAlert.create().show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0*snapshot.getBytesTransferred()/snapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Đã tải được "+(int)progress+"%");
                }
            });

        };
        luunhanhvien.setOnClickListener(listenerToLuuNhanVien);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    //xu ly spinner
    @Override
    public void onItemSelected(
            AdapterView<?> parent, View v, int position, long id) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void loadProfileDefault() {
        Glide.with(this).load(R.drawable.baseline_account_circle_black_48)
                .into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(this, R.color.profile_default_tint));
    }

//    @OnClick({R.id.img_plus, R.id.img_profile})
//    public void onProfileImageClick() {
//        showImagePickerOptions();
//    }

    public void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onCameraSelected() {
                launchCamera();
            }

            @Override
            public void onGallerySelected() {
                launchGallery();
            }
        });
    }

    public void launchCamera() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.REQUEST_CODE_TYPE, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public void launchGallery() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.REQUEST_CODE_TYPE, ImagePickerActivity.REQUEST_IMAGE_GALLERY);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public void loadImageProfile(String url) {
        Glide.with(this).load(url)
                .into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                loadImageProfile(uri.toString());
            }
        }
    }
}
