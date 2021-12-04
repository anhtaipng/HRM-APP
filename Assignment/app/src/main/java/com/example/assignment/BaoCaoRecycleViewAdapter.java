package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BaoCaoRecycleViewAdapter extends RecyclerView.Adapter<BaoCaoRecycleViewAdapter.MyViewHolder> {
    private AppCompatActivity appCompatActivity;
    private MyViewHolder holder;
    private List<Report> listReport;
    Context context;

    public BaoCaoRecycleViewAdapter(Context ct, List<Report> listReport) {
        this.context = ct;
        this.listReport = listReport;

    }

    @NonNull
    @Override
    public BaoCaoRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thong_ke_bao_cao_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Report report = listReport.get(position);
        holder.reportName.setText(report.getR_name());
        holder.reportDescription.setText(report.getR_description());
        holder.reportImage.setImageResource(report.getPathImage());
    }

    @Override
    public int getItemCount() {
        return listReport.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView reportName, reportDescription;
        public ImageView reportImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            reportName = itemView.findViewById(R.id.chart_name);
            reportDescription = itemView.findViewById(R.id.chart_description);
            reportImage = itemView.findViewById(R.id.chart_image);

            CardView card;
            card = itemView.findViewById(R.id.card_view);
            card.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), reportName.getText().toString(), Toast.LENGTH_LONG).show();
                switch (reportName.getText().toString()) {
                    case "Thành phần nhân sự": {
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        Intent intent = new Intent(v.getContext(), PieChartActivity.class);
                        activity.startActivity(intent);
                        break;
                    }
                    case "Số nhân viên đi làm": {
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        Intent intent = new Intent(v.getContext(), SoNhanVienDiLamActivity.class);
                        activity.startActivity(intent);
                        break;
                    }
                    case "Số lượng nhân viên nghỉ": {
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        Intent intent = new Intent(v.getContext(), NhanVienNghiPhepActivity.class);
                        activity.startActivity(intent);
                        break;
                    }
                    case "Report 3": {
                        break;
                    }

                }

            });


        }
    }
}
