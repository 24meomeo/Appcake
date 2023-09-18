package com.example.cherrycake.Home;

import android.content.Intent;
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
import com.example.cherrycake.GioHang.GioHangActivity;
import com.example.cherrycake.GioHang.MyCartModel;
import com.example.cherrycake.SanPham.ChiTietSPActivity;
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

public class ItemFavouAdapter extends RecyclerView.Adapter<ItemFavouAdapter.ItemViewHolder>{
    private ArrayList<ProductModel> aListItemFavou;
    private onClickItem onClickItemListener;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private int quantity = 1;


    public ItemFavouAdapter(ArrayList<ProductModel> aListItemFavou, onClickItem itemListener) {
        this.aListItemFavou = aListItemFavou;
        this.onClickItemListener = itemListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfavou_main,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        ProductModel item = aListItemFavou.get(position);
        if (item == null)
            return;
//        Glide.with(context).load(item.getItfvpict()).into(holder.aitfvpict);
        holder.aitfvname.setText(item.getName());
        holder.aitfvprice.setText(NumberFormat.getNumberInstance(Locale.US).format(item.getPrice())+" VNĐ");
        Glide.with(holder.aitfvpict.getContext()).load(item.getImage()).into(holder.aitfvpict);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickToDetail(item.getName(),item.getPrice(),item.getDescription(),item.getImage());
            }
        });
        holder.aitfvadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                Map<String, Object> cartMap = new HashMap<>();
                cartMap.put("name", item.getName());
                cartMap.put("price", item.getPrice());
                cartMap.put("totalQuantity", quantity);

                cartMap.put("anh", item.getImage());
                CollectionReference CartcollectionReference = firestore.collection("USERS")
                        .document(firebaseAuth.getCurrentUser().getUid()).collection("AddToCart");
                CartcollectionReference.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> collect = task.getResult().getDocuments().stream().
                                filter(s -> Objects.requireNonNull(s.toObject(MyCartModel.class))
                                        .name.contains(holder.aitfvname.getText().toString()))
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
//    public void changePrice(int quantity) {
//        int gia = quantity * getIntent().getIntExtra("gia",0);
//        String giatien = NumberFormat.getNumberInstance(Locale.US).format(gia);
//        tvPriceDetailC.setText(giatien + " VNĐ");
//        product.setPrice(getIntent().getIntExtra("gia",0));
//    }
    public interface onClickItem{
        void onClickToDetail(String name, int price, String description, String picture);
    }
    @Override
    public int getItemCount() {
        if(aListItemFavou != null){
            return aListItemFavou.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView aitfvname, aitfvprice;
        private ImageView aitfvpict;
        private ImageButton aitfvadd;
        private FirebaseFirestore firestore;
        private FirebaseAuth firebaseAuth;

        private CheckBox checkBoxFavourite;
        private CollectionReference favouriteList = FirebaseFirestore.getInstance().collection("FAVOURITES");
        private ProductModel productModel;

        private String userID, quantity, totalQuantity;
//        final HashMap<String,Object> cartMap = new HashMap<>();
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            aitfvname = itemView.findViewById(R.id.itfvname);
            aitfvprice = itemView.findViewById(R.id.itfvprice);
            aitfvpict = itemView.findViewById(R.id.itfvpict);
            aitfvadd = itemView.findViewById(R.id.itfvadd);

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
//            favouriteList.whereEqualTo("image",anh)
//                    .whereEqualTo("user",user)
//                    .get()
//                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                        @Override
//                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                                checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
//                                checkBoxFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                                    @Override
//                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                        if(b) {
//                                            Toast.makeText(DetailActivity.this,"Xóa khỏi danh sách yêu thích",Toast.LENGTH_SHORT).show();
//                                            checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite);
//                                            removeFavourite(anh);
//                                        } else {
//                                            Toast.makeText(DetailActivity.this,"Thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
//                                            checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
//                                            addFavourite(user,ten,price,mota,loai,anh);
//                                        }
//                                    }
//                                });
//                            }
//
//                        }
//                    });
//            //
//
//
//            //nút favourite
//
//            checkBoxFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    if(b) {
//                        Toast.makeText(DetailActivity.this,"Thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
//                        checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
//                        addFavourite(user,ten,price,mota,loai,anh);
//                        //
//                    } else {
//                        Toast.makeText(DetailActivity.this,"Xóa khỏi danh sách yêu thích",Toast.LENGTH_SHORT).show();
//                        checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite);
//                        removeFavourite(anh);
//                        //
//                    }
//                }
//            });
//            aitfvadd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Map<String, Object> cartMap = new HashMap<>();
//                    cartMap.put("name", aitfvname.getText().toString());
//                    cartMap.put("price", aitfvprice.getText().toString());
//                    cartMap.put("totalQuantity", quantity);
////                    Glide.with(holder.aitfvpict.getContext()).load(item.getImage()).into(holder.aitfvpict);
//                    cartMap.put("anh", aitfvpict.getContext().toString());
//                    CollectionReference CartcollectionReference = firestore.collection("AddToCart").document(userID).collection("User");
//                    CartcollectionReference.add(cartMap);
//                }
//            });
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
    public void add(ProductModel itemFavou) {
        aListItemFavou.add(itemFavou);
        notifyDataSetChanged();
    }
}
