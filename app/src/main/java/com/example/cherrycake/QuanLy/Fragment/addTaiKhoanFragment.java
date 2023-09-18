package com.example.cherrycake.QuanLy.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cherrycake.Login.nhapgmailactivity;
import com.example.cherrycake.Login.otpactivity;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class addTaiKhoanFragment extends Fragment {
    View v;

    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference storageReference;


    private FirebaseAuth auth;
//    String userID = firebaseAuth.getCurrentUser().getUid();

    EditText medtMail, medtMK, medtVaiTro;
    Button mbtnThem, mbtnHuy;
    String mail, mk, vaitro, soluong, mota;
    Long gi, sl;
    private Uri imageUri = null;
    private String imageURL; //đường dẫn ảnh
    private HashMap<String, Object> map;

    String userID;
//    private String imageUrl; // khai báo biến imageUrl ở đây


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.taikhoan_fragment, container, false);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        medtMail = v.findViewById(R.id.edtMail);
        medtMK = v.findViewById(R.id.edtMK);
        medtVaiTro = v.findViewById(R.id.edtVaiTro);
        mbtnThem = v.findViewById(R.id.btnThem);
        mbtnHuy = v.findViewById(R.id.btnHuy);
        String strpass = "cherrycake123";

        mbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = medtMail.getText().toString().trim();
                mk = medtMK.getText().toString().trim();
                vaitro = medtVaiTro.getText().toString().trim();
                if (mail.isEmpty() || mk.isEmpty() || vaitro.isEmpty())
                {
                    Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    auth.createUserWithEmailAndPassword(mail, mk)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        userID = auth.getCurrentUser().getUid();
                                        DocumentReference documentReference = firestore.collection("USERS").document(userID);
                                        //.collection("Info").document();
//                                        CollectionReference InfocollectionReference = firestore.collection("USERS").document(userID).collection("Info");
                                        Map<String, Object> userinfo = new HashMap<>();
                                        userinfo.put("idUser", userID);
                                        userinfo.put("Fullname",null);
                                        userinfo.put("Mail", mail);
                                        userinfo.put("Phone",null);
                                        userinfo.put("VaiTro", vaitro);
                                        documentReference.set(userinfo);
//                                        InfocollectionReference.add(userinfo);

                                        Intent intent = new Intent(v.getContext(), otpactivity.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(v.getContext(), "Gmail không hợp lệ",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });
        mbtnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        return v;
    }


}
