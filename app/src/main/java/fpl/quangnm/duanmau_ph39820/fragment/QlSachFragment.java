package fpl.quangnm.duanmau_ph39820.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import fpl.quangnm.duanmau_ph39820.R;
import fpl.quangnm.duanmau_ph39820.adapter.SachAdapter;
import fpl.quangnm.duanmau_ph39820.dao.LoaiSachDAO;
import fpl.quangnm.duanmau_ph39820.dao.SachDAO;
import fpl.quangnm.duanmau_ph39820.model.LoaiSach;
import fpl.quangnm.duanmau_ph39820.model.Sach;

public class QlSachFragment extends Fragment {
    RecyclerView recyclerViewSach;
    FloatingActionButton floating_addSach;
    SachDAO sachDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_ql_sach, container, false);
        recyclerViewSach = view.findViewById(R.id.recyclerViewSach);
        floating_addSach = view.findViewById(R.id.floating_addSach);

        sachDAO = new SachDAO(getContext());
        loadData();

        floating_addSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

       return view;
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sach,null);
        builder.setView(view);

        EditText edt_add_tensach = view.findViewById(R.id.edt_add_tensach);
        EditText edt_add_tien = view.findViewById(R.id.edt_add_tien);
        Spinner spn_loaisach = view.findViewById(R.id.spn_loaisach);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spn_loaisach.setAdapter(simpleAdapter);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tensach = edt_add_tensach.getText().toString();
                int tien = Integer.parseInt(edt_add_tien.getText().toString());
                HashMap<String,Object> hs = (HashMap<String, Object>) spn_loaisach.getSelectedItem();
                int maloai = (int) hs.get("maloai");  // nos laf hashmap

                boolean check = sachDAO.themSachMoi(tensach,tien,maloai);
                if (check == true){
                    Toast.makeText(getContext(), "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(getContext(), "Thêm sách thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void loadData(){
        ArrayList<Sach> list = sachDAO.getDSDauSach();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewSach.setLayoutManager(linearLayoutManager);
        SachAdapter sachAdapter = new SachAdapter(getContext(),list, getDSLoaiSach(),sachDAO);  // spinner
        recyclerViewSach.setAdapter(sachAdapter);
    }
    private ArrayList<HashMap<String,Object>> getDSLoaiSach(){
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();
        ArrayList<HashMap<String,Object>> listHm = new ArrayList<>();

        for (LoaiSach loai : list){
            HashMap<String,Object> hs = new HashMap<>();
            hs.put("maloai", loai.getId());
            hs.put("tenloai", loai.getTenloai());
            listHm.add(hs);
        }
        return listHm;
    }

}