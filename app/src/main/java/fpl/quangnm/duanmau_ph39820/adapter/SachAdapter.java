package fpl.quangnm.duanmau_ph39820.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import fpl.quangnm.duanmau_ph39820.R;
import fpl.quangnm.duanmau_ph39820.dao.SachDAO;
import fpl.quangnm.duanmau_ph39820.model.Sach;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach> list;
    private ArrayList<HashMap<String,Object>> listHM;   // spinner
    private SachDAO sachDAO;    //chuc nang sua

    public SachAdapter(Context context, ArrayList<Sach> list, ArrayList<HashMap<String,Object>> listHM,SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.sachDAO =sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_sach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSach_maSach.setText("Mã sách: "+list.get(position).getMasach());
        holder.tvSach_tenSach.setText("Tên sách: "+list.get(position).getTensach());
        holder.tvSach_GiaThue.setText("Giá thuê: "+list.get(position).getGiathue());
        holder.tvSach_maLoai.setText("Mã loại: "+list.get(position).getMaloai());
        holder.tvSach_tenLoai.setText("Tên loại: "+list.get(position).getTenloai());

        holder.img_editS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(list.get(holder.getAdapterPosition()));
            }
        });
        holder.img_deleteS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMasach());
                if (check == 1){
                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    loadData();
                } else if(check == 0){
                    Toast.makeText(context, "Xóa không thành công!", Toast.LENGTH_SHORT).show();
                } else if (check == -1){
                    Toast.makeText(context, "Xóa không thành công vì tồn tại loại sách!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvSach_maSach,tvSach_tenSach,tvSach_GiaThue,tvSach_maLoai,tvSach_tenLoai;
        ImageView img_editS,img_deleteS;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSach_maSach = itemView.findViewById(R.id.tvSach_maSach);
            tvSach_tenSach = itemView.findViewById(R.id.tvSach_tenSach);
            tvSach_GiaThue = itemView.findViewById(R.id.tvSach_GiaThue);
            tvSach_maLoai = itemView.findViewById(R.id.tvSach_maLoai);
            tvSach_tenLoai = itemView.findViewById(R.id.tvSach_tenLoai);
            img_editS = itemView.findViewById(R.id.img_editS);
            img_deleteS = itemView.findViewById(R.id.img_deleteS);
        }
    }
    private void showDialog(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_sach,null);
        builder.setView(view);

        TextView tv_idedit_Sach = view.findViewById(R.id.tv_idedit_Sach);
        EditText edt_edit_tensach = view.findViewById(R.id.edt_edit_tensach);
        EditText edt_edt_tien = view.findViewById(R.id.edt_edt_tien);
        Spinner spn_edit_loaisach = view.findViewById(R.id.spn_edit_loaisach);

        tv_idedit_Sach.setText("Mã sách: "+sach.getMasach());
        edt_edit_tensach.setText(sach.getTensach());
        edt_edt_tien.setText(String.valueOf(sach.getGiathue()));
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spn_edit_loaisach.setAdapter(simpleAdapter);

        int index = 0;
        int position = -1;
        for (HashMap<String,Object> item : listHM){
            if ((int)item.get("maloai")==sach.getMaloai()){
                position = index;
            }
            index ++;
        }
        spn_edit_loaisach.setSelection(position);



        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tensach = edt_edit_tensach.getText().toString();
                int tien = Integer.parseInt(edt_edt_tien.getText().toString());
                HashMap<String,Object> hs = (HashMap<String, Object>) spn_edit_loaisach.getSelectedItem();
                int maloai = (int) hs.get("maloai");  // nos laf hashmap

                boolean check = sachDAO.capNhatThongTinSach(sach.getMasach(),tensach,tien,maloai);
                if (check == true){
                    Toast.makeText(context, "Sửa sách thành công!", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(context, "Sửa sách thất bại!", Toast.LENGTH_SHORT).show();
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
        list.clear();  // clear sach cu
        list = sachDAO.getDSDauSach();  // doi lai sach da sửa vào ds
        notifyDataSetChanged();
    }
}
