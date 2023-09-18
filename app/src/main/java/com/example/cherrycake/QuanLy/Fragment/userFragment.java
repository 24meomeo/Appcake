package com.example.cherrycake.QuanLy.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cherrycake.Home.UserInfoActivity;
import com.example.cherrycake.Login.modauactivity;
import com.example.cherrycake.QuanLy.TaiKhoanAdapter;
import com.example.cherrycake.QuanLy.TaiKhoanModel;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class userFragment extends Fragment {
    ImageView mimgUser;
    TextView mtvUserName, mtvUserInfo, mtvUserVoucher, mtvUserOrders;
    Button mbtnLogout;
    FirebaseAuth firebaseAuth;
    String userID;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    RecyclerView recyclerView;
    List<TaiKhoanModel> TaiKhoanModelList;
    TaiKhoanAdapter TaiKhoanAdapter;
    FloatingActionButton mbtnThem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_userquanly, container, false);
        mimgUser = v.findViewById(R.id.imgUser);
        mtvUserName = v.findViewById(R.id.tvUserName);
        mtvUserInfo = v.findViewById(R.id.tvUserInfo);
//        mtvUserVoucher = v.findViewById(R.id.tvUserVoucher);
//        mtvUserOrders= v.findViewById(R.id.tvUserOrders);
        mbtnLogout= v.findViewById(R.id.btnLogout);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userID = firebaseAuth.getCurrentUser().getUid();

//        mtvUserOrders.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) { //khi làm fragment nhớ thêm getActivity
//                Intent i = new Intent(UserFragmentActivity.this.getActivity(), HistoryOrderActivity.class);
//                startActivity(i);
//            }
//        });
        recyclerView = v.findViewById(R.id.rcvHienThiTaiKhoan);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        TaiKhoanModelList = new ArrayList<>();

        CollectionReference KHcollectionReference = firestore.collection("USERS");
        KHcollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list){
                    TaiKhoanModel taiKhoanModel = doc.toObject(TaiKhoanModel.class);
                    TaiKhoanAdapter.add(taiKhoanModel);
                }
            }
        });
        TaiKhoanAdapter = new TaiKhoanAdapter(v.getContext(), TaiKhoanModelList);
        recyclerView.setAdapter(TaiKhoanAdapter);

        StorageReference profileRef = storageReference.child("Users/"+ userID +"/Profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(mimgProfileImage);
                Glide.with(userFragment.this).load(uri).override(100,100).centerCrop().into(mimgUser);
            }
        });

        DocumentReference InfoProfiledocumentReference = firestore.collection("USERS").document(userID);
        InfoProfiledocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String fullname = documentSnapshot.getString("Fullname");
                mtvUserName.setText(fullname);
            }
        });

        mtvUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUserInfo = new Intent(userFragment.this.getActivity(), UserInfoActivity.class);
                startActivity(intentUserInfo);
            }
        });
        mbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intentSignOut = new Intent(userFragment.this.getActivity(), modauactivity.class);
                startActivity(intentSignOut);
            }
        });
//        mbtnThem = v.findViewById(R.id.btnFloatingAddProduct);
//        mbtnThem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View n) {
//                //Tắt hiển thị khoFragment để addFragment hiện lên
//
//                loadFragment(new addProductFragment());
//            }
//        });
        return v;
    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction fmtrans = getParentFragmentManager().beginTransaction();
        fmtrans.replace(R.id.homeadmin_fragment,fragment);
        fmtrans.addToBackStack(null);
        fmtrans.commit();
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
                Glide.with(userFragment.this).load(uri).override(100, 100).centerCrop().into(mimgUser);
            }
        });
        DocumentReference InfoProfiledocumentReference = firestore.collection("USERS").document(userID);
        InfoProfiledocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String fullname = documentSnapshot.getString("Fullname");
                mtvUserName.setText(fullname);
            }
        });
    }
}
