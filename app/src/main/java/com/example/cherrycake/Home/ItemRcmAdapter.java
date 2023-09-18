package com.example.cherrycake.Home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cherrycake.GioHang.MyCartModel;
import com.example.cherrycake.SanPham.ProductModel;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemRcmAdapter extends RecyclerView.Adapter<ItemRcmAdapter.ItemViewHolder> {
    private ArrayList<ProductModel> aListItemRCM;
    private onClickItem onClickItemListener;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private int quantity = 1;

    public ItemRcmAdapter(ArrayList<ProductModel> aListItemRCM, onClickItem itemListener ) {
        this.aListItemRCM = aListItemRCM;
        this.onClickItemListener = itemListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrcm_main,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        ProductModel item = aListItemRCM.get(position);
        if (item == null)
            return;
//        Glide.with(context).load(item.getItfvpict()).into(holder.aitfvpict);
        holder.aitrcmname.setText(item.getName());
        holder.aitrcmprice.setText(NumberFormat.getNumberInstance(Locale.US).format(item.getPrice())+" VNĐ");
        Glide.with(holder.aitrcmpict.getContext()).load(item.getImage()).fitCenter().into(holder.aitrcmpict);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickToDetail(item.getName(),item.getPrice(),item.getDescription(),item.getImage());
            }
        });
        holder.aitrcmadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                Map<String, Object> cartMap = new HashMap<>();
                cartMap.put("name", item.getName());
                cartMap.put("price", item.getPrice());
                cartMap.put("totalQuantity", quantity);
//                    Glide.with(holder.aitfvpict.getContext()).load(item.getImage()).into(holder.aitfvpict);
//                    cartMap.put("anh", aitfvpict.getContext().toString());
                cartMap.put("anh", item.getImage());
                CollectionReference CartcollectionReference = firestore.collection("USERS").document(firebaseAuth.getCurrentUser().getUid()).collection("AddToCart");
//                CartcollectionReference.add(cartMap);
                CartcollectionReference.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> collect = task.getResult().getDocuments().stream().
                                filter(s -> Objects.requireNonNull(s.toObject(MyCartModel.class))
                                        .name.contains(holder.aitrcmname.getText().toString()))
                                .collect(Collectors.toList());

                        Log.d("Catr", "collect: " + collect.isEmpty());
                        if (!collect.isEmpty()) {
                            Log.d("Catr", "addToCart: " + collect.get(0).getId());
                            MyCartModel myCartModel = collect.get(0).toObject(MyCartModel.class);
                            myCartModel.setId(collect.get(0).getId());
                            myCartModel.totalQuantity = myCartModel.totalQuantity + /*Integer.parseInt(tvQuantityC.getText().toString())*/ quantity;
                            CartcollectionReference.document(myCartModel.id).set(myCartModel);
                            Toast.makeText(v.getContext(), "Cập nhật giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            CartcollectionReference.add(cartMap);
                            Toast.makeText(v.getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        }
//
//                        Intent i = new Intent(ChiTietSPActivity.this, GioHangActivity.class);
//                        startActivity(i);
                    }
                });
            }
        });
        holder.favouriteList.whereEqualTo("image",item.getImage())
                .whereEqualTo("user",holder.userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            holder.checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
                            holder.checkBoxFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    if(b) {
                                        Toast.makeText(compoundButton.getContext(), "Xóa khỏi danh sách yêu thích",Toast.LENGTH_SHORT).show();
                                        holder.checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite);
                                        holder.removeFavourite(item.getImage());
                                    } else {
                                        Toast.makeText(compoundButton.getContext(), "Thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
                                        holder.checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
                                        holder.addFavourite(holder.userID,item.getName(),item.getPrice(),item.getDescription(),item.getCategory(),item.getImage());
                                    }
                                }
                            });
                        }
                    }
                });

        holder.checkBoxFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    Toast.makeText(compoundButton.getContext(), "Thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
                    holder.checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
                    holder.addFavourite(holder.userID,item.getName(),item.getPrice(),item.getDescription(),item.getCategory(),item.getImage());
                    //
                } else {
                    Toast.makeText(compoundButton.getContext(), "Xóa khỏi danh sách yêu thích",Toast.LENGTH_SHORT).show();
                    holder.checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite);
                    holder.removeFavourite(item.getImage());
                    //
                }
            }
        });
    }
    public interface onClickItem{
        void onClickToDetail(String name, int price, String description, String picture);
    }
    @Override
    public int getItemCount() {
        if(aListItemRCM != null){
            return aListItemRCM.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView aitrcmname, aitrcmprice;
        private ImageView aitrcmpict;
        private ImageButton aitrcmadd;
        private FirebaseFirestore firestore;
        private FirebaseAuth firebaseAuth;

        private CheckBox checkBoxFavourite;
        private CollectionReference favouriteList = FirebaseFirestore.getInstance().collection("FAVOURITES");
        private ProductModel productModel;
        private String userID, quantity, totalQuantity;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            aitrcmname = itemView.findViewById(R.id.itrcmName);
            aitrcmprice = itemView.findViewById(R.id.itrcmPrice);
            aitrcmpict = itemView.findViewById(R.id.itrcmPict);
            aitrcmadd = itemView.findViewById(R.id.itrcmAdd);

            firestore = FirebaseFirestore.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
            userID = firebaseAuth.getCurrentUser().getUid();

            checkBoxFavourite = itemView.findViewById(R.id.itfvheart);

//            quantity = "1";
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            aitrcmadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        public void addFavourite(String nguoidung,String ten,int gia,String mota,String loai,String anh) {
            productModel = new ProductModel(nguoidung,ten,mota,loai,anh,gia);
            favouriteList.add(productModel);
        }
        // Xóa khỏi danh sách yêu thích //
        public void removeFavourite(String anh) {
            favouriteList.whereEqualTo("image",anh)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            String documentId = "";

                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                documentId = documentSnapshot.getId();
                            }
                            favouriteList.document(documentId).delete();
                        }
                    });

        }
    }
    public void add(ProductModel itemRcm) {
        aListItemRCM.add(itemRcm);
        notifyDataSetChanged();
    }
}
