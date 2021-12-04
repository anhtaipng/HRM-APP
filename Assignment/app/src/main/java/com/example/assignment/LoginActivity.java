package com.example.assignment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private Button button8;
    private EditText id;
    private EditText pass;
    private boolean check;
    private TextView faillogin;
    private Staff value;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        FirebaseApp.initializeApp(this);
        button8 = (Button) findViewById(R.id.button_login);
        id = (EditText) findViewById(R.id.editTextLoginID);
        pass = (EditText) findViewById(R.id.editTextLoginPassword);
        faillogin = (TextView) findViewById(R.id.dangnhapthatbai);
        View.OnClickListener listenerToMainActivity = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faillogin.setTextColor(Color.BLUE);
                faillogin.setText("Đang đăng nhập...");

                if (id.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    faillogin.setTextColor(Color.RED);
                    faillogin.setText("Nhập id và password");
                    return;
                }
                DatabaseReference passStaffRef = FirebaseDatabase.getInstance().getReference("staff").child(id.getText().toString());
                passStaffRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    synchronized public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        if (dataSnapshot.exists()) {
                            value = dataSnapshot.getValue(Staff.class);
                            check = value.getPassword().equals(pass.getText().toString());
                            if (!check) {
                                faillogin.setTextColor(Color.RED);
                                faillogin.setText("Đăng nhập thất bại!");
                            }
                            else {
                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                intent.putExtra("idStaffLogin", value.getStaffId());
                                intent.putExtra("chucvu", value.getchucvu());
                                startActivity(intent);
                                finish();
                            }
                        }
                        else{
                            faillogin.setTextColor(Color.RED);
                            faillogin.setText(("ID không tồn tại"));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("LoginAcitivty", "Failed to read value.", error.toException());
                    }
                });





            }
        };

        button8.setOnClickListener(listenerToMainActivity);

    }
}