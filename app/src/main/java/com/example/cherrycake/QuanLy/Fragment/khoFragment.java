package com.example.cherrycake.QuanLy.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.QuanLy.SanPhamAdapter;
import com.example.cherrycake.QuanLy.SanPhamModel;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class khoFragment extends Fragment {
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
    private Uri imageUri = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_kho, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//        userID = firebaseAuth.getCurrentUser().getUid();
//
        recyclerView = v.findViewById(R.id.rcvSP);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        SPModelList = new ArrayList<>();

        CollectionReference SPcollectionReference = firestore.collection("PRODUCTS");
        SPcollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list){
                    SanPhamModel spModel = doc.toObject(SanPhamModel.class);
                    SPAdapter.add(spModel);
                }
            }
        });
        SPAdapter = new SanPhamAdapter(/*v.getContext()*/getActivity(), this, SPModelList);
        recyclerView.setAdapter(SPAdapter);

        mbtnThem = v.findViewById(R.id.btnFloatingAddProduct);
        mbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View n) {
                //Tắt hiển thị khoFragment để addFragment hiện lên

                loadFragment(new addProductFragment());
            }
        });
        return v;
    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction fmtrans = getParentFragmentManager().beginTransaction();
        fmtrans.replace(R.id.homeadmin_fragment,fragment);
        fmtrans.addToBackStack(null);
        fmtrans.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                imageUri = data.getData();
                SPAdapter.updateImage(imageUri);
//                Glide.with(SanPhamAdapter.this.context).load(imageUri).override(512, 512).centerCrop().into(medtdialogImage);
            }
        }
    }
}
