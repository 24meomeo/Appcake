package com.example.cherrycake.QuanLy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class khachhangjava extends AppCompatActivity {
    RecyclerView recyclerView;
    List<TaiKhoanModel> KHModelList;
    TaiKhoanAdapter KHAdapter;

    //firestore
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
//    FirebaseUser user = auth.getCurrentUser();

    String userID;
    Button btnsanpham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachhangjava);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();

        btnsanpham = findViewById(R.id.btnSANPHAM);
        btnsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(khachhangjava.this, adminActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.rcvHienThiKhachHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        KHModelList = new ArrayList<>();

        CollectionReference KHcollectionReference = firestore.collection("USERS");
        KHcollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list){
                    TaiKhoanModel taiKhoanModel = doc.toObject(TaiKhoanModel.class);
                    KHAdapter.add(taiKhoanModel);
                }
            }
        });
//        DocumentReference InfoProfiledocumentReference = firestore.collection("USERS").document(userID);
//        InfoProfiledocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    // Lấy dữ liệu từ document và đưa vào Adapter
//                    KhachHangModel khachHangModel = documentSnapshot.toObject(KhachHangModel.class);
//                    KHAdapter.add(khachHangModel);
//                } else {
//                    Toast.makeText(khachhangjava.this, "Chưa tồn tại thông tin", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        KHAdapter = new TaiKhoanAdapter(this, KHModelList);
        recyclerView.setAdapter(KHAdapter);
//        String currentmail = user.getEmail();



    }
}