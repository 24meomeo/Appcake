package com.example.cherrycake.GioHang;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.GioHang.ThanhToan.ThanhToanActivity;
import com.example.cherrycake.Home.HomeActivity;
import com.example.cherrycake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class GioHangActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<MyCartModel> cartModelList;
    MyCartAdapter cartAdapter;


    // biến chứa tổng giá tiền của cart
    int totalBill;
    int totalQuantity;
    TextView AllAmount;

    Button btnThanhToan;
    TextView btnBack;
    TextView btnHome;
    CollectionReference collectionReference;


    //firestore
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);

        // ánh xạ
        AllAmount = findViewById(R.id.tvCartTotalPrice);
        btnBack = (TextView) findViewById(R.id.iconCartBack);
        btnHome = (TextView) findViewById(R.id.iconCartHome);
        btnThanhToan = (Button) findViewById(R.id.btnCartThanhToan);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(GIoHangActivity.this, HomeActivity.class);
//                startActivity(i);
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GioHangActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        // get dữ liệu tổng giá tiền từ cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));
//
//        // get dữ liệu tổng sản phẩm từ cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(qMessageReceiver, new IntentFilter("MyTotalQuantity"));

        //set dữ liệu
        recyclerView = findViewById(R.id.rvCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this, cartModelList);
        recyclerView.setAdapter(cartAdapter);


//        totalQuantity = getIntent().getStringExtra("totalQuantity");

        collectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid()).collection("AddToCart");
//        collectionReference.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
//                    MyCartModel myCartModel = doc.toObject(MyCartModel.class);
//                    cartModelList.add(myCartModel);
//                    cartAdapter.notifyDataSetChanged();
//                }
//            }
//        });

        btnThanhToan.setOnClickListener(view -> {
//            collectionReference.get().addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    if (!task.getResult().getDocuments().isEmpty()) {
//                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
//                            collectionReference.document(doc.getId()).delete();
//                        }
//                    }
//                }
//            });

            Intent i = new Intent(GioHangActivity.this, ThanhToanActivity.class);
            i.putExtra("totalPriceFromCart", totalBill);
            i.putExtra("totalQuantityFromCart", totalQuantity);
            startActivity(i);
        });
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onResume() {
        super.onResume();
        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                List<MyCartModel> collect = task.getResult().getDocuments().stream().
                        map(s -> Objects.requireNonNull(s.toObject(MyCartModel.class)))
                        .collect(Collectors.toList());

                cartModelList.clear();
                cartModelList.addAll(collect);
                int totalAmount = cartModelList.stream().mapToInt(s -> s.getTotalQuantity() * s.getPrice()).sum();
                totalBill = totalAmount;
                AllAmount.setText(NumberFormat.getNumberInstance(Locale.US).format(totalAmount)+" VNĐ");
//                AllAmount.setText(totalAmount + " VNĐ");
                cartAdapter.notifyDataSetChanged();
            }
        });
    }
    // hàm nhận tổng giá tiền từ cart adapter
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<MyCartModel> lst = intent.getParcelableArrayListExtra("data");
            if (lst != null) {
                int totalAmount = lst.stream().mapToInt(s -> s.getTotalQuantity() * s.getPrice()).sum();
                totalBill = totalAmount;
                AllAmount.setText(totalAmount + " VNĐ");
            }
        }
    };
    // hàm nhận tổng sản phẩm từ cart adapter
    public BroadcastReceiver qMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalQuantity = intent.getIntExtra("totalQuantity", 1);
        }
    };
}