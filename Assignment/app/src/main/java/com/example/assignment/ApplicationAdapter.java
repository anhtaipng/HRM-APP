package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter <ApplicationAdapter.MyViewHolderApplication> {
    private List<Application> appList;

    public ApplicationAdapter(List<Application> appList) {
        this.appList = appList;
    }

    @Override
    public ApplicationAdapter.MyViewHolderApplication onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donxinphep_recycleview, parent, false);
        return new MyViewHolderApplication(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderApplication holder, int position) {
        Application application = appList.get(position);

        FirebaseApp.initializeApp(holder.mContext);
        DatabaseReference staffNameRef = FirebaseDatabase.getInstance().getReference("staff").child(application.getIdStaffLogin()).child("staffName");
        staffNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue().toString();
                holder.tennhanvien.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        holder.idnhanvien.setText("ID: " + application.getIdStaffLogin());
        holder.thoigiannghi.setText("Thời gian xin nghỉ:\n" + application.getDateStart() + " đến " + application.getDateEnd());
        holder.iddonxinphep.setText(application.getIdApplication());
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }


    public class MyViewHolderApplication extends RecyclerView.ViewHolder {
        public TextView tennhanvien, idnhanvien, thoigiannghi, iddonxinphep;
        public CardView cardview;
        public Context mContext;

        public MyViewHolderApplication(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            tennhanvien = itemView.findViewById(R.id.card_view_donxinphep_tennhanvien);
            idnhanvien = itemView.findViewById(R.id.card_view_donxinphep_idnhanvien);
            thoigiannghi = itemView.findViewById(R.id.card_view_donxinphep_thoigiannghi);
            iddonxinphep = itemView.findViewById(R.id.card_view_donxinphep_iddonxinphep);

            cardview = itemView.findViewById(R.id.card_view_donxinphep);
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseApp.initializeApp(v.getContext());
                    DatabaseReference donxinphepRef = FirebaseDatabase.getInstance().getReference("donxinphep").child(iddonxinphep.getText().toString())
                            .child("idSupervisor");
                    donxinphepRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String idStaffLogin = snapshot.getValue(String.class);
                            Intent intent = new Intent(v.getContext(), DonXinPhepActivity.class);
                            intent.putExtra("idDonXinPhep", iddonxinphep.getText().toString());
                            intent.putExtra("idStaffLogin", idStaffLogin);
                            v.getContext().startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            });
        }
    }
}
