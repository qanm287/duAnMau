package fpl.quangnm.duanmau_ph39820.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import fpl.quangnm.duanmau_ph39820.R;
import fpl.quangnm.duanmau_ph39820.adapter.PhieuMuonAdapter;
import fpl.quangnm.duanmau_ph39820.dao.PhieuMuonDAO;
import fpl.quangnm.duanmau_ph39820.dao.SachDAO;
import fpl.quangnm.duanmau_ph39820.dao.ThanhVienDAO;
import fpl.quangnm.duanmau_ph39820.model.PhieuMuon;
import fpl.quangnm.duanmau_ph39820.model.Sach;
import fpl.quangnm.duanmau_ph39820.model.ThanhVien;

public class QlPhieuMuonFragment extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    RecyclerView recyclerViewQLPhieuMuon;
    FloatingActionButton floating_addPM;
    ArrayList<PhieuMuon> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_phieu_muon, container, false);
        recyclerViewQLPhieuMuon = view.findViewById(R.id.recyclerViewQLPhieuMuon);
        floating_addPM = view.findViewById(R.id.floating_addPM);
        loadData();

        floating_addPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }
    private void loadData(){
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.getDSPhieuMuon();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewQLPhieuMuon.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter phieuMuonAdapter = new PhieuMuonAdapter(list,getContext());
        recyclerViewQLPhieuMuon.setAdapter(null);
        phieuMuonAdapter.notifyDataSetChanged();
        recyclerViewQLPhieuMuon.setAdapter(phieuMuonAdapter);

    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_phieumuon,null);
        Spinner spn_addPMThanhVien = view.findViewById(R.id.spn_addPMThanhVien);
        Spinner spn_addPMSach = view.findViewById(R.id.spn_addPMSach);
        getDataThanhVien(spn_addPMThanhVien);
        getDataSach(spn_addPMSach);
        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // lay ma tv
                HashMap<String,Object> hsTV = (HashMap<String, Object>) spn_addPMThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("matv");
                // lay ma sach
                HashMap<String,Object> hsSach = (HashMap<String, Object>) spn_addPMSach.getSelectedItem();
                int masach = (int) hsSach.get("masach");
                int tien = (int) hsSach.get("giathue");
                themPM(matv,masach,tien);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog =builder.create();
        alertDialog.show();

    }
    private void getDataThanhVien(Spinner spnThanhVien){
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
        ArrayList<ThanhVien> list = thanhVienDAO.getDSThanhVien();
        ArrayList<HashMap<String, Object>> listHasmap = new ArrayList<>();

        for (ThanhVien tv: list){
            HashMap<String,Object> hs = new HashMap<>();
            hs.put("matv", tv.getMatv());
            hs.put("hoten",tv.getHoten());
            listHasmap.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHasmap,
                android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach){
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<Sach> list = sachDAO.getDSDauSach();
        ArrayList<HashMap<String, Object>> listHasmap = new ArrayList<>();

        for (Sach sc: list){
            HashMap<String,Object> hs = new HashMap<>();
            hs.put("masach", sc.getMasach());
            hs.put("tensach",sc.getTensach());
            hs.put("giathue",sc.getGiathue());
            listHasmap.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHasmap,
                android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }
    private void themPM(int matv,int masach,int tien){
        // lay matt
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("matt","");

        // lay ngay
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);

        PhieuMuon phieuMuon = new PhieuMuon(matv,matt, masach, ngay,0,tien);
        boolean check = phieuMuonDAO.themPhieuMuon(phieuMuon);
        if (check){
            Toast.makeText(getContext(), "Thêm phiếu mượn thành công!", Toast.LENGTH_SHORT).show();
            loadData();
        } else {
            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

}