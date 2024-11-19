package fpl.quangnm.duanmau_ph39820.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import fpl.quangnm.duanmau_ph39820.R;
import fpl.quangnm.duanmau_ph39820.dao.ThongKeDAO;


public class ThongKeDoanhThuFragment extends Fragment {
    EditText edt_tkStart,edt_tkEnd;
    Button btnThongKe;
    TextView tv_tienThongKe;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_doanh_thu, container, false);
        edt_tkStart = view.findViewById(R.id.edt_tkStart);
        edt_tkEnd = view.findViewById(R.id.edt_tkEnd);
        btnThongKe = view.findViewById(R.id.btnThongKe);
        tv_tienThongKe = view.findViewById(R.id.tv_tienThongKe);

        Calendar calendar = Calendar.getInstance();

        edt_tkEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String ngay = "";
                                String thang = "";
                                if (i2 < 10){
                                    ngay = "0" + i2;
                                } else {
                                    ngay =String.valueOf(i2);
                                }

                                if ((i1+1)<10){
                                    thang = "0" + (i1+1);
                                } else {
                                    thang = String.valueOf(i1+1);
                                }
                                edt_tkEnd.setText(ngay+"/"+thang+"/"+i);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        edt_tkStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String ngay = "";
                                String thang = "";
                                if (i2 < 10){
                                    ngay = "0" + i2;
                                } else {
                                    ngay =String.valueOf(i2);
                                }

                                if ((i1+1)<10){
                                    thang = "0" + (i1+1);
                                } else {
                                    thang = String.valueOf(i1+1);
                                }
                                edt_tkStart.setText(ngay+"/"+thang+"/"+i);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
                String ngaybatdau = edt_tkStart.getText().toString();
                String ngayketthuc = edt_tkEnd.getText().toString();
                int  doanhthu = thongKeDAO.getDoanhThu(ngaybatdau,ngayketthuc);
                tv_tienThongKe.setText(doanhthu + "VNĐ");
            }
        });

        return view;
    }
}