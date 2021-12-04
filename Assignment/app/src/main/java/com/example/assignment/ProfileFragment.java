package com.example.assignment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    Button luunhanvien;
    ImageButton logout;
    private TextView manhanvien;
    private EditText matkhaunhanvien;
    private EditText tennhanvien;
    private EditText emailnhanvien;
    private EditText sdtnhanvien;
    private EditText chucvunhanvien;
    private TextView phongban;
    private String id;
    private ImageView imgProfile;

    public ProfileFragment(String idfStaffLogin) {
        // Required empty public constructor
        id = idfStaffLogin;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment("none");
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseApp.initializeApp(view.getContext());
        DatabaseReference staffRef = FirebaseDatabase.getInstance().getReference("staff").child(id);
        luunhanvien = (Button) view.findViewById(R.id.button_luu_profile);
        matkhaunhanvien = (EditText) view.findViewById(R.id.editText_matkhaunhanvien_profile);
        tennhanvien = (EditText) view.findViewById(R.id.editText_tennhanvien_profile);
        emailnhanvien = (EditText) view.findViewById(R.id.editText_email_profile);
        sdtnhanvien = (EditText) view.findViewById(R.id.editText_sdt_profile);
        chucvunhanvien = (EditText) view.findViewById(R.id.editText_chucvu_profile);
        phongban = (TextView) view.findViewById(R.id.textView__phongban_profile2);
        manhanvien = (TextView) view.findViewById(R.id.textView_manhanvien_tudong_profile);



        staffRef.addValueEventListener(new ValueEventListener() {
           @Override
           synchronized public void onDataChange(DataSnapshot dataSnapshot) {
               // This method is called once with the initial value and again
               // whenever data at this location is updated.
               if (dataSnapshot.exists()) {
                   Staff value = dataSnapshot.getValue(Staff.class);
                   manhanvien.setText(value.getStaffId());
                   matkhaunhanvien.setText(value.getPassword());
                   tennhanvien.setText(value.getStaffName());
                   emailnhanvien.setText(value.getEmail());
                   sdtnhanvien.setText(value.getPhone());
                   phongban.setText(value.getPhongBan());
                   chucvunhanvien.setText(value.getchucvu());
                   // [START storage_load_with_glide]
                   // Reference to an image file in Cloud Storage
                   StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("public/" + value.getStaffId() + ".jpg");

                   // ImageView in your Activity
                   imgProfile = view.findViewById(R.id.img_profile_fragment);

                   // Download directly from StorageReference using Glide
                   // (See MyAppGlideModule for Loader registration)
                   Glide.with(view.getContext())
                           .load(storageReference)
                           .into(imgProfile);
                   // [END storage_load_with_glide]
               } else {
                    return;
               }
           }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("LoginAcitivty", "Failed to read value.", error.toException());
            }
        });


        luunhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            synchronized public void onClick(View v){
//                matkhaunhanvien = (EditText) v.findViewById(R.id.editText_matkhaunhanvien_profile);
//                tennhanvien = (EditText) v.findViewById(R.id.editText_tennhanvien_profile);
//                emailnhanvien = (EditText) v.findViewById(R.id.editText_email_profile);
//                sdtnhanvien = (EditText) v.findViewById(R.id.editText_sdt_profile);
//                chucvunhanvien = (EditText) v.findViewById(R.id.editText_chucvu_profile);
//                phongban = (TextView) v.findViewById(R.id.textView__phongban_profile2);
                Staff newStaff = new Staff(id, tennhanvien.getText().toString(), matkhaunhanvien.getText().toString(), phongban.getText().toString(),
                        emailnhanvien.getText().toString(), sdtnhanvien.getText().toString(), chucvunhanvien.getText().toString() );
                staffRef.setValue(newStaff);
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("idStaffLogin", id);
                intent.putExtra("chucvu", chucvunhanvien.getText().toString());
                startActivity(intent);
                getActivity().finish();
            }
        });


        logout = (ImageButton) view.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }
}