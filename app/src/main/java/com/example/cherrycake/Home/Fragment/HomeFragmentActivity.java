package com.example.cherrycake.Home.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cherrycake.SanPham.ChiTietSPActivity;
import com.example.cherrycake.GioHang.GioHangActivity;
import com.example.cherrycake.Home.ItemFavouAdapter;
import com.example.cherrycake.Home.ItemRcmAdapter;
import com.example.cherrycake.Home.UserInfoActivity;
import com.example.cherrycake.SanPham.ProductModel;
import com.example.cherrycake.SanPham.DanhSachSPActivity;
import com.example.cherrycake.R;
//import com.example.cherrycake.ui.choosecolo.MainActivity;
import com.example.cherrycake.Login.vechungtoiactivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HomeFragmentActivity extends Fragment implements ItemFavouAdapter.onClickItem, ItemRcmAdapter.onClickItem {

    View v;
    String userID;
    String[] greetings = {"Hôm nay bạn thế nào?", "Cùng thử bánh nhé!", "Chào mừng trở lại!"};

    ImageButton mimgbtAboutus, mimgbtFind, mimgbtCart, mimgbtDirect, mimgbtPhone;
    AppCompatImageView linkView;
    ImageView mimgImagePro;
    LinearLayout mcake, mcandy, mcupcake, mcroissant;
    ImageView mcakeimg, mcandyimg, mcupcakeimg, mcroissantimg;
    TextView mcaketv, mcandytv, mcupcaketv, mcroissanttv;

    TextView mtvWelcome, mGreetingAuto, mtvaboutus,mtvViewAll1, mtvViewAll2;
    RecyclerView mrcvFavouItem, mrcvRcmItem;
    ItemFavouAdapter mItemFavouAdapter;
    ItemRcmAdapter mItemRcmAdapter;
    ArrayList<ProductModel> itcakefv, itcakercm, itcandyfv, itcandyrcm, itcupcakefv, itcupcakercm, itcroissfv, itcroissrcm;

    FirebaseFirestore firestoreHome;
    StorageReference storageReference;

//    MeowBottomNavigation btnav;
    int selectedMenu = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        mimgImagePro = v.findViewById(R.id.imgImagePro);
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("Users/"+ userID +"/Profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(mimgProfileImage);
                Glide.with(HomeFragmentActivity.this).load(uri).override(50, 50).centerCrop().into(mimgImagePro);
            }
        });
        mimgImagePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userinfointent = new Intent(HomeFragmentActivity.this.getActivity(), UserInfoActivity.class);
                startActivity(userinfointent);
//                mdrawLo.openDrawer(GravityCompat.START);
            }
        });
//
//        linkView = v.findViewById(R.id.linkView);
//        linkView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(requireActivity(), MainActivity.class));
//            }
//        });

        mtvWelcome = v.findViewById(R.id.tvWelcome);
        FirebaseAuth FullnamefirebaseAuth = FirebaseAuth.getInstance();
        userID = FullnamefirebaseAuth.getCurrentUser().getUid();
        FirebaseFirestore FullnamefirebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference FullnamedocumentReference = FullnamefirebaseFirestore.collection("USERS").document(userID);
        FullnamedocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String fullName = documentSnapshot.getString("Fullname");
                if (fullName != null) {
                    String[] nameParts = fullName.split("\\s+");
                    String lastName = "";
                    if (nameParts.length > 0) {
                        lastName = nameParts[nameParts.length - 1];
                    }
                    mtvWelcome.setText("Xin chào " + lastName);
                }
                else {
                    mtvWelcome.setText("Xin chào bạn");
                }
            }
        });
//        mtvGreeting.setText("Xin chào" + mlastname);


        mimgbtFind = v.findViewById(R.id.imgbtfind);
        mimgbtCart = v.findViewById(R.id.imgbtcart);
        mimgbtFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //Trỏ tới giỏ hàng
        mimgbtCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartintent = new Intent(HomeFragmentActivity.this.getActivity(), GioHangActivity.class);
                startActivity(cartintent);
            }
        });

        //Liên hệ
        mimgbtDirect = v.findViewById(R.id.imgbtdirect);
        mimgbtPhone = v.findViewById(R.id.imgbtphone);
        mimgbtAboutus = v.findViewById(R.id.imgbtaboutus);
        mtvaboutus = v.findViewById(R.id.tvaboutus);
        mimgbtDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://maps.app.goo.gl/QZDpT5coQrjGzQFw6";
                Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(map);
            }
        });
        mimgbtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callPhone = new Intent((Intent.ACTION_DIAL));
                callPhone.setData(Uri.parse("tel: 0948369673"));
                startActivity(callPhone);
            }
        });
        //Nhấp vào test để trỏ tới giỏ hàng
        mtvaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directToAboutUs();
            }
        });
        //Nhấp vào icon để trỏ tới giỏ hàng
        mimgbtAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directToAboutUs();
            }
        });


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


        LinearLayoutManager horizontalLayoutItManagerIT1 = new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL, false);
        mrcvFavouItem = v.findViewById(R.id.rcvFavouItem);
        mrcvFavouItem.setLayoutManager(horizontalLayoutItManagerIT1);
        LinearLayoutManager horizontalLayoutItManagerIT2 = new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL, false);
        mrcvRcmItem = v.findViewById(R.id.rcvRcmItem);
        mrcvRcmItem.setLayoutManager(horizontalLayoutItManagerIT2);

        //Vừa vào

        loadItemHCakeData();


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
                    loadItemHCakeData();
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
                    loadItemHCandyData();
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
                    loadItemHCupCData();
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
                    loadItemHCroissData();
                }
            }
        });

        //MeoBottomNavigation
//        btnav = findViewById(R.id.btnav);
//        loadFragment(new HomeFragmentActivity());
//        btnav.show(1,true );
//        btnav.add(new MeowBottomNavigation.Model(1, R.drawable.btnavhome));
//        btnav.add(new MeowBottomNavigation.Model(2, R.drawable.btnavgrid));
//        btnav.add(new MeowBottomNavigation.Model(3, R.drawable.btnavheart));
//        btnav.add(new MeowBottomNavigation.Model(4, R.drawable.btnavuserinf));
//
//        btnav.getId();
//        btnav.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//
//                switch (model.getId()){
//                    case 1:
//
////                        Intent homeintent = new Intent(MainActivity.this,);
////                        startActivity(homeintent);
//                        break;
//                    case 2:
//                        Intent categoryintent = new Intent(HomeActivity.this,ProductActivity.class);
//                        startActivity(categoryintent);
//                        break;
//                    case 3:
//                        loadFragment(new InfoFragmentActivity());
////                        Intent favouritelistintent = new Intent(MainActivity.this, );
////                        startActivity(favouritelistintent);
//                        break;
//                    case 4:
//                        loadFragment(new HomeFragmentActivity());
////                        mdrawLo.openDrawer(GravityCompat.START);
//                        break;
//                }
//                return null;
//            }
//        });
//        btnav.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//
//                return null;
//            }
//        });


        mtvViewAll1 = v.findViewById(R.id.tvViewAll1);
        mtvViewAll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeFragmentActivity.this.getActivity(), DanhSachSPActivity.class);
                startActivity(i);
            }
        });
        mtvViewAll2 = v.findViewById(R.id.tvViewAll2);
        mtvViewAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeFragmentActivity.this.getActivity(), DanhSachSPActivity.class);
                startActivity(i);
            }
        });
        return v;

    }
    void loadItemHCakeData(){
        firestoreHome = FirebaseFirestore.getInstance();

        itcakefv = new ArrayList<>();
        CollectionReference itHCakeFVreference = firestoreHome.collection("PRODUCTS");
        itHCakeFVreference.whereEqualTo("category","Bánh kem").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                Collections.shuffle(list);
                List<DocumentSnapshot> randomDocuments = list.subList(0, Math.min(list.size(), 8));
                for(DocumentSnapshot doc:randomDocuments)
                {
                    ProductModel itemFavou = doc.toObject(ProductModel.class);
                    mItemFavouAdapter.add(itemFavou);
                }
            }
        });
        mItemFavouAdapter = new ItemFavouAdapter(itcakefv, this);
        mrcvFavouItem.setAdapter(mItemFavouAdapter);

        itcakercm = new ArrayList<>();
        CollectionReference itHCakeRCMreference = firestoreHome.collection("PRODUCTS");
        itHCakeRCMreference.whereEqualTo("category","Bánh kem").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                Collections.shuffle(list);
                List<DocumentSnapshot> randomDocuments = list.subList(2, Math.min(list.size(), 10));
                for(DocumentSnapshot doc:randomDocuments)
                {
                    ProductModel itemRcm = doc.toObject(ProductModel.class);
                    mItemRcmAdapter.add(itemRcm);
                }
            }
        });
        mItemRcmAdapter = new ItemRcmAdapter(itcakercm, this);
        mrcvRcmItem.setAdapter(mItemRcmAdapter);
    }
    protected void loadItemHCandyData(){
        firestoreHome = FirebaseFirestore.getInstance();

        itcandyfv = new ArrayList<>();
        CollectionReference itHCandyFVreference = firestoreHome.collection("PRODUCTS");
        itHCandyFVreference.whereEqualTo("category","Kẹo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                Collections.shuffle(list);
                List<DocumentSnapshot> randomDocuments = list.subList(0, Math.min(list.size(), 8));
                for(DocumentSnapshot doc:randomDocuments)
                {
                    ProductModel itemFavou = doc.toObject(ProductModel.class);
                    mItemFavouAdapter.add(itemFavou);
                }
            }
        });
        mItemFavouAdapter = new ItemFavouAdapter(itcandyfv, this);
        mrcvFavouItem.setAdapter(mItemFavouAdapter);

        itcandyrcm = new ArrayList<>();
        CollectionReference itHCandyRCMreference = firestoreHome.collection("PRODUCTS");
        itHCandyRCMreference.whereEqualTo("category","Kẹo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                Collections.shuffle(list);
                List<DocumentSnapshot> randomDocuments = list.subList(0, Math.min(list.size(), 8));
                for(DocumentSnapshot doc:randomDocuments)
                {
                    ProductModel itemRcm = doc.toObject(ProductModel.class);
                    mItemRcmAdapter.add(itemRcm);
                }
            }
        });
        mItemRcmAdapter = new ItemRcmAdapter(itcandyrcm, this);
        mrcvRcmItem.setAdapter(mItemRcmAdapter);
//        itcandyrcm = new ArrayList<>();
//        itcandyrcm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itcandyrcm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itcandyrcm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        mItemRcmAdapter = new ItemRcmAdapter(itcandyrcm);
//        mrcvRcmItem.setAdapter(mItemRcmAdapter);
    }
    protected void loadItemHCupCData(){
        itcupcakefv = new ArrayList<>();
        firestoreHome = FirebaseFirestore.getInstance();
        CollectionReference itHCupCFVreference = firestoreHome.collection("PRODUCTS");
        itHCupCFVreference.whereEqualTo("category","Cupcake").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                Collections.shuffle(list);
                List<DocumentSnapshot> randomDocuments = list.subList(0, Math.min(list.size(), 6));
                for(DocumentSnapshot doc:randomDocuments)
                {
                    ProductModel itemFavou = doc.toObject(ProductModel.class);
                    mItemFavouAdapter.add(itemFavou);
                }
            }
        });
        mItemFavouAdapter = new ItemFavouAdapter(itcupcakefv, this);
        mrcvFavouItem.setAdapter(mItemFavouAdapter);
//
        itcupcakercm = new ArrayList<>();
        CollectionReference itHCupCRCMreference = firestoreHome.collection("PRODUCTS");
        itHCupCRCMreference.whereEqualTo("category","Cupcake").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                Collections.shuffle(list);
                List<DocumentSnapshot> randomDocuments = list.subList(0, Math.min(list.size(), 8));
                for(DocumentSnapshot doc:randomDocuments)
                {
                    ProductModel itemRcm = doc.toObject(ProductModel.class);
                    mItemRcmAdapter.add(itemRcm);
                }
            }
        });
        mItemRcmAdapter = new ItemRcmAdapter(itcupcakercm,this);
        mrcvRcmItem.setAdapter(mItemRcmAdapter);
//        itcupcakercm = new ArrayList<>();
//        itcupcakercm.add(new ItemRcm(R.drawable.pictdemo2,"Hoa hồng","40.000đ"));
//        itcupcakercm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itcupcakercm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        mItemRcmAdapter = new ItemRcmAdapter(itcupcakercm);
//        mrcvRcmItem.setAdapter(mItemRcmAdapter);
    }
    protected void loadItemHCroissData(){
        itcroissfv = new ArrayList<>();
        firestoreHome = FirebaseFirestore.getInstance();
        CollectionReference itHCroissFVreference = firestoreHome.collection("PRODUCTS");
        itHCroissFVreference.whereEqualTo("category","Bánh mặn").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                Collections.shuffle(list);
                List<DocumentSnapshot> randomDocuments = list.subList(0, Math.min(list.size(), 8));
                for(DocumentSnapshot doc:randomDocuments)
                {
                    ProductModel itemFavou = doc.toObject(ProductModel.class);
                    mItemFavouAdapter.add(itemFavou);
                }
            }
        });
        mItemFavouAdapter = new ItemFavouAdapter(itcroissfv,this);
        mrcvFavouItem.setAdapter(mItemFavouAdapter);

        itcroissrcm = new ArrayList<>();
        CollectionReference itHCCroissRCMreference = firestoreHome.collection("PRODUCTS");
        itHCCroissRCMreference.whereEqualTo("category","Bánh mặn").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                Collections.shuffle(list);
                List<DocumentSnapshot> randomDocuments = list.subList(0, Math.min(list.size(), 6));
                for(DocumentSnapshot doc:randomDocuments)
                {
                    ProductModel itemRcm = doc.toObject(ProductModel.class);
                    mItemRcmAdapter.add(itemRcm);
                }
            }
        });
        mItemRcmAdapter = new ItemRcmAdapter(itcroissrcm, this);
        mrcvRcmItem.setAdapter(mItemRcmAdapter);
    }
    @Override
    public void onClickToDetail(String name, int price, String description, String picture) {
        Intent i = new Intent(this.getActivity(), ChiTietSPActivity.class);
        i.putExtra("ten", name);
        i.putExtra("mota",description);
        i.putExtra("gia",price);
        i.putExtra("anh",picture);
        startActivity(i);
    }
    //Đến trang giới thiệu
    public void directToAboutUs(){
        Intent toAboutUs = new Intent(HomeFragmentActivity.this.getActivity(), vechungtoiactivity.class);
        startActivity(toAboutUs);
    }

    @Override
    public void onResume() {
        super.onResume();
        // load ảnh trong storage tại đây
        StorageReference profileRef = storageReference.child("Users/"+ userID +"/Profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // load ảnh từ URI vào ImageView tại đây
                Glide.with(HomeFragmentActivity.this).load(uri).override(65, 65).centerCrop().into(mimgImagePro);
            }
        });
        // Lấy đối tượng TextView
        mGreetingAuto = v.findViewById(R.id.tvGreetingAuto);

        // Lấy một giá trị ngẫu nhiên từ mảng nội dung chào hỏi
        Random random = new Random();
        String randomGreeting = greetings[random.nextInt(greetings.length)];

        // Thiết lập nội dung TextView với giá trị ngẫu nhiên được lấy từ mảng nội dung chào hỏi
        mGreetingAuto.setText(randomGreeting);
    }
}