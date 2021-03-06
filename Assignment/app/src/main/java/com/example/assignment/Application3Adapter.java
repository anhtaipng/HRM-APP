package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class Application3Adapter extends RecyclerView.Adapter <Application3Adapter.MyViewHolderApplication> {
    private List<Application> appList;

    public Application3Adapter(List<Application> appList) {
        this.appList = appList;
    }

    @Override
    public Application3Adapter.MyViewHolderApplication onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hr_tinhtrangdonxinphep_recycleview, parent, false);
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
                holder.tennguoivietdon.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        //String lydo = application.getReason().equals("yte") ? "Ngh??? ph??p y t???" : application.getReason().equals("hangnam") ? "Ngh??? ph??p h???ng n??m" : "L?? do kh??c";
        String tinhtrang = application.getState().equals("accept") ? "Ch???p thu???n" : application.getState().equals("reject") ? "T??? ch???i" : "??ang ch???";
        int src = application.getState().equals("accept") ? R.drawable.accept : application.getState().equals("reject") ? R.drawable.reject
                : R.drawable.pending;
        holder.thoigiannghi.setText("Th???i gian xin ngh???:\n" + application.getDateStart() + " ?????n " + application.getDateEnd());
        holder.tinhtrang.setText(tinhtrang);
        holder.anh.setImageResource(src);
        holder.idnguoiduyetdon.setText(application.getIdSupervisor());
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }


    public class MyViewHolderApplication extends RecyclerView.ViewHolder {
        public TextView tinhtrang, thoigiannghi, tennguoivietdon, idnguoiduyetdon;
        private ImageView anh;
        public Context mContext;

        public MyViewHolderApplication(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            tinhtrang = itemView.findViewById(R.id.card_view_tinhtrangdonxinphep_tinhtrang);
            thoigiannghi = itemView.findViewById(R.id.card_view_tinhtrangdonxinphep_thoigiannghi);
            anh = itemView.findViewById(R.id.image_view_tinhtrangdonxinphep);
            tennguoivietdon = itemView.findViewById(R.id.card_view_tinhtrangdonxinphep_tennguoivietdon);
            idnguoiduyetdon = itemView.findViewById(R.id.card_view_tinhtrangdonxinphep_idnguoiduyetdon);
        }
    }
}
