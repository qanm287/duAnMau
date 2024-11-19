package fpl.quangnm.duanmau_ph39820.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpl.quangnm.duanmau_ph39820.R;
import fpl.quangnm.duanmau_ph39820.model.Sach;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach> list;

    public Top10Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recyc_top10,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_TopMaSach.setText(String.valueOf(list.get(position).getMasach()));
        holder.txt_TopTenSach.setText(list.get(position).getTensach());
        holder.txt_TopSoLuong.setText(String.valueOf(list.get(position).getSoluongdamuon()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_TopMaSach,txt_TopTenSach,txt_TopSoLuong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_TopMaSach = itemView.findViewById(R.id.txt_TopMaSach);
            txt_TopTenSach = itemView.findViewById(R.id.txt_TopTenSach);
            txt_TopSoLuong = itemView.findViewById(R.id.txt_TopSoLuong);
        }
    }
}

