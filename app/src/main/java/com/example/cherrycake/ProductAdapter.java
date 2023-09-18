package com.example.cherrycake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cherrycake.SanPham.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    ArrayList<ProductModel> lstProductModel;
    Context context;
    ProductCallback productCallBack;


    public ProductAdapter(ArrayList<ProductModel> lstProductModel, ProductCallback productCallBack) {
        this.lstProductModel = lstProductModel;
        this.productCallBack = productCallBack;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Nạp layout cho View biểu diễn phần tử user
        View userView = inflater.inflate(R.layout.itemrcm_main, parent, false);
        //
        ProductViewHolder viewHolder = new ProductViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // lấy item của dữ liệu
        ProductModel item = lstProductModel.get(position);
        // gán item  của view
        holder.textViewNameProduct.setText(item.getName());

        String price = NumberFormat.getNumberInstance(Locale.US).format(item.getPrice());
        holder.textViewPriceProduct.setText(price + " VNĐ");
        Glide.with(context).load(item.getImage())
                .into(holder.imageViewProduct);
        // lấy sự kiện
        holder.itemView.setOnClickListener(view -> productCallBack.onItemClick(item.getUser(),item.getName(),item.getPrice(),item.getDescription(),item.getCategory(),item.getImage()));
    }

    @Override
    public int getItemCount() {
        return lstProductModel.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewNameProduct, textViewPriceProduct, textViewDescriptionProduct;
        private ImageView imageViewProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameProduct = itemView.findViewById(R.id.itrcmName);
            textViewPriceProduct = itemView.findViewById(R.id.itrcmPrice);
            imageViewProduct = itemView.findViewById(R.id.itrcmPict);
        }
    }

    public interface ProductCallback {
        void onItemClick(String nguoidung,String ten,int gia,String mota,String loai,String anh);
    }
    public void add(ProductModel productModel) {
        lstProductModel.add(productModel);
        notifyDataSetChanged();
    }
}
