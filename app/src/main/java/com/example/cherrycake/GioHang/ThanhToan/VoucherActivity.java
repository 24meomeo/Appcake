package com.example.cherrycake.GioHang.ThanhToan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.R;
import com.example.cherrycake.data.model.VoucherModel;
import com.example.cherrycake.ui.VoucherAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VoucherActivity extends AppCompatActivity {

    CollectionReference collectionReference;
    //firestore
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private VoucherAdapter voucherAdapter;
    private RecyclerView rvVoucher;
    List<VoucherModel> voucherModelList;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        collectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid()).collection("Voucher");

        rvVoucher = findViewById(R.id.rvVoucher);
        TextView tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(view -> finish());
        voucherModelList = new ArrayList<>();
        voucherAdapter = new VoucherAdapter(this, voucherModelList, voucherModel -> {
            collectionReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult().getDocuments().get(voucherModelList.indexOf(voucherModel));
                    collectionReference.document(doc.getId()).delete();
                }
            });

            Intent intent = new Intent("Voucher");
            intent.putExtra("voucherItem", voucherModel);
            LocalBroadcastManager.getInstance(VoucherActivity.this).sendBroadcast(intent);
            finish();
        });

        rvVoucher.setAdapter(voucherAdapter);

        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                List<VoucherModel> collect = task.getResult().getDocuments().stream().
                        map(s -> Objects.requireNonNull(s.toObject(VoucherModel.class)))
                        .collect(Collectors.toList());

                voucherModelList.clear();
                voucherModelList.addAll(collect);
                voucherAdapter.notifyDataSetChanged();
            }
        });
    }
}