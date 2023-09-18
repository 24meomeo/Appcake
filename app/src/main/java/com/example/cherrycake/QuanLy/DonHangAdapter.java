package com.example.cherrycake.QuanLy;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.DonHang.HistoryOrderModel;
import com.example.cherrycake.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder>{
    Context context;
    List<HistoryOrderModel> list;

    private onClickButton onClickButton;

    FirebaseFirestore firestore;
    //    FirebaseAuth auth;
//    FirebaseUser user;
    String userID;

    public DonHangAdapter(Context context, List<HistoryOrderModel> list, onClickButton onClickButton) {
        this.context = context;
        this.list = list;
        this.onClickButton = onClickButton;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//        // nạp layout cho view biểu diễn phần từ gio hang item
//        View userView = inflater.inflate(R.layout.khachhangitem,parent,false);
//        //
//        ViewHolder viewHolder = new ViewHolder(userView);
//        return viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
//        firestore = FirebaseFirestore.getInstance();
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        userID = auth.getCurrentUser().getUid();
        HistoryOrderModel item = list.get(position);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        holder.madon.setText(String.valueOf(item.getMadonhang()));

        holder.makhach.setText(String.valueOf(item.getMakhachhang()));
//        holder.tongGia.setText(String.valueOf(item.getTongGia()));
//        holder.ngayMua.setText(item.getNgayMua());
//        holder.gioMua.setText(item.getThoigianMua());
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
        holder.btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton.onClickToDetail(item.getMadonhang(), item.getMakhachhang(), item.getTongGia(), item.getTongSoLuong(), item.getNgayMua(),
                        item.getThoigianMua(),
                        item.getTrangthai());
            }
        });
    }
    public interface onClickButton{
        void onClickToDetail(String madon, String makhach,int tonggia, int tongsoluong, String ngaymua, String giomua, int trangthai);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView madon;
        TextView trangthai;
        TextView makhach;
        Button btnChiTiet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            madon = itemView.findViewById(R.id.HistoryMaDon);
            makhach = itemView.findViewById(R.id.HistoryMaKhach);
            trangthai = itemView.findViewById(R.id.HistoryTrangThai);
            btnChiTiet = itemView.findViewById(R.id.btnChiTiet);

            trangthai.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            trangthai.setHorizontallyScrolling(true);
            trangthai.setMarqueeRepeatLimit(-1);
            trangthai.setSelected(true);
            trangthai.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        trangthai.post(new Runnable() {
                            @Override
                            public void run() {
                                if (trangthai.getWidth() < trangthai.getLayout().getLineWidth(0)) {
                                    trangthai.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                    trangthai.setHorizontallyScrolling(true);
                                    trangthai.setMarqueeRepeatLimit(-1);
                                    trangthai.setSelected(true);
                                } else {
                                    trangthai.setEllipsize(null);
                                    trangthai.setHorizontallyScrolling(false);
                                    trangthai.setMarqueeRepeatLimit(0);
                                    trangthai.setSelected(false);
                                }
                            }
                        });
                    }
                }
            });

            makhach.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            makhach.setHorizontallyScrolling(true);
            makhach.setMarqueeRepeatLimit(-1);
            makhach.setSelected(true);
            makhach.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        makhach.post(new Runnable() {
                            @Override
                            public void run() {
                                if (makhach.getWidth() < makhach.getLayout().getLineWidth(0)) {
                                    makhach.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                    makhach.setHorizontallyScrolling(true);
                                    makhach.setMarqueeRepeatLimit(-1);
                                    makhach.setSelected(true);
                                } else {
                                    makhach.setEllipsize(null);
                                    makhach.setHorizontallyScrolling(false);
                                    makhach.setMarqueeRepeatLimit(0);
                                    makhach.setSelected(false);
                                }
                            }
                        });
                    }
                }
            });


            madon.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            madon.setHorizontallyScrolling(true);
            madon.setMarqueeRepeatLimit(-1);
            madon.setSelected(true);
            madon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        madon.post(new Runnable() {
                            @Override
                            public void run() {
                                if (madon.getWidth() < madon.getLayout().getLineWidth(0)) {
                                    madon.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                    madon.setHorizontallyScrolling(true);
                                    madon.setMarqueeRepeatLimit(-1);
                                    madon.setSelected(true);
                                } else {
                                    madon.setEllipsize(null);
                                    madon.setHorizontallyScrolling(false);
                                    madon.setMarqueeRepeatLimit(0);
                                    madon.setSelected(false);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
    public void add(HistoryOrderModel SanPhamModel){
        list.add(SanPhamModel);
        notifyDataSetChanged();
    }
}
