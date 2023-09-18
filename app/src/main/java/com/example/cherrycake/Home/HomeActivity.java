package com.example.cherrycake.Home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.cherrycake.Home.Fragment.FavouriteFragmentActivity;
import com.example.cherrycake.Home.Fragment.HomeFragmentActivity;
import com.example.cherrycake.Home.Fragment.UserFragmentActivity;
import com.example.cherrycake.Home.Fragment.ProductFragmentActivity;
import com.example.cherrycake.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {
//    DrawerLayout mdrawLo;

//    RecyclerView rvListC;
//    ArrayList<Product> lstProduct;
//    ProductAdapter productAdapter;
//
//    ImageButton mimgbtImage, mimgbtAboutus, mimgbtFind, mimgbtCart, mimgbtDirect, mimgbtPhone;
//
//    LinearLayout mcake, mcandy, mcupcake, mcroissant;
//    ImageView mcakeimg, mcandyimg, mcupcakeimg, mcroissantimg;
//    TextView mcaketv, mcandytv, mcupcaketv, mcroissanttv;
//
//    TextView mtvaboutus,mtvViewAll1, mtvViewAll2;
//    RecyclerView mrcvFavouItem, mrcvRcmItem;
//    ItemFavouAdapter mItemFavouAdapter;
//    ItemRcmAdapter mItemRcmAdapter;
////    List<ItemFavou> itcandyfv, itcupcakefv, itcroissfv;
//    ArrayList<Product> itcakefv, itcakercm, itcandyfv, itcandyrcm, itcupcakefv, itcupcakercm, itcroissfv, itcroissrcm;
////    List<ItemRcm> itcandyrcm, itcupcakercm, itcroissrcm;
//    FirebaseFirestore firestoreHome;

    MeowBottomNavigation btnav;
//    int selectedMenu = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
//        rvListC = findViewById(R.id.rcvRcmItem);

//        getSupportActionBar().hide();

//        //Menu
//        mdrawLo = findViewById(R.id.drawLo);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mdrawLo, R.string.nav_menu_op,R.string.nav_menu_cl);
//        mdrawLo.addDrawerListener(toggle);
//        toggle.syncState();
//        mimgbtImage = findViewById(R.id.imgbtImage);
//        mimgbtImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                mdrawLo.openDrawer(GravityCompat.START);
//            }
//        });
//
//
//        mimgbtFind = findViewById(R.id.imgbtfind);
//        mimgbtCart = findViewById(R.id.imgbtcart);
//        mimgbtFind.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        //Trỏ tới giỏ hàng
//        mimgbtCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cartintent = new Intent(HomeActivity.this, GIoHangActivity.class);
//                startActivity(cartintent);
//            }
//        });
//
//        //Liên hệ
//        mimgbtDirect = findViewById(R.id.imgbtdirect);
//        mimgbtPhone = findViewById(R.id.imgbtphone);
//        mimgbtAboutus = findViewById(R.id.imgbtaboutus);
//        mtvaboutus = findViewById(R.id.tvaboutus);
//        mimgbtDirect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "https://maps.app.goo.gl/QZDpT5coQrjGzQFw6";
//                Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(map);
//            }
//        });
//        mimgbtPhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent callPhone = new Intent((Intent.ACTION_DIAL));
//                callPhone.setData(Uri.parse("tel: 0948369673"));
//                startActivity(callPhone);
//            }
//        });
//        //Nhấp vào test để trỏ tới giỏ hàng
//        mtvaboutus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                directToAboutUs();
//            }
//        });
//        //Nhấp vào icon để trỏ tới giỏ hàng
//        mimgbtAboutus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                directToAboutUs();
//            }
//        });
//
//
//        mcake = findViewById(R.id.cake);
//        mcandy = findViewById(R.id.candy);
//        mcupcake = findViewById(R.id.cupcake);
//        mcroissant = findViewById(R.id.croissant);
//
//        mcakeimg = findViewById(R.id.cakeimg);
//        mcandyimg = findViewById(R.id.candyimg);
//        mcupcakeimg = findViewById(R.id.cupcakeimg);
//        mcroissantimg = findViewById(R.id.croissantimg);
//
//        mcaketv = findViewById(R.id.caketv);
//        mcandytv = findViewById(R.id.candytv);
//        mcupcaketv = findViewById(R.id.cupcaketv);
//        mcroissanttv = findViewById(R.id.croissanttv);
//
//
//        LinearLayoutManager horizontalLayoutItManagerIT1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
//        mrcvFavouItem = findViewById(R.id.rcvFavouItem);
//        mrcvFavouItem.setLayoutManager(horizontalLayoutItManagerIT1);
//        LinearLayoutManager horizontalLayoutItManagerIT2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
//        mrcvRcmItem = findViewById(R.id.rcvRcmItem);
//        mrcvRcmItem.setLayoutManager(horizontalLayoutItManagerIT2);
//
//        //Vừa vào
//
//        loadItemHCakeData();
//
//
//        mcake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Kiểm nếu mặc định for u đã được chọn hay chưa
//                if(selectedMenu != 1){
//
//                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
//                    mcandytv.setVisibility(View.GONE);
//                    mcupcaketv.setVisibility(View.GONE);
//                    mcroissanttv.setVisibility(View.GONE);
//
//                    mcandyimg.setImageResource(R.drawable.candy);
//                    mcupcakeimg.setImageResource(R.drawable.cupcake);
//                    mcroissantimg.setImageResource(R.drawable.croissant);
//
//                    mcandy.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcupcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcroissant.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//
//                    //Chọn menu cake
//                    mcaketv.setVisibility(View.VISIBLE);
//                    mcakeimg.setImageResource(R.drawable.cakeselected);
//                    mcake.setBackgroundResource(R.drawable.roundcake);
//
//                    //Tạo hiệu ứng cho cake
//                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
//                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,0.0f);
//                    scaleAnimation.setDuration(200);
//                    scaleAnimation.setFillAfter(true);
//                    mcake.startAnimation(scaleAnimation);
//
//                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
//                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
//                    selectedMenu = 1;
//
//                    //Load sản phẩm Cake
//                    loadItemHCakeData();
//                }
//            }
//        });
//        mcandy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(selectedMenu != 2){
//
//                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
//                    mcaketv.setVisibility(View.GONE);
//                    mcupcaketv.setVisibility(View.GONE);
//                    mcroissanttv.setVisibility(View.GONE);
//
//                    mcakeimg.setImageResource(R.drawable.cake);
//                    mcupcakeimg.setImageResource(R.drawable.cupcake);
//                    mcroissantimg.setImageResource(R.drawable.croissant);
//
////                    mcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
////                    mcupcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
////                    mcroissant.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcake.setBackgroundResource(R.drawable.roundnotselect);
//                    mcupcake.setBackgroundResource(R.drawable.roundnotselect);
//                    mcroissant.setBackgroundResource(R.drawable.roundnotselect);
//
//
//                    //Chọn menu candy
//                    mcandytv.setVisibility(View.VISIBLE);
//                    mcandyimg.setImageResource(R.drawable.candyselected);
//                    mcandy.setBackgroundResource(R.drawable.roundcandy);
//
//                    //Tạo hiệu ứng cho candy
//                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
//                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
//                    scaleAnimation.setDuration(200);
//                    scaleAnimation.setFillAfter(true);
//                    mcandy.startAnimation(scaleAnimation);
//
//                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
//                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
//                    selectedMenu = 2;
//
//                    //Load sản phẩm Candy
//                    loadItemHCandyData();
//                }
//            }
//        });
//        mcupcake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(selectedMenu != 3){
//
//                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
//                    mcaketv.setVisibility(View.GONE);
//                    mcandytv.setVisibility(View.GONE);
//                    mcroissanttv.setVisibility(View.GONE);
//
//                    mcakeimg.setImageResource(R.drawable.cake);
//                    mcandyimg.setImageResource(R.drawable.candy);
//                    mcroissantimg.setImageResource(R.drawable.croissant);
//
////                    mcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
////                    mcandy.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
////                    mcroissant.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcake.setBackgroundResource(R.drawable.roundnotselect);
//                    mcandy.setBackgroundResource(R.drawable.roundnotselect);
//                    mcroissant.setBackgroundResource(R.drawable.roundnotselect);
//                    //Chọn menu candy
//                    mcupcaketv.setVisibility(View.VISIBLE);
//                    mcupcakeimg.setImageResource(R.drawable.cupcakeselected);
//                    mcupcake.setBackgroundResource(R.drawable.roundcupcake);
//
//                    //Tạo hiệu ứng cho candy
//                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
//                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
//                    scaleAnimation.setDuration(200);
//                    scaleAnimation.setFillAfter(true);
//                    mcupcake.startAnimation(scaleAnimation);
//
//                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
//                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
//                    selectedMenu = 3;
//
//                    //Load sản phẩm cupcake
//                    loadItemHCupCData();
//                }
//            }
//        });
//
//        //
////        LoadData();
////        mItemRcmAdapter = new ItemRcmAdapter(lstProduct, this);
////        productAdapter = new ProductAdapter(lstProduct,this);
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
////        rvListC.setAdapter(mItemRcmAdapter);
////        rvListC.setLayoutManager(linearLayoutManager);
//        //
//
//        mcroissant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(selectedMenu != 4){
//
//                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
//                    mcaketv.setVisibility(View.GONE);
//                    mcandytv.setVisibility(View.GONE);
//                    mcupcaketv.setVisibility(View.GONE);
//
//                    mcakeimg.setImageResource(R.drawable.cake);
//                    mcandyimg.setImageResource(R.drawable.candy);
//                    mcupcakeimg.setImageResource(R.drawable.cupcake);
//
////                    mcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
////                    mcandy.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
////                    mcupcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcake.setBackgroundResource(R.drawable.roundnotselect);
//                    mcandy.setBackgroundResource(R.drawable.roundnotselect);
//                    mcupcake.setBackgroundResource(R.drawable.roundnotselect);
//                    //Chọn menu candy
//                    mcroissanttv.setVisibility(View.VISIBLE);
//                    mcroissantimg.setImageResource(R.drawable.croissantselected);
//                    mcroissant.setBackgroundResource(R.drawable.roundcroissant);
//
//                    //Tạo hiệu ứng cho candy
//                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
//                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
//                    scaleAnimation.setDuration(200);
//                    scaleAnimation.setFillAfter(true);
//                    mcroissant.startAnimation(scaleAnimation);
//
//                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
//                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
//                    selectedMenu = 4;
//
//                    //Load sản phẩm croissant
//                    loadItemHCroissData();
//                }
//            }
//        });

        //MeoBottomNavigation
        btnav = findViewById(R.id.btnav);
        loadFragment(new HomeFragmentActivity());
        btnav.show(1,true );
        btnav.add(new MeowBottomNavigation.Model(1, R.drawable.btnavhome));
        btnav.add(new MeowBottomNavigation.Model(2, R.drawable.btnavgrid));
        btnav.add(new MeowBottomNavigation.Model(3, R.drawable.btnavheart));
        btnav.add(new MeowBottomNavigation.Model(4, R.drawable.btnavuserinf));

        btnav.getId();
        btnav.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch (model.getId()){
                    case 1:
                        loadFragment(new HomeFragmentActivity());

//                        Intent homeintent = new Intent(MainActivity.this,);
//                        startActivity(homeintent);
                        break;
                    case 2:
                        loadFragment(new ProductFragmentActivity());
//                        Intent categoryintent = new Intent(HomeActivity.this,ProductActivity.class);
//                        startActivity(categoryintent);
                        break;
                    case 3:
                        loadFragment(new FavouriteFragmentActivity());
//                        Intent favouritelistintent = new Intent(MainActivity.this, );
//                        startActivity(favouritelistintent);
                        break;
                    case 4:
                        loadFragment(new UserFragmentActivity());
//                        mdrawLo.openDrawer(GravityCompat.START);
                        break;
                }
                return null;
            }
        });
        btnav.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                return null;
            }
        });


//        mtvViewAll1 = findViewById(R.id.tvViewAll1);
//        mtvViewAll1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(HomeActivity.this,ProductActivity.class);
//                startActivity(i);
//            }
//        });
//        mtvViewAll2 = findViewById(R.id.tvViewAll2);
//        mtvViewAll2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(HomeActivity.this,ProductActivity.class);
//                startActivity(i);
//            }
//        });

        //Demo


//        itfv.add(new ItemFavou(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itfv.add(new ItemFavou(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itfv.add(new ItemFavou(R.drawable.pictdemo,"Hoa cúc","30.000đ"));






    }


    //
//    void LoadData() {
//        lstProduct =new ArrayList<>();
//        FirebaseFirestore.getInstance()
//                .collection("PRODUCTS")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
//                        for(DocumentSnapshot ds:dsList) {
//                            Product product = ds.toObject(Product.class);
//                            mItemRcmAdapter.add(product);
//                        }
//                    }
//                });
//    }

//     b

    //

//    void loadItemHCakeData(){
//        firestoreHome = FirebaseFirestore.getInstance();
//
//        itcakefv = new ArrayList<>();
//        CollectionReference itHCakeFVreference = firestoreHome.collection("PRODUCTS");
//        itHCakeFVreference.whereEqualTo("category","Bánh kem").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                for(DocumentSnapshot doc:list)
//                {
//                    Product itemFavou = doc.toObject(Product.class);
//                    mItemFavouAdapter.add(itemFavou);
//                }
//            }
//        });
//        mItemFavouAdapter = new ItemFavouAdapter(itcakefv, this);
//        mrcvFavouItem.setAdapter(mItemFavouAdapter);
//
//        itcakercm = new ArrayList<>();
//        CollectionReference itHCakeRCMreference = firestoreHome.collection("PRODUCTS");
//        itHCakeRCMreference.whereEqualTo("category","Bánh kem").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                for(DocumentSnapshot doc:list)
//                {
//                    Product itemRcm = doc.toObject(Product.class);
//                    mItemRcmAdapter.add(itemRcm);
//                }
//            }
//        });
//        mItemRcmAdapter = new ItemRcmAdapter(itcakercm, this);
//        mrcvRcmItem.setAdapter(mItemRcmAdapter);
//    }
//    protected void loadItemHCandyData(){
//        firestoreHome = FirebaseFirestore.getInstance();
//
//        itcandyfv = new ArrayList<>();
//        CollectionReference itHCandyFVreference = firestoreHome.collection("PRODUCTS");
//        itHCandyFVreference.whereEqualTo("category","Kẹo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                for(DocumentSnapshot doc:list)
//                {
//                    Product itemFavou = doc.toObject(Product.class);
//                    mItemFavouAdapter.add(itemFavou);
//                }
//            }
//        });
//        mItemFavouAdapter = new ItemFavouAdapter(itcandyfv, this);
//        mrcvFavouItem.setAdapter(mItemFavouAdapter);
//
//        itcandyrcm = new ArrayList<>();
//        CollectionReference itHCandyRCMreference = firestoreHome.collection("PRODUCTS");
//        itHCandyRCMreference.whereEqualTo("category","Kẹo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                for(DocumentSnapshot doc:list)
//                {
//                    Product itemRcm = doc.toObject(Product.class);
//                    mItemRcmAdapter.add(itemRcm);
//                }
//            }
//        });
//        mItemRcmAdapter = new ItemRcmAdapter(itcandyrcm, this);
//        mrcvRcmItem.setAdapter(mItemRcmAdapter);
////        itcandyrcm = new ArrayList<>();
////        itcandyrcm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
////        itcandyrcm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
////        itcandyrcm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
////        mItemRcmAdapter = new ItemRcmAdapter(itcandyrcm);
////        mrcvRcmItem.setAdapter(mItemRcmAdapter);
//    }
//    protected void loadItemHCupCData(){
//        itcupcakefv = new ArrayList<>();
//        firestoreHome = FirebaseFirestore.getInstance();
//        CollectionReference itHCupCFVreference = firestoreHome.collection("PRODUCTS");
//        itHCupCFVreference.whereEqualTo("category","Cupcake").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                for(DocumentSnapshot doc:list)
//                {
//                    Product itemFavou = doc.toObject(Product.class);
//                    mItemFavouAdapter.add(itemFavou);
//                }
//            }
//        });
//        mItemFavouAdapter = new ItemFavouAdapter(itcupcakefv, this);
//        mrcvFavouItem.setAdapter(mItemFavouAdapter);
////
//        itcupcakercm = new ArrayList<>();
//        CollectionReference itHCupCRCMreference = firestoreHome.collection("PRODUCTS");
//        itHCupCRCMreference.whereEqualTo("category","Cupcake").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                for(DocumentSnapshot doc:list)
//                {
//                    Product itemRcm = doc.toObject(Product.class);
//                    mItemRcmAdapter.add(itemRcm);
//                }
//            }
//        });
//        mItemRcmAdapter = new ItemRcmAdapter(itcupcakercm,this);
//        mrcvRcmItem.setAdapter(mItemRcmAdapter);
////        itcupcakercm = new ArrayList<>();
////        itcupcakercm.add(new ItemRcm(R.drawable.pictdemo2,"Hoa hồng","40.000đ"));
////        itcupcakercm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
////        itcupcakercm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
////        mItemRcmAdapter = new ItemRcmAdapter(itcupcakercm);
////        mrcvRcmItem.setAdapter(mItemRcmAdapter);
//    }
//    protected void loadItemHCroissData(){
//        itcroissfv = new ArrayList<>();
//        firestoreHome = FirebaseFirestore.getInstance();
//        CollectionReference itHCroissFVreference = firestoreHome.collection("PRODUCTS");
//        itHCroissFVreference.whereEqualTo("category","Bánh mặn").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                for(DocumentSnapshot doc:list)
//                {
//                    Product itemFavou = doc.toObject(Product.class);
//                    mItemFavouAdapter.add(itemFavou);
//                }
//            }
//        });
//        mItemFavouAdapter = new ItemFavouAdapter(itcroissfv,this);
//        mrcvFavouItem.setAdapter(mItemFavouAdapter);
//
//        itcroissrcm = new ArrayList<>();
//        CollectionReference itHCCroissRCMreference = firestoreHome.collection("PRODUCTS");
//        itHCCroissRCMreference.whereEqualTo("category","Bánh mặn").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                for(DocumentSnapshot doc:list)
//                {
//                    Product itemRcm = doc.toObject(Product.class);
//                    mItemRcmAdapter.add(itemRcm);
//                }
//            }
//        });
//        mItemRcmAdapter = new ItemRcmAdapter(itcroissrcm, this);
//        mrcvRcmItem.setAdapter(mItemRcmAdapter);
//    }
//    @Override
//    public void onClickToDetail(String name, int price, String description, String picture) {
//        Intent i = new Intent(this, DetailActivity.class);
//        i.putExtra("ten", name);
//        i.putExtra("mota",description);
//        i.putExtra("gia",price);
//        i.putExtra("anh",picture);
//        startActivity(i);
//    }
//    //Đến trang giới thiệu
//    public void directToAboutUs(){
//        Intent toAboutUs = new Intent(HomeActivity.this, vechungtoiactivity.class);
//        startActivity(toAboutUs);
//    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction fmtrans = getSupportFragmentManager().beginTransaction();
        fmtrans.replace(R.id.home_fragment,fragment);
        fmtrans.addToBackStack(null);
        fmtrans.commit();
    }
}