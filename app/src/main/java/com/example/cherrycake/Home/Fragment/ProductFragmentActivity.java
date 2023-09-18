package com.example.cherrycake.Home.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.SanPham.ChiTietSPActivity;
import com.example.cherrycake.SanPham.ProductModel;
import com.example.cherrycake.SanPham.ProductGridAdapter;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ProductFragmentActivity extends Fragment implements ProductGridAdapter.ProductCallback {
    RecyclerView rvListC;
    ArrayList<ProductModel> lstProductModel;
    ProductGridAdapter productGridAdapter;
    DrawerLayout mdrawMenuPro;
    GridLayoutManager gridLayoutManager= new GridLayoutManager(ProductFragmentActivity.this.getActivity(),2);
    FloatingActionButton mFloatButtonPro;
    LinearLayout mcake, mcandy, mcupcake, mcroissant;
    ImageView mcakeimg, mcandyimg, mcupcakeimg, mcroissantimg;
    TextView mcaketv, mcandytv, mcupcaketv, mcroissanttv;
    View v;
    int selectedMenu = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_product_activity, container, false);
        rvListC = v.findViewById(R.id.rvGrid);
        LoadData();

        mcake = v.findViewById(R.id.cake);
        mcandy = v.findViewById(R.id.candy);
        mcupcake = v.findViewById(R.id.cupcake);
        mcroissant = v.findViewById(R.id.croissant);

        mcakeimg = v.findViewById(R.id.cakeimg);
        mcandyimg = v.findViewById(R.id.candyimg);
        mcupcakeimg = v.findViewById(R.id.cupcakeimg);
        mcroissantimg = v.findViewById(R.id.croissantimg);

        mcaketv = v.findViewById(R.id.caketv);
        mcandytv = v.findViewById(R.id.candytv);
        mcupcaketv = v.findViewById(R.id.cupcaketv);
        mcroissanttv = v.findViewById(R.id.croissanttv);


        mcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm nếu mặc định for u đã được chọn hay chưa
                if(selectedMenu != 1){

                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
                    mcandytv.setVisibility(View.GONE);
                    mcupcaketv.setVisibility(View.GONE);
                    mcroissanttv.setVisibility(View.GONE);

                    mcandyimg.setImageResource(R.drawable.candy);
                    mcupcakeimg.setImageResource(R.drawable.cupcake);
                    mcroissantimg.setImageResource(R.drawable.croissant);

//                    mcandy.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.bgroundnotselect));
//                    mcupcake.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.bgroundnotselect));
//                    mcroissant.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.bgroundnotselect));

                    mcandy.setBackgroundResource(R.drawable.roundnotselect);
                    mcupcake.setBackgroundResource(R.drawable.roundnotselect);
                    mcroissant.setBackgroundResource(R.drawable.roundnotselect);
                    //Chọn menu cake
                    mcaketv.setVisibility(View.VISIBLE);
                    mcakeimg.setImageResource(R.drawable.cakeselected);
                    mcake.setBackgroundResource(R.drawable.roundcake);

                    //Tạo hiệu ứng cho cake
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    mcake.startAnimation(scaleAnimation);

                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
                    selectedMenu = 1;

                    //Load sản phẩm Cake
                    LoadDataMenuBanhKem();
                }
            }
        });
        mcandy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedMenu != 2){

                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
                    mcaketv.setVisibility(View.GONE);
                    mcupcaketv.setVisibility(View.GONE);
                    mcroissanttv.setVisibility(View.GONE);

                    mcakeimg.setImageResource(R.drawable.cake);
                    mcupcakeimg.setImageResource(R.drawable.cupcake);
                    mcroissantimg.setImageResource(R.drawable.croissant);

//                    mcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcupcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcroissant.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
                    mcake.setBackgroundResource(R.drawable.roundnotselect);
                    mcupcake.setBackgroundResource(R.drawable.roundnotselect);
                    mcroissant.setBackgroundResource(R.drawable.roundnotselect);


                    //Chọn menu candy
                    mcandytv.setVisibility(View.VISIBLE);
                    mcandyimg.setImageResource(R.drawable.candyselected);
                    mcandy.setBackgroundResource(R.drawable.roundcandy);

                    //Tạo hiệu ứng cho candy
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    mcandy.startAnimation(scaleAnimation);

                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
                    selectedMenu = 2;

                    //Load sản phẩm Candy
                    LoadDataMenuKeo();
                }
            }
        });
        mcupcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedMenu != 3){

                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
                    mcaketv.setVisibility(View.GONE);
                    mcandytv.setVisibility(View.GONE);
                    mcroissanttv.setVisibility(View.GONE);

                    mcakeimg.setImageResource(R.drawable.cake);
                    mcandyimg.setImageResource(R.drawable.candy);
                    mcroissantimg.setImageResource(R.drawable.croissant);

//                    mcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcandy.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcroissant.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
                    mcake.setBackgroundResource(R.drawable.roundnotselect);
                    mcandy.setBackgroundResource(R.drawable.roundnotselect);
                    mcroissant.setBackgroundResource(R.drawable.roundnotselect);
                    //Chọn menu candy
                    mcupcaketv.setVisibility(View.VISIBLE);
                    mcupcakeimg.setImageResource(R.drawable.cupcakeselected);
                    mcupcake.setBackgroundResource(R.drawable.roundcupcake);

                    //Tạo hiệu ứng cho candy
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    mcupcake.startAnimation(scaleAnimation);

                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
                    selectedMenu = 3;

                    //Load sản phẩm cupcake
                    LoadDataMenuBanhNho();
                }
            }
        });

        //
//        LoadData();
//        mItemRcmAdapter = new ItemRcmAdapter(lstProduct, this);
//        productAdapter = new ProductAdapter(lstProduct,this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
//        rvListC.setAdapter(mItemRcmAdapter);
//        rvListC.setLayoutManager(linearLayoutManager);
        //

        mcroissant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedMenu != 4){

                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
                    mcaketv.setVisibility(View.GONE);
                    mcandytv.setVisibility(View.GONE);
                    mcupcaketv.setVisibility(View.GONE);

                    mcakeimg.setImageResource(R.drawable.cake);
                    mcandyimg.setImageResource(R.drawable.candy);
                    mcupcakeimg.setImageResource(R.drawable.cupcake);

//                    mcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcandy.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcupcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
                    mcake.setBackgroundResource(R.drawable.roundnotselect);
                    mcandy.setBackgroundResource(R.drawable.roundnotselect);
                    mcupcake.setBackgroundResource(R.drawable.roundnotselect);
                    //Chọn menu candy
                    mcroissanttv.setVisibility(View.VISIBLE);
                    mcroissantimg.setImageResource(R.drawable.croissantselected);
                    mcroissant.setBackgroundResource(R.drawable.roundcroissant);

                    //Tạo hiệu ứng cho candy
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    mcroissant.startAnimation(scaleAnimation);

                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
                    selectedMenu = 4;

                    //Load sản phẩm croissant
                    LoadDataMenuBanhMan();
                }
            }
        });


        // load menu
//        Menu();
//        mdrawMenuPro = v.findViewById(R.id.drawmenuPro);
//        NavigationView navigationView = v.findViewById(R.id.nav_menu);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ProductFragmentActivity.this.getActivity(), mdrawMenuPro, R.string.nav_menu_op, R.string.nav_menu_cl);
//        mdrawMenuPro.addDrawerListener(toggle);
//        toggle.syncState();
//
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.menubanhkem:
//                        Toast.makeText(ProductFragmentActivity.this.getActivity(), "asd", Toast.LENGTH_SHORT).show();
//                        LoadDataMenuBanhKem();
//                        return true;
//                    case R.id.menukeo:
//                        LoadDataMenuKeo();
//                        return true;
//                    case R.id.menubanhnho:
//                        LoadDataMenuBanhNho();
//                        return true;
//                    case R.id.menubanhman:
//                        LoadDataMenuBanhMan();
//                        mdrawMenuPro.closeDrawer(GravityCompat.START);
//                        return true;
//                    default: return true;
//                }
//            }
//        });

//        mFloatButtonPro = v.findViewById(R.id.btnFloatingProduct);
//        mFloatButtonPro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoadDataMenuBanhKem();
////                mdrawMenuPro.openDrawer(GravityCompat.START);
//            }
//        });
        return v;
    }
    void LoadDataMenuBanhKem(){
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
    void LoadDataMenuKeo(){
        lstProductModel = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("PRODUCTS").whereEqualTo("category","Kẹo")
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
    void LoadDataMenuBanhNho(){
        lstProductModel = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("PRODUCTS").whereEqualTo("category","Cupcake")
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
    void LoadDataMenuBanhMan(){
        lstProductModel = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("PRODUCTS").whereEqualTo("category","Bánh mặn")
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
        productGridAdapter = new ProductGridAdapter(lstProductModel, this);
        rvListC.setAdapter(productGridAdapter);
        rvListC.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemClick(String ten, int gia, String mota, String anh) {
        Intent i = new Intent(ProductFragmentActivity.this.getActivity(), ChiTietSPActivity.class);
        i.putExtra("ten",ten);
        i.putExtra("mota",mota);
        i.putExtra("gia",gia);
        i.putExtra("anh",anh);
        startActivity(i);
    }
//    void Menu(){
//        mdrawMenuPro = v.findViewById(R.id.drawmenuPro);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ProductFragmentActivity.this.getActivity(), mdrawMenuPro, R.string.nav_menu_op, R.string.nav_menu_cl);
//        mdrawMenuPro.addDrawerListener(toggle);
//        toggle.syncState();
//
//
//    }
}