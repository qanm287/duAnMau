package fpl.quangnm.duanmau_ph39820.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpl.quangnm.duanmau_ph39820.R;
import fpl.quangnm.duanmau_ph39820.adapter.ThanhVienAdapter;
import fpl.quangnm.duanmau_ph39820.dao.ThanhVienDAO;
import fpl.quangnm.duanmau_ph39820.model.ThanhVien;


public class QlThanhVienFragment extends Fragment {
    RecyclerView recyclerViewThanhVien;
    FloatingActionButton btnfloat_addThanhVien;
    ThanhVienDAO thanhVienDAO;
    ArrayList<ThanhVien> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_thanh_vien, container, false);
        recyclerViewThanhVien = view.findViewById(R.id.recyclerViewThanhVien);
        btnfloat_addThanhVien = view.findViewById(R.id.btnfloat_addThanhVien);

        thanhVienDAO = new ThanhVienDAO(getContext());
        loadData();

        btnfloat_addThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_thanhvien,null);
        builder.setView(view);

        EditText edt_namSinhThanhVien = view.findViewById(R.id.edt_namSinhThanhVien);
        EditText edt_hoTenThanhVien = view.findViewById(R.id.edt_hoTenThanhVien);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String hoten = edt_hoTenThanhVien.getText().toString();
                String namsinh = edt_namSinhThanhVien.getText().toString();

                boolean check = thanhVienDAO.themThanhVien(hoten,namsinh);
                if (check == true){
                    Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại! ", Toast.LENGTH_SHORT).show();
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

    public void loadData(){
        list = thanhVienDAO.getDSThanhVien();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewThanhVien.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter thanhVienAdapter = new ThanhVienAdapter(getContext(),list, thanhVienDAO);
        recyclerViewThanhVien.setAdapter(thanhVienAdapter);
    }
}