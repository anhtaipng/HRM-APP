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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskUserAdapter extends RecyclerView.Adapter <TaskUserAdapter.MyViewHolder> {

    private List<Task> userTask;
    private Context context;
    private MyViewHolder holder;
    private int position;
    private String idsStaffLogin;


    public TaskUserAdapter(List<Task> usertask, Context rcontext, String idStaffLogin){
        this.userTask = usertask;
        this.context = rcontext;
        this.idsStaffLogin = idStaffLogin;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_recycleview, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Task task = userTask.get(position);
        holder.taskName.setText(task.getTaskName());
        holder.taskDescription.setText((task.getDescription()));
        holder.taskImage.setImageResource(task.getPathImage());

    }

    @Override
    public int getItemCount() {
        return userTask.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView taskName, taskDescription;
        public ImageView taskImage;



        public MyViewHolder(View view){
            super(view);

            taskName = view.findViewById(R.id.task_name);
            taskDescription = view.findViewById(R.id.task_description);
            taskImage = view.findViewById(R.id.task_image);

            CardView card;
            card = view.findViewById(R.id.card_view);
            card.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    switch (taskName.getText().toString()) {
                        case "??i???m danh/ Ch???m c??ng": {
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), QuetQrActivity.class);
                            intent.putExtra("idStaffLogin", idsStaffLogin);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                        case "K???t qu??? ??i???m danh": {
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), TomTatDiemDanh.class);
                            intent.putExtra("idStaffLogin", idsStaffLogin);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                        case "Vi???t ????n xin ngh??? ph??p":{
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), donxinphep.class);
                            intent.putExtra("idStaffLogin", idsStaffLogin);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                        case "T??nh tr???ng ????n xin ph??p":{
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), TinhTrangDonXinPhepActivity.class);
                            intent.putExtra("idStaffLogin", idsStaffLogin);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                        case "T???o th??ng b??o": {
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), ThongBaoActivity.class);
                            intent.putExtra("idStaffLogin", idsStaffLogin);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                        case "Th???ng k?? b??o c??o":{
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), ThongKeBaoCaoActivity.class);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                        case "Th??m nh??n vi??n": {
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), ThemNhanVienActivity.class);
                            intent.putExtra("idStaffLogin", idsStaffLogin);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                        case "Th??ng tin nh??n vi??n":{
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), DanhBaNhanSuActivity.class);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                        case "Duy???t ????n ngh??? ph??p":{
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), DuyetDonXinPhepActivity.class);
                            intent.putExtra("idStaffLogin", idsStaffLogin);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                        case "X??a nh??n vi??n":{
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), XoaNhanVienActivity.class);
                            intent.putExtra("idStaffLogin", idsStaffLogin);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                        case "K???t qu??? ??i???m danh c??ng ty":{
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), TomTatDiemDanhHR.class);
                            intent.putExtra("idStaffLogin", idsStaffLogin);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                        case "T??nh tr???ng ????n xin ph??p c??ng ty":{
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Intent intent = new Intent(v.getContext(), TinhTrangDonXinPhepHRActivity.class);
                            intent.putExtra("idStaffLogin", idsStaffLogin);
                            activity.startActivity(intent);
                            //finish();
                            break;
                        }
                    }

                }
            });

        }





    }

}
