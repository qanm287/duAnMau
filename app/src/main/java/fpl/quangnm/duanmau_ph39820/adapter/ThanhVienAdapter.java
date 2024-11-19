package fpl.quangnm.duanmau_ph39820.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpl.quangnm.duanmau_ph39820.R;
import fpl.quangnm.duanmau_ph39820.dao.ThanhVienDAO;
import fpl.quangnm.duanmau_ph39820.model.ThanhVien;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list,ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_thanhvien,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_TVmatv.setText("Mã thành viên: "+list.get(position).getMatv());
        holder.tv_TVhoTen.setText("Tên thành viên: "+list.get(position).getHoten());
        holder.tv_TVNamSinh.setText("Năm sinh: "+list.get(position).getNamsinh());
        holder.img_edit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });
        holder.img_delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = thanhVienDAO.xoaThongTinThanhVien(list.get(holder.getAdapterPosition()).getMatv());
                if (check == 1){
                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    loadData();
                } else if (check == 0){
                    Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                } else if (check == -1){
                    Toast.makeText(context, "Thành viên tồn tại phiếu mượn nên k xóa được!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_TVmatv,tv_TVhoTen,tv_TVNamSinh;
        ImageView img_delete_tv,img_edit_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_TVNamSinh = itemView.findViewById(R.id.tv_TVNamSinh);
            tv_TVhoTen = itemView.findViewById(R.id.tv_TVhoTen);
            tv_TVmatv = itemView.findViewById(R.id.tv_TVmatv);
            img_delete_tv = itemView.findViewById(R.id.img_delete_tv);
            img_edit_tv = itemView.findViewById(R.id.img_edit_tv);
        }
    }
    private  void  showDialogUpdate(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_thanhvien,null);
        builder.setView(view);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tv_idadd_ThanhVien = view.findViewById(R.id.tv_idadd_ThanhVien);
        EditText edt_namSinhThanhVien = view.findViewById(R.id.edt_namSinhThanhVien);
        EditText edt_hoTenThanhVien = view.findViewById(R.id.edt_hoTenThanhVien);

        tv_idadd_ThanhVien.setText("Mã TV: " + thanhVien.getMatv());
        edt_hoTenThanhVien.setText(thanhVien.getHoten());
        edt_namSinhThanhVien.setText(thanhVien.getNamsinh());

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String hoten = edt_hoTenThanhVien.getText().toString();
                String namsinh = edt_namSinhThanhVien.getText().toString();
                int id = thanhVien.getMatv();

                boolean check = thanhVienDAO.capNhatThongTinThanhVien(id,hoten,namsinh);
                if (check == true){
                    Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(context, "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();
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
        list.clear();
        list = thanhVienDAO.getDSThanhVien();
        notifyDataSetChanged();
    }
}
