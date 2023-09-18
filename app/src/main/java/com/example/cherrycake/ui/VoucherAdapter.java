package com.example.cherrycake.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cherrycake.R;
import com.example.cherrycake.data.model.VoucherModel;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.ViewHolder> {

    Context context;
    List<VoucherModel> list;
    OnItemClickListener onItemClickListener;

    public VoucherAdapter(Context context, List<VoucherModel> list, OnItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = itemClickListener;
    }

    @NonNull
    @Override // hàm này dược sử dụng để tạo ra các View hiển thị
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // nạp layout cho view biểu diễn phần từ gio hang item
        View userView = inflater.inflate(R.layout.voucher_item, parent, false);
        //
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override     // pthuc này dùng để gán dữ liệu cho View
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VoucherModel item = list.get(position);
        Glide.with(context).load(item.getAnh()).into(holder.ivVoucherItem);
        holder.tvVoucherTitle.setText(item.getTitle());
        holder.tvVoucherDes.setText(item.getDescription());

        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivVoucherItem;
        TextView tvVoucherTitle, tvVoucherDes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivVoucherItem = itemView.findViewById(R.id.ivVoucherItem);
            tvVoucherTitle = itemView.findViewById(R.id.tvVoucherTitle);
            tvVoucherDes = itemView.findViewById(R.id.tvVoucherDes);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(VoucherModel voucherModel);
    }

}
