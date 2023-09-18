package com.example.cherrycake.DonHang;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.R;

import java.util.List;

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder>{

    Context context;
    List<HistoryOrderModel> list;

    public HistoryOrderAdapter(Context context, List<HistoryOrderModel> list) {
        this.context = context;
        this.list = list;
    }

    public HistoryOrderAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // nạp layout cho view biểu diễn phần từ history order item
        View userView = inflater.inflate(R.layout.historyorderitem,parent,false);
        //
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryOrderModel item = list.get(position);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        holder.tongSoLuong.setText(String.valueOf(item.getTongSoLuong()));
        holder.tongGia.setText(String.valueOf(item.getTongGia()));
        holder.ngayMua.setText(item.getNgayMua());
        holder.gioMua.setText(item.getThoigianMua());
        int trangthai = item.getTrangthai();
        if(trangthai == 0){
            holder.trangthai.setText("Đang chờ");
            holder.trangthai.setTextColor(Color.parseColor("#F62D2B"));
        }else if(trangthai == 1){
            holder.trangthai.setText("Đã xác nhận");
            holder.trangthai.setTextColor(Color.parseColor("#088948"));
        }else if(trangthai == 2){
            holder.trangthai.setText("Đang làm bánh");
            holder.trangthai.setTextColor(Color.parseColor("#088948"));
        }else if(trangthai == 3){
            holder.trangthai.setText("Bánh đã có");
            holder.trangthai.setTextColor(Color.parseColor("#088948"));
        }else{
            holder.trangthai.setText("Từ chối");
            holder.trangthai.setTextColor(Color.parseColor("#DF0512"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tongSoLuong, tongGia, ngayMua, gioMua, trangthai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tongSoLuong = itemView.findViewById(R.id.HistoryTongSL);
            tongGia = itemView.findViewById(R.id.HistoryTongGia);
            ngayMua = itemView.findViewById(R.id.HistoryNgayMua);
            gioMua = itemView.findViewById(R.id.HistoryGioMua);
            trangthai = itemView.findViewById(R.id.HistoryTrangThai);
        }
    }
    public void add(HistoryOrderModel SanPhamModel){
        list.add(SanPhamModel);
        notifyDataSetChanged();
    }
}
