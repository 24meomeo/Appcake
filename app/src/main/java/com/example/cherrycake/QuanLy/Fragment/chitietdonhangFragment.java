package com.example.cherrycake.QuanLy.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cherrycake.Home.HomeActivity;
import com.example.cherrycake.QuanLy.HomeAdmin;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class chitietdonhangFragment extends AppCompatActivity {
    Button mbtnXacNhan, mbtnHuy;
    TextView mtvMa, mtvtongia, mtvTongSoLuong, mtvNgayMua, mtvGioMua;
    EditText edtTrangThai;
    //firestore
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    int ttm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietdonhang_fragment);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        String makhach = getIntent().getStringExtra("makhach");
        String madon = getIntent().getStringExtra("madon");
        int tonggia= getIntent().getIntExtra("tonggia", 0);
        int tongsoluong = getIntent().getIntExtra("tongsoluong", 0);
        String ngaymua = getIntent().getStringExtra("ngaymua");
        String giomua = getIntent().getStringExtra("giomua");
        int trangthai = getIntent().getIntExtra("trangthai", 0);

        mtvMa = findViewById(R.id.edtMaDon);
        mtvMa.setText(madon);
        mtvMa.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mtvMa.setHorizontallyScrolling(true);
        mtvMa.setMarqueeRepeatLimit(-1);
        mtvMa.setSelected(true);
        mtvMa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mtvMa.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mtvMa.getWidth() < mtvMa.getLayout().getLineWidth(0)) {
                                mtvMa.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                mtvMa.setHorizontallyScrolling(true);
                                mtvMa.setMarqueeRepeatLimit(-1);
                                mtvMa.setSelected(true);
                            } else {
                                mtvMa.setEllipsize(null);
                                mtvMa.setHorizontallyScrolling(false);
                                mtvMa.setMarqueeRepeatLimit(0);
                                mtvMa.setSelected(false);
                            }
                        }
                    });
                }
            }
        });
        mtvNgayMua = findViewById(R.id.edtNgayMua);
        mtvNgayMua.setText(ngaymua);
        mtvtongia = findViewById(R.id.edtTongGia);
        mtvtongia.setText(tonggia + "");
        mtvGioMua = findViewById(R.id.edtGioMua);
        mtvGioMua.setText(giomua);
        mtvTongSoLuong = findViewById(R.id.edtTongSoLuong);
        mtvTongSoLuong.setText(tongsoluong + "");

        edtTrangThai = findViewById(R.id.edtTrangThai);
//        edtTrangThai.setText(trangthai + "");
        if(trangthai == 0){
            edtTrangThai.setText("Đang chờ");
            edtTrangThai.setTextColor(Color.parseColor("#F62D2B"));
        }else if(trangthai == 1){
            edtTrangThai.setText("Đã xác nhận");
            edtTrangThai.setTextColor(Color.parseColor("#088948"));
        }else if(trangthai == 2){
            edtTrangThai.setText("Đang làm bánh");
            edtTrangThai.setTextColor(Color.parseColor("#088948"));
        }else if(trangthai == 3){
            edtTrangThai.setText("Bánh đã có");
            edtTrangThai.setTextColor(Color.parseColor("#088948"));
        }else{
            edtTrangThai.setText("Từ chối");
            edtTrangThai.setTextColor(Color.parseColor("#DF0512"));
        }

        mbtnXacNhan = findViewById(R.id.btnXacNhan);
        mbtnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trangthaimoi = edtTrangThai.getText().toString();
                if(trangthaimoi.equals("Đã xác nhận")){
                    ttm = 1;
                } else if (trangthaimoi.equals("Đang làm bánh")) {
                    ttm = 2;
                } else if (trangthaimoi.equals("Bánh đã có")) {
                    ttm = 3;
                } else if (trangthaimoi.equals("Từ chối")) {
                    ttm = 4;
                } else{
                    ttm = 0;
                }
                if(trangthaimoi.equals("Đang chờ")){
                    Toast.makeText(chitietdonhangFragment.this, "Không có thay đổi", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(chitietdonhangFragment.this, HomeAdmin.class);
                    startActivity(intent);
                }else{
                    Map<String,Object> edited = new HashMap<>();
                    edited.put("trangthai", ttm);

                    DocumentReference documentReference = db.collection("ORDERS").document(madon);
                    documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(v.getContext(), "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(chitietdonhangFragment.this, HomeAdmin.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), "Lỗi cập nhật dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });

        mbtnHuy = findViewById(R.id.btnHuy);
        mbtnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chitietdonhangFragment.this, HomeAdmin.class);
                startActivity(intent);
            }
        });
    }
}
