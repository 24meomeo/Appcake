package com.example.cherrycake.QuanLy.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.DonHang.HistoryOrderAdapter;
import com.example.cherrycake.DonHang.HistoryOrderModel;
import com.example.cherrycake.QuanLy.DonHangAdapter;
import com.example.cherrycake.QuanLy.SanPhamAdapter;
import com.example.cherrycake.QuanLy.SanPhamModel;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class donhangFragment extends Fragment implements DonHangAdapter.onClickButton{

    View v;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    String userID;

    ImageView mimgSP;
    FloatingActionButton mbtnThem;
    RecyclerView recyclerView;
    List<SanPhamModel> SPModelList;
    SanPhamAdapter SPAdapter;
    //firestore
    FirebaseAuth auth;

//    List<HistoryOrderModel> historyOrderModelList;
//    HistoryOrderAdapter historyOrderAdapter;

    List<HistoryOrderModel> historyOrderModelList1;
    DonHangAdapter donhangadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_donhang, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//        userID = firebaseAuth.getCurrentUser().getUid();
//
        recyclerView = v.findViewById(R.id.rcvdonhang);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        historyOrderModelList1 = new ArrayList<>();
        CollectionReference collectionReference = firestore.collection("ORDERS");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list){
                    HistoryOrderModel historyOrderModel = doc.toObject(HistoryOrderModel.class);
                    donhangadapter.add(historyOrderModel);
                }
            }
        });
        donhangadapter = new DonHangAdapter(v.getContext(), historyOrderModelList1, this::onClickToDetail);
        recyclerView.setAdapter(donhangadapter);

//        CollectionReference SPcollectionReference = firestore.collection("SP");
//        SPcollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                for(DocumentSnapshot doc:list){
//                    SanPhamModel spModel = doc.toObject(SanPhamModel.class);
//                    SPAdapter.add(spModel);
//                }
//            }
//        });

//        DonHangAdapter = new HistoryOrderAdapter(v.getContext(), historyOrderModelList1);
//        recyclerView.setAdapter(donhangadapter);

//        mbtnThem = v.findViewById(R.id.btnFloatingAddProduct);
//        mbtnThem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View n) {
//                //Tắt hiển thị khoFragment để addFragment hiện lên
//
//                loadFragment(new addFragment());
//            }
//        });
        return v;
    }

    @Override
    public void onClickToDetail(String madon, String makhach, int tonggia, int tongsoluong, String ngaymua, String giomua, int trangthai) {
        Intent i = new Intent(this.getActivity(), chitietdonhangFragment.class);
        i.putExtra("madon", madon);
        i.putExtra("makhach", makhach);
        i.putExtra("tonggia", tonggia);
        i.putExtra("tongsoluong", tongsoluong);
        i.putExtra("ngaymua", ngaymua);
        i.putExtra("giomua", giomua);
        i.putExtra("trangthai", trangthai);
        startActivity(i);
    }
//    private void loadFragment(Fragment fragment){
//        FragmentTransaction fmtrans = getParentFragmentManager().beginTransaction();
//        fmtrans.replace(R.id.homeadmin_fragment,fragment);
//        fmtrans.addToBackStack(null);
//        fmtrans.commit();
//    }
}
