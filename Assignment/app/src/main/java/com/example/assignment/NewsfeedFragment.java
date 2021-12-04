package com.example.assignment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsfeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsfeedFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recycleview;
    private NoticeAdapter adapter;
    private List<Notice> noticeList;
    private List<Notice> outputList;

    private Spinner spinner;
    private String[] items = {"Bảng tin Công Ty", "Bảng tin phòng IT", "Bảng tin phòng kinh doanh", "Bảng tin phòng nhân sự"};
    private String bangtinduocchon;

    public NewsfeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsfeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsfeedFragment newInstance(String param1, String param2) {
        NewsfeedFragment fragment = new NewsfeedFragment();
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
    synchronized public View onCreateView(LayoutInflater inflater , ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        recycleview = view.findViewById(R.id.recycler_view_bangtin);

        //xu ly bat su kien thay doi spinner
        spinner = (Spinner) view.findViewById(R.id.spinner_bangtin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            synchronized public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bangtinduocchon = spinner.getSelectedItem().toString();
                noticeList = new ArrayList<Notice>();
                outputList = new ArrayList<Notice>();
                FirebaseApp.initializeApp(view.getContext());
                DatabaseReference noticeRef = FirebaseDatabase.getInstance().getReference("notice");

                noticeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    synchronized public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot noticeSnapshot : dataSnapshot.getChildren()) {
                            Notice temp = noticeSnapshot.getValue(Notice.class);
                            if (temp.getBangtin().equals(bangtinduocchon)) {
                                noticeList.add(temp);
                            }
                        }
                        Collections.reverse(noticeList);
                        int length = Math.min(10, noticeList.size());
                        for (int i=0;i<length;i++)
                            outputList.add(noticeList.get(i));
                        adapter = new NoticeAdapter(outputList);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        recycleview.setAdapter(adapter);
                        recycleview.setNestedScrollingEnabled(false);
                        recycleview.setLayoutManager(mLayoutManager);
                        recycleview.hasFixedSize();
                        recycleview.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
                            @Override
                            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(view.getContext(), "Loading More ...",
                                                Toast.LENGTH_SHORT).show();
                                        int m = Math.min(adapter.getItemCount() + 10, noticeList.size());
                                        for (int i = adapter.getItemCount(); i < m; i++) {
                                            outputList.add(noticeList.get(i));
                                        }
                                        adapter = new NoticeAdapter(outputList);
                                        recycleview.setAdapter(adapter);
                                    }
                                }, 1500);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> aa = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, items);
        // provide a particular design for the drop-down lines
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // associate GUI spinner and adapter
        spinner.setAdapter(aa);

        return view;
    }


}