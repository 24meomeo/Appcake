package com.example.cherrycake.Home.Fragment;

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

import com.bumptech.glide.Glide;
import com.example.cherrycake.DonHang.HistoryOrderActivity;
import com.example.cherrycake.Home.UserInfoActivity;
import com.example.cherrycake.R;
import com.example.cherrycake.Login.modauactivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserFragmentActivity extends Fragment {
    ImageView mimgUser;
    TextView mtvUserName, mtvUserInfo, mtvUserVoucher, mtvUserOrders;
    Button mbtnLogout;
    FirebaseAuth firebaseAuth;
    String userID;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        mimgUser = v.findViewById(R.id.imgUser);
        mtvUserName = v.findViewById(R.id.tvUserName);
        mtvUserInfo = v.findViewById(R.id.tvUserInfo);
        mtvUserVoucher = v.findViewById(R.id.tvUserVoucher);
        mtvUserOrders= v.findViewById(R.id.tvUserOrders);
        mbtnLogout= v.findViewById(R.id.btnLogout);

        mbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userID = firebaseAuth.getCurrentUser().getUid();

        mtvUserOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //khi làm fragment nhớ thêm getActivity
                Intent i = new Intent(UserFragmentActivity.this.getActivity(), HistoryOrderActivity.class);
                startActivity(i);
            }
        });

        StorageReference profileRef = storageReference.child("Users/"+ userID +"/Profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(mimgProfileImage);
                Glide.with(UserFragmentActivity.this).load(uri).override(100,100).centerCrop().into(mimgUser);
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
                Intent intentUserInfo = new Intent(UserFragmentActivity.this.getActivity(), UserInfoActivity.class);
                startActivity(intentUserInfo);
            }
        });
        mbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intentSignOut = new Intent(UserFragmentActivity.this.getActivity(), modauactivity.class);
                startActivity(intentSignOut);
            }
        });
        return v;
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
                Glide.with(UserFragmentActivity.this).load(uri).override(100, 100).centerCrop().into(mimgUser);
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