package com.example.cherrycake.SanPham;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DanhSachSPActivity extends AppCompatActivity implements ProductGridAdapter.ProductCallback {

    RecyclerView rvListC;
    ArrayList<ProductModel> lstProductModel;
    ProductGridAdapter productGridAdapter;
    GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
    DrawerLayout mdrawMenuPro;

    FloatingActionButton mFloatButtonPro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        rvListC = findViewById(R.id.rvGrid);
        LoadData();
        productGridAdapter = new ProductGridAdapter(lstProductModel, this);
        rvListC.setAdapter(productGridAdapter);
        rvListC.setLayoutManager(gridLayoutManager);


        // load menu
        mdrawMenuPro = findViewById(R.id.drawmenuPro);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mdrawMenuPro, R.string.nav_menu_op, R.string.nav_menu_cl);
        mdrawMenuPro.addDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = findViewById(R.id.nav_menu);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.menubanhkem:
//                        LoadDataMenu();
//                        return true;
//                    case R.id.menukeo:
//                        return true;
//                    case R.id.menubanhnho:
//                        return true;
//                    case R.id.menubanhman:
//                        return true;
//                }
//                return true;
//            }
//        });
//        mFloatButtonPro = findViewById(R.id.btnFloatingProduct);
//        mFloatButtonPro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mdrawMenuPro.openDrawer(GravityCompat.START);
//                LoadDataMenu();
//            }
//        });



    }
    void LoadDataMenu(){
        lstProductModel = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("PRODUCTS").whereEqualTo("category","Bánh kem")
                .get()
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

        productGridAdapter = new ProductGridAdapter(lstProductModel, this);
        rvListC.setAdapter(productGridAdapter);
        rvListC.setLayoutManager(gridLayoutManager);

    }
    void LoadData() {
        lstProductModel =new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("PRODUCTS")
                .get()
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
    }

    @Override
    public void onItemClick(String ten, int gia, String mota, String anh) {
        Intent i = new Intent(this, ChiTietSPActivity.class);
        i.putExtra("ten",ten);
        i.putExtra("mota",mota);
        i.putExtra("gia",gia);
        i.putExtra("anh",anh);
        startActivity(i);
    }
}