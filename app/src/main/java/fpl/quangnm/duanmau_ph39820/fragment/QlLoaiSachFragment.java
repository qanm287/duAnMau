package fpl.quangnm.duanmau_ph39820.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fpl.quangnm.duanmau_ph39820.R;
import fpl.quangnm.duanmau_ph39820.adapter.LoaiSachAdapter;
import fpl.quangnm.duanmau_ph39820.dao.LoaiSachDAO;
import fpl.quangnm.duanmau_ph39820.model.ItemClick;
import fpl.quangnm.duanmau_ph39820.model.LoaiSach;


public class QlLoaiSachFragment extends Fragment {
    RecyclerView recyclerViewLS;
    LoaiSachDAO loaiSachDAO;
    EditText edt_nhapLoaiSach;
    int maloai;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ql_loai_sach, container, false);
        recyclerViewLS = view.findViewById(R.id.recycViewLoaiSach);
        edt_nhapLoaiSach = view.findViewById(R.id.edt_nhapLoaiSach);
        Button btn_addLS = view.findViewById(R.id.btn_addLS);
        Button btn_editLS = view.findViewById(R.id.btn_editLS);
        loadData();

        btn_editLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenLoai = edt_nhapLoaiSach.getText().toString();
                LoaiSach loaiSach = new LoaiSach(maloai, tenLoai);
                if (loaiSachDAO.thayDoiLoaiSach(loaiSach)){
                    loadData();
                    edt_nhapLoaiSach.setText("");
                } else {
                    Toast.makeText(getContext(), "Thay doi thong tin khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });



        btn_addLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenloai = edt_nhapLoaiSach.getText().toString();
                if (loaiSachDAO.themLoaiSach(tenloai)){
                    loadData();
                    edt_nhapLoaiSach.setText("");
                    Toast.makeText(getContext(), "Them loai sach thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Them loai sach k thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    public void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewLS.setLayoutManager(linearLayoutManager);
        loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();
        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(getContext(), list, new ItemClick() {
            @Override
            public void onClickLoaiSach(LoaiSach loaiSach) {
                edt_nhapLoaiSach.setText(loaiSach.getTenloai());
                maloai = loaiSach.getId();
            }
        });
        recyclerViewLS.setAdapter(loaiSachAdapter);
    }
}