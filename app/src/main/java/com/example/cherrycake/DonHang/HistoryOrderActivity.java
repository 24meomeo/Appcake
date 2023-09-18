package com.example.cherrycake.DonHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.Home.HomeActivity;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<HistoryOrderModel> historyOrderModelList;
    HistoryOrderAdapter historyOrderAdapter;

    ImageView btnBack;

    //firestore
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    FirebaseUser user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        user = auth.getCurrentUser();
        userID = user.getUid();

        btnBack = findViewById(R.id.btnBackOfLSDH);

        //set dữ liệu



//        DocumentReference HistoryOrderDocumnetReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
//                .collection("Orders").document("TongOrder");
//        HistoryOrderDocumnetReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if(document.exists()){
//                        HistoryOrderModel historyOrderModel = document.toObject(HistoryOrderModel.class);
//                        historyOrderAdapter.add(historyOrderModel);
//                    }
//                }
//            }
//        });

        recyclerView = findViewById(R.id.rcvHistoryOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyOrderModelList = new ArrayList<>();
        CollectionReference HistoryOrderDocumnetReference = firestore.collection("ORDERS");
        HistoryOrderDocumnetReference.whereEqualTo("makhachhang", userID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list){
                    HistoryOrderModel historyOrderModel = doc.toObject(HistoryOrderModel.class);
                    historyOrderAdapter.add(historyOrderModel);
                }
            }
        });

        historyOrderAdapter = new HistoryOrderAdapter(this, historyOrderModelList);
        recyclerView.setAdapter(historyOrderAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HistoryOrderActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

    }
}
