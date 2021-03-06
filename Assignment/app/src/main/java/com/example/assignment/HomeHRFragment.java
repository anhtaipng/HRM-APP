package com.example.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeHRFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeHRFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private List<Task> taskList;
    private TaskUserAdapter mAdapter;

    private String idStaffLogin;

    public HomeHRFragment() {
    }

    public HomeHRFragment(String idStaff) {
        // Required empty public constructor
        this.idStaffLogin = idStaff;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeHRFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeHRFragment newInstance(String param1, String param2) {
        HomeHRFragment fragment = new HomeHRFragment("none");
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        taskList = new ArrayList<Task>();
        taskList.add(new Task("Th??m nh??n vi??n", R.drawable.task_themnhanvien, "Th??m nh??n vi??n v?? ch???p ???nh l??m th??? nh??n vi??n"));
        taskList.add(new Task("X??a nh??n vi??n", R.drawable.task_xoanhanvien, "X??a t??i kho???n c???a nh??n vi??n b??? cho th??i vi???c"));
        taskList.add(new Task("T??nh tr???ng ????n xin ph??p c??ng ty",R.drawable.task_tinhtrangdonxinphep,
                "Ki???m tra, theo d??i t??nh tr???ng c??c ????n xin ph??p c???a to??n b??? c??ng ty"));
        taskList.add(new Task("K???t qu??? ??i???m danh c??ng ty", R.drawable.task_tomtat,
                "Theo d??i k???t qu??? ??i???m danh c???a to??n b??? nh??n vi??n trong c??ng ty."));
        taskList.add(new Task("T???o th??ng b??o", R.drawable.task_thongbao, "T???o th??ng b??o tr??n b???ng tin"));
        taskList.add(new Task("Th???ng k?? b??o c??o", R.drawable.task_baocao, "Th???ng k??, b??o c??o t??nh tr???ng, s??? l?????ng nh??n vi??n..."));
        taskList.add(new Task("Th??ng tin nh??n vi??n", R.drawable.task_truyxuatthongtin, "Truy xu???t th??ng tin nh??n vi??n c???a b???n"));

        mAdapter = new TaskUserAdapter(taskList, getActivity(), idStaffLogin);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(mLayoutManager);

        return view;
    }
}