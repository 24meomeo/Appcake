package com.example.cherrycake.GioHang.ThanhToan;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.R;
import com.example.cherrycake.data.model.VoucherModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class ThanhToanActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ThanhToanModel> thanhToanModelList;
    ThanhToanAdapter thanhToanAdapter;

    TextView tvToTalPriceOfPay, tvPriceOfPayContentItem4, tvTitleVoucher;

    Button btnMuaHang;
    int totalPrice;
    int totalQuantity;

    // firebase
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    CollectionReference collectionReference;
    FirebaseUser user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();
        userID = user.getUid();

        btnMuaHang = findViewById(R.id.btnPay);
        tvToTalPriceOfPay = findViewById(R.id.tvPriceOfPayFooter);
        tvTitleVoucher = findViewById(R.id.tvTitleVoucher);
        tvPriceOfPayContentItem4 = findViewById(R.id.tvPriceOfPayContentItem4);

        // load và nhận dữ liệu tổng tiền từ cart activity
        totalPrice = getIntent().getIntExtra("totalPriceFromCart", 0);
        // format giá
        String totalGia = NumberFormat.getNumberInstance(Locale.US).format(totalPrice);
        tvToTalPriceOfPay.setText(totalGia + " VNĐ");

        //load và nhận dữ liệu tổng sản phẩm
        totalQuantity = getIntent().getIntExtra("totalQuantityFromCart", 1);

        TextView tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(view -> {
            finish();
        });

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiverVoucher, new IntentFilter("Voucher"));

        FrameLayout frameLayout = findViewById(R.id.framOfPayContentItem2);
        frameLayout.setOnClickListener(view -> {
            startActivity(new Intent(ThanhToanActivity.this, VoucherActivity.class));
        });

        recyclerView = findViewById(R.id.rvThanhToan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        thanhToanModelList = new ArrayList<>();

        thanhToanAdapter = new ThanhToanAdapter(this, thanhToanModelList);
        recyclerView.setAdapter(thanhToanAdapter);

        firestore.collection("USERS").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                ThanhToanModel thanhToanModel = doc.toObject(ThanhToanModel.class);
                                thanhToanModelList.add(thanhToanModel);
                                thanhToanAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        collectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid()).collection("AddToCart");

        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionReference.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().getDocuments().isEmpty()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                collectionReference.document(doc.getId()).delete();
                            }
                        }
                    }
                });
                placedOrder();
            }
        });
    }

    private void placedOrder() {

        // các biến lưu thời gian mua hàng
        String saveCurrentDate, saveCurrentTime;
        Calendar callForDate = Calendar.getInstance();
        Calendar callForTime = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForTime.getTime());

        // nạp chi tiết đơn đặt hàng
        if(thanhToanModelList != null && thanhToanModelList.size() > 0){
            for(ThanhToanModel model : thanhToanModelList){
                Map<String, Object> infoorder = new HashMap<>();
                infoorder.put("Tên", model.getName());
                infoorder.put("Số lượng", model.getTotalQuantity());
                infoorder.put("Giá", model.getPrice());

                // tham chiếu documnet dùng hàm set
                // còn tham chiếu collection dùng hàm add
//                DocumentReference OrderInfocollectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
//                        .collection("Orders").document("TongOrder").collection("Chi tiết Order").document();
//                OrderInfocollectionReference.set(infoorder);
//
//                CollectionReference OrderInfocollectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
//                        .collection("Orders").document().collection("Chi tiết đơn hàng");
//                OrderInfocollectionReference.add(infoorder);
            }
        }
        int trangthai = 0;
        String id = UUID.randomUUID().toString();
        // nạp tổng đơn đặt hàng
        Map<String, Object> dateInfo = new HashMap<>();
        dateInfo.put("madonhang", id);
        dateInfo.put("makhachhang", userID);
        dateInfo.put("tongSoLuong", totalQuantity);
        dateInfo.put("tongGia",totalPrice);
        dateInfo.put("ngayMua", saveCurrentDate);
        dateInfo.put("thoigianMua", saveCurrentTime);
        dateInfo.put("trangthai", trangthai);
//        DocumentReference TongOrderdocumentReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
//                .collection("Orders").document("TongOrder");
//        TongOrderdocumentReference.set(dateInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Intent i = new Intent(ThanhToanActivity.this, SuccessActivity.class);
//                startActivity(i);
//            }
//        });

        DocumentReference TongOrderdocumentReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
                .collection("Orders").document(id);
        DocumentReference documentReference = firestore.collection("ORDERS").document(id);
        documentReference.set(dateInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent i = new Intent(ThanhToanActivity.this, SuccessOrderActivity.class);
                startActivity(i);
            }
        });
//        TongOrderdocumentReference.set(dateInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Intent i = new Intent(ThanhToanActivity.this, SuccessOrderActivity.class);
//                startActivity(i);
//            }
//        });
    }

    public BroadcastReceiver mMessageReceiverVoucher = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            VoucherModel voucherModel = intent.getParcelableExtra("voucherItem");
            if (voucherModel != null) {
                tvTitleVoucher.setText(voucherModel.getTitle());
                int payVoucher = (voucherModel.getCode() * totalPrice) / 100;
                tvPriceOfPayContentItem4.setText(NumberFormat.getNumberInstance(Locale.US).format(payVoucher) + " VNĐ");
                int payTotal = totalPrice - ((voucherModel.getCode() * totalPrice) / 100);
                tvToTalPriceOfPay.setText(NumberFormat.getNumberInstance(Locale.US).format(payTotal) + " VNĐ");
            }
        }
    };
}