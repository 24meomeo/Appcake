package com.example.cherrycake.ui.chooseimage;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.data.model.Image;
import com.example.cherrycake.databinding.ItemChooseImageBinding;
import com.example.cherrycake.utils.Constants;

import java.util.List;

public class ChooseImageAdapter extends RecyclerView.Adapter<ChooseImageAdapter.ViewHolder> {
    List<Image> data = Constants.INSTANCE.getDEFAULT_IMAGE();
    OnItemClick itemClick = null;

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemChooseImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemChooseImageBinding binding;
        public ViewHolder(@NonNull ItemChooseImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Image image){
            binding.tvName.setText(image.getName());
            itemView.setOnClickListener(v -> {
                if (itemClick!= null){
                    itemClick.onClick(image);
                }
            });
        }
    }

    interface OnItemClick {
        public void onClick(Image image);
    }
}


