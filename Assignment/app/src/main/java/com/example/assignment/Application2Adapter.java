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

public class Application2Adapter extends RecyclerView.Adapter <Application2Adapter.MyViewHolderApplication> {
    private List<Application> appList;

    public Application2Adapter(List<Application> appList) {
        this.appList = appList;
    }

    @Override
    public Application2Adapter.MyViewHolderApplication onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tinhtrangdonxinphep_recycleview, parent, false);
        return new MyViewHolderApplication(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderApplication holder, int position) {
        Application application = appList.get(position);

//        FirebaseApp.initializeApp(holder.mContext);
//        DatabaseReference staffNameRef = FirebaseDatabase.getInstance().getReference("staff").child(application.getIdStaffLogin()).child("staffName");
//        staffNameRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue().toString();
//                holder.tennhanvien.setText(value);
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//            }
//        });
        String lydo = application.getReason().equals("yte") ? "Nghỉ phép y tế" : application.getReason().equals("hangnam") ? "Nghỉ phép hằng năm" : "Lý do khác";
        String tinhtrang = application.getState().equals("accept") ? "Chấp thuận" : application.getState().equals("reject") ? "Từ chối" : "Đang chờ";
        int src = application.getState().equals("accept") ? R.drawable.accept : application.getState().equals("reject") ? R.drawable.reject
                : R.drawable.pending;
        holder.lydo.setText(lydo);
        holder.thoigiannghi.setText("Thời gian xin nghỉ:\n" + application.getDateStart() + " đến " + application.getDateEnd());
        holder.tinhtrang.setText(tinhtrang);
        holder.anh.setImageResource(src);
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }


    public class MyViewHolderApplication extends RecyclerView.ViewHolder {
        public TextView lydo, tinhtrang, thoigiannghi;
        private ImageView anh;
        //public Context mContext;

        public MyViewHolderApplication(@NonNull View itemView) {
            super(itemView);
            //mContext = itemView.getContext();
            lydo = itemView.findViewById(R.id.card_view_tinhtrangdonxinphep_lydo);
            tinhtrang = itemView.findViewById(R.id.card_view_tinhtrangdonxinphep_tinhtrang);
            thoigiannghi = itemView.findViewById(R.id.card_view_tinhtrangdonxinphep_thoigiannghi);
            anh = itemView.findViewById(R.id.image_view_tinhtrangdonxinphep);

        }
    }
}
