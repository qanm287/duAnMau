package fpl.quangnm.duanmau_ph39820.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fpl.quangnm.duanmau_ph39820.R;
import fpl.quangnm.duanmau_ph39820.adapter.Top10Adapter;
import fpl.quangnm.duanmau_ph39820.dao.ThongKeDAO;
import fpl.quangnm.duanmau_ph39820.model.Sach;


public class ThongKeTop10Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_top10, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTop10);
        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
        ArrayList<Sach> list  = thongKeDAO.getTop10();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Top10Adapter adapter = new Top10Adapter(getContext(),list);
        recyclerView.setAdapter(adapter);
        return view;
    }
}