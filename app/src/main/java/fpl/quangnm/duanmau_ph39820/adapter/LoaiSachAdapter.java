package fpl.quangnm.duanmau_ph39820.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpl.quangnm.duanmau_ph39820.R;
import fpl.quangnm.duanmau_ph39820.dao.LoaiSachDAO;
import fpl.quangnm.duanmau_ph39820.model.ItemClick;
import fpl.quangnm.duanmau_ph39820.model.LoaiSach;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LoaiSach> list ;
    private ItemClick itemClick;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_loaisach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_LSMaLoai.setText("Mã loại: "+ String.valueOf(list.get(position).getId()));
        holder.txt_LSTenLoai.setText("Tên loại: "+list.get(position).getTenloai());
        holder.img_deleteLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                int  check = loaiSachDAO.xoaLoaiSach(list.get(holder.getAdapterPosition()).getId());
                if (check == 1){
                    //load data
                    list.clear();
                    list = loaiSachDAO.getDSLoaiSach();
                    notifyDataSetChanged();
                } else if (check == -1){
                    Toast.makeText(context, "Khong the xoa loai sach nay vi sach dang ton tai", Toast.LENGTH_SHORT).show();
                } else if (check == 0){
                    Toast.makeText(context, "Xoa khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.img_editLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaiSach loaiSach = list.get(holder.getAdapterPosition());
                itemClick.onClickLoaiSach(loaiSach);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_LSTenLoai,txt_LSMaLoai;
        ImageView  img_deleteLS,img_editLS;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_LSTenLoai = itemView.findViewById(R.id.txt_LSTenLoai);
            txt_LSMaLoai = itemView.findViewById(R.id.txt_LSMaLoai);
            img_deleteLS = itemView.findViewById(R.id.img_deleteLS);
            img_editLS = itemView.findViewById(R.id.img_editLS);
        }
    }

}
