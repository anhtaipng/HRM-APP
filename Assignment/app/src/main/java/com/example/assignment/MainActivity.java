package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//import android.support.design.widget.CoordinatorLayout;
//import androidx.constraintlayout.widget.ConstraintLayout;


public class MainActivity extends AppCompatActivity {
    private String chucvu;
    private String idStaffLogin;
    private Toolbar toolbar;

    @Override
    synchronized protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        chucvu = intent.getStringExtra("chucvu");
        idStaffLogin = intent.getStringExtra("idStaffLogin");
        if (chucvu.equals("HR"))
            loadFragment(new HomeHRFragment(idStaffLogin));
        else if (chucvu.equals("Quản lý"))
            loadFragment(new HomeManagerFragment(idStaffLogin));
        else
            loadFragment(new HomeFragment(idStaffLogin));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Dashboard");
                    Intent intent = getIntent();
                    chucvu = intent.getStringExtra("chucvu");
                    idStaffLogin = intent.getStringExtra("idStaffLogin");
                    if (chucvu.equals("HR"))
                        loadFragment(new HomeHRFragment(idStaffLogin));
                    else if (chucvu.equals("Quản lý"))
                        loadFragment(new HomeManagerFragment(idStaffLogin));
                    else
                        loadFragment(new HomeFragment(idStaffLogin));
                    return true;
                case R.id.navigation_newsfeed:
                    toolbar.setTitle("Bảng tin");
                    fragment = new NewsfeedFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("Hồ sơ");
                    fragment = new ProfileFragment(idStaffLogin);
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }





}
