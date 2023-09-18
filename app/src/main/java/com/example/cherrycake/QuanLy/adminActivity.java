package com.example.cherrycake.QuanLy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.Login.MaincuaYenActivity;
import com.example.cherrycake.QuanLy.Fragment.addProductFragment;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class adminActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    List<SanPhamModel> SPModelList;
    SanPhamAdapter SPAdapter;

    //firestore
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    Button btnkhachhang;
    FloatingActionButton mFloatButtonPro, mFloatButtonProAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        btnkhachhang = findViewById(R.id.btnkhachhang);
        btnkhachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminActivity.this, khachhangjava.class);
                startActivity(intent);
            }
        });

        //set dữ liệu
        recyclerView = findViewById(R.id.rcvHienThiAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
//        SPcollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for (DocumentSnapshot doc :task.getResult().getDocuments()){
//                                SanPhamModel spModel = doc.toObject(SanPhamModel.class);
//                                SPAdapter.add(spModel);
//                                SPAdapter.notifyDataSetChanged();
//                            }
//                        }



//                    }
//                });
        SPAdapter = new SanPhamAdapter(this, SPModelList);
        recyclerView.setAdapter(SPAdapter);

        mFloatButtonPro = findViewById(R.id.btnFloatingProduct);

        mFloatButtonPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intentSignOut = new Intent(adminActivity.this, MaincuaYenActivity.class);
                startActivity(intentSignOut);
            }
        });

        mFloatButtonProAdd = findViewById(R.id.btnFloatingAddProduct);
        mFloatButtonProAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new addProductFragment());
//                ReProduct reProduct = new ReProduct(adminActivity.this);
//                reProduct.show();
            }
        });
    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction fmtrans = getSupportFragmentManager().beginTransaction();
        fmtrans.replace(R.id.homeadmin_fragment,fragment);
        fmtrans.addToBackStack(null);
        fmtrans.commit();
    }
    public class ReProduct extends Dialog {
        EditText medtdialogName;
        //        EditText medtdialogDes;
        EditText medtdialogCate;
        EditText medtdialogPrice;
        //        EditText medtdialogImage;
        Button mbtDialogChange;

        public ReProduct(Context context) {
            super(context);
            setContentView(R.layout.custom_dialog_editproduct);
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setDimAmount(0.6f);

            medtdialogName = findViewById(R.id.edtdialogReName);
//            medtdialogDes = findViewById(R.id.edtdialogReDes);
            medtdialogCate = findViewById(R.id.edtdialogReCate);
            medtdialogPrice = findViewById(R.id.edtdialogRePrice);
//            medtdialogImage = findViewById(R.id.edtdialogReImage);
            mbtDialogChange = findViewById(R.id.btnDialogProductChange);

            String cate = medtdialogCate.getText().toString();
            String name = medtdialogName.getText().toString();
            String price = medtdialogPrice.getText().toString();

            mbtDialogChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (medtdialogName.getText().toString().isEmpty()
                            || medtdialogCate.getText().toString().isEmpty() || medtdialogPrice.getText().toString().isEmpty()
                    ) {
                        Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
//                            user.updatePassword(medtdialogPass.getText().toString());
                        CollectionReference collectionReference = firestore.collection("SP");
                        String id = UUID.randomUUID().toString();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("category", medtdialogCate.getText().toString());
                        map.put("name", medtdialogName.getText().toString());
                        map.put("price",  medtdialogPrice.getText().toString());
                        map.put("id", id);

                        collectionReference.document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(adminActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(adminActivity.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        });
                        dismiss();
                    }
                }
            });
        }
    }

//            mbtDialogChange.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(medtdialogPass.getText().toString().isEmpty() || medtdialogRePass.getText().toString().isEmpty()){
//                        Toast.makeText(UserInfoActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    }else if (!medtdialogPass.getText().toString().equals(medtdialogRePass.getText().toString())){
//                        Toast.makeText(UserInfoActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        user.updatePassword(medtdialogPass.getText().toString());
//                        finish();
//                        Toast.makeText(UserInfoActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//    public class ReProduct extends Dialog{
//        EditText medtdialogName;
//        EditText medtdialogDes;
//        EditText medtdialogCate;
//        EditText medtdialogPrice;
//        EditText medtdialogImage;
//        Button mbtDialogChange;
//
//        public ReProduct(Context context){
//            super(context);
//            setContentView(R.layout.custom_dialog_editproduct);
//            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            getWindow().setDimAmount(0.6f);
//
//            medtdialogName = findViewById(R.id.edtdialogReName);
////            medtdialogDes = findViewById(R.id.edtdialogReDes);
//            medtdialogCate = findViewById(R.id.edtdialogReCate);
//            medtdialogPrice = findViewById(R.id.edtdialogRePrice);
//            medtdialogImage = findViewById(R.id.edtdialogReImage);
//            mbtDialogChange = findViewById(R.id.btnDialogProductChange);
//
//
//
//            mbtDialogChange.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(medtdialogName.getText().toString().isEmpty()
//                            || medtdialogCate.getText().toString().isEmpty() || medtdialogPrice.getText().toString().isEmpty()
//                            || medtdialogImage.getText().toString().isEmpty()){
//                        Toast.makeText(adminActivvity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    } else {
//                        user.updatePassword(medtdialogPass.getText().toString());
//                        Toast.makeText(UserInfoActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
//                        dismiss();
//                    }
//                }
//            });
////            mbtDialogChange.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if(medtdialogPass.getText().toString().isEmpty() || medtdialogRePass.getText().toString().isEmpty()){
////                        Toast.makeText(UserInfoActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
////                    }else if (!medtdialogPass.getText().toString().equals(medtdialogRePass.getText().toString())){
////                        Toast.makeText(UserInfoActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
////                    }
////                    else {
////                        user.updatePassword(medtdialogPass.getText().toString());
////                        finish();
////                        Toast.makeText(UserInfoActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
////                    }
////                }
////            });
//        }
//    }
}