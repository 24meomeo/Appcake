package com.example.cherrycake.Home.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.SanPham.ChiTietSPActivity;
import com.example.cherrycake.SanPham.ProductModel;
import com.example.cherrycake.SanPham.ProductGridAdapter;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragmentActivity extends Fragment implements ProductGridAdapter.ProductCallback {
    RecyclerView rvListC;
    ArrayList<ProductModel> lstProductModel;
    ProductGridAdapter productGridAdapter;
    GridLayoutManager gridLayoutManager= new GridLayoutManager(FavouriteFragmentActivity.this.getActivity(),2);
    View v;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_favourite_activity, container, false);
        rvListC = v.findViewById(R.id.rvGrid);
        rvListC.setLayoutManager(gridLayoutManager);
        LoadData();
        return v;
    }
    void LoadData() {
        lstProductModel =new ArrayList<>();
        CollectionReference collectionReference = firestore.collection("FAVOURITES");
        collectionReference.whereEqualTo("user",userID).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot ds:dsList) {
                                    ProductModel productModel = ds.toObject(ProductModel.class);
                                    productGridAdapter.add(productModel);
                                }
                            }
                        });
//        FirebaseFirestore.getInstance()
//                .collection("FAVOURITES").document().get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
//                        for(DocumentSnapshot ds:dsList) {
//                            Product product = ds.toObject(Product.class);
//                            productGridAdapter.add(product);
//                        }
//                    }
//                });
        productGridAdapter = new ProductGridAdapter(lstProductModel, this);
        rvListC.setAdapter(productGridAdapter);
    }
    @Override
    public void onItemClick(String ten, int gia, String mota, String anh) {
        Intent i = new Intent(FavouriteFragmentActivity.this.getActivity(), ChiTietSPActivity.class);
        i.putExtra("ten",ten);
        i.putExtra("mota",mota);
        i.putExtra("gia",gia);
        i.putExtra("anh",anh);
        startActivity(i);
    }
}