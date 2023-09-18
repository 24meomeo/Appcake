package com.example.cherrycake.QuanLy;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class reProductFragment extends Fragment {
    View v;

    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference storageReference;


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    String userID = firebaseAuth.getCurrentUser().getUid();

    ImageView mimgSP;
    ImageButton mimgbtSPChange;
    EditText medttenSP, medtloaiSP, medtgiaSP, medtSoLuongSP, medtMoTaSP;
    Button mbtnThem, mbtnHuy;
    String ten, loai, gia, soluong, mota;
    Long gi, sl;
    private Uri imageUri = null;
    private String imageURL; //đường dẫn ảnh
    private HashMap<String, Object> map;
//    private String imageUrl; // khai báo biến imageUrl ở đây


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.add_fragment, container, false);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        mimgSP = v.findViewById(R.id.imgSP);
        mimgbtSPChange = v.findViewById(R.id.imgbtSPChange);
        medttenSP = v.findViewById(R.id.edtTenSP);
        medtloaiSP = v.findViewById(R.id.edtLoaiSP);
        medtgiaSP = v.findViewById(R.id.edtGiaSP);
        medtSoLuongSP = v.findViewById(R.id.edtSoLuong);
        medtMoTaSP = v.findViewById(R.id.edtMoTa);
        mbtnThem = v.findViewById(R.id.btnThem);
        mbtnHuy = v.findViewById(R.id.btnHuy);

        mimgbtSPChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryintent, 1000);
            }
        });

        medttenSP.setText("Binhne");
        medtgiaSP.setText("7072003");
        medtloaiSP.setText("Binh");
        medtSoLuongSP.setText("30");
        medtMoTaSP.setText("kaka");


        mbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ten = medttenSP.getText().toString().trim();
                loai = medtloaiSP.getText().toString().trim();
                gia = medtgiaSP.getText().toString().trim();
                gi = Long.parseLong(gia);
                soluong = medtSoLuongSP.getText().toString().trim();
                sl = Long.parseLong(soluong);
                mota = medtMoTaSP.getText().toString().trim();
                if (ten.isEmpty() || loai.isEmpty() || gia.isEmpty() || soluong.isEmpty() || mota.isEmpty()
                        || imageUri == null)
                {
                    Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    uploadInfo();
                }
//                if ( /*medttenSP.getText().toString().trim().isEmpty() || medtloaiSP.getText().toString().trim().isEmpty() || medtgiaSP.getText().toString().trim().isEmpty()
//                        || medtSoLuongSP.getText().toString().trim().isEmpty() || medtMoTaSP.getText().toString().isEmpty()
//                        || */imageUri == null)
//                {
//                    Toast.makeText(v.getContext(), "Vui lòng thêm ảnh sản phẩm", Toast.LENGTH_SHORT).show();
//                } else {
////                    uploadImg();
//                    uploadInfo();
//
////                    Toast.makeText(v.getContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
//                }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                imageUri = data.getData();
                Glide.with(reProductFragment.this).load(imageUri).override(512, 512).centerCrop().into(mimgSP);
//                mimgUserChange.setImageURI(imageUri);
//                uploadImg(imageUri);
            }
        }
    }
    private void uploadImg() {
//        Glide.with(addFragment.this).load(imageUri).override(100, 100).centerCrop().into(mimgSP);
        if(imageUri != null){
            StorageReference myRef = storageReference.child("images/" + imageUri.getLastPathSegment()); //Lấy tên file ảnh cuối cùng
            myRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri != null){
                                imageURL = uri.toString();
                            }
                            else{
                                Toast.makeText(reProductFragment.this.getActivity(),"Không tìm thấy uri",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(reProductFragment.this.getActivity(), "Lỗi lấy đường link ảnh", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(reProductFragment.this.getActivity(), "Lỗi tải ảnh lên Firebase Storage", Toast.LENGTH_SHORT).show();
                }
            });
        }
//        String imageName = UUID.randomUUID().toString() + ".jpg"; // tạo tên ảnh ngẫu nhiên
//        storageReference = FirebaseStorage.getInstance().getReference().child("images/" + imageName); // tham chiếu đến đường dẫn lưu trữ ảnh trên Firebase Storage
//        storageReference.putFile(imageUri) // tải ảnh lên Firebase Storage
//                .addOnSuccessListener(taskSnapshot -> {
//                    // Lấy đường dẫn tới ảnh đã tải lên Firebase Storage
//                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
//                        // Sử dụng thư viện Glide để hiển thị ảnh
//                        // Lưu đường dẫn ảnh vào Firestore để có thể lấy lại ảnh sau này
//                        String imageUrl = uri.toString();
//                        // thêm đường dẫn ảnh vào map
//                        map.put("imageUrl", imageUrl);
//                    });
//                })
//                .addOnFailureListener(e -> Toast.makeText(addFragment.this.getActivity(), "Lỗi tải ảnh lên Firebase Storage", Toast.LENGTH_SHORT).show());
    }
    private void uploadInfo(){
        if(imageUri != null){
            StorageReference myRef = storageReference.child("images/" + imageUri.getLastPathSegment()); //Lấy tên file ảnh cuối cùng
            myRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri != null){
                                imageURL = uri.toString();
                                if(imageURL == null){
                                    Toast.makeText(reProductFragment.this.getActivity(),"Chưa tìm thấy đường dẫn hình ảnh",Toast.LENGTH_SHORT).show();
                                }else {
                                    CollectionReference collectionReference = firestore.collection("SP");
                                    String id = UUID.randomUUID().toString();
                                    map = new HashMap<>();
                                    map.put("name", ten);
                                    map.put("category", loai);
                                    map.put("price", gi);
                                    map.put("soluong", sl);
                                    map.put("image", imageURL);
                                    map.put("description", mota);
                                    map.put("id", id);
//                    if (imageUrl != null) {
//                        map.put("imageUrl", imageUrl);
//                    }
                                    collectionReference.document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(reProductFragment.this.getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                                            getParentFragmentManager().popBackStack();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(reProductFragment.this.getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                            else{
                                Toast.makeText(reProductFragment.this.getActivity(),"Không tìm thấy uri",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(reProductFragment.this.getActivity(), "Lỗi lấy đường link ảnh", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(reProductFragment.this.getActivity(), "Lỗi tải ảnh lên Firebase Storage", Toast.LENGTH_SHORT).show();
                }
            });
        }
//        String ten = medttenSP.getText().toString().trim();
//        String loai = medtloaiSP.getText().toString().trim();
//        String gia = medtgiaSP.getText().toString().trim();
//        int gi = Integer.parseInt(gia);
//        String soluong = medtSoLuongSP.getText().toString().trim();
//        int sl = Integer.parseInt(soluong);
//        String mota = medtMoTaSP.getText().toString().trim();
//        if (ten.isEmpty() || loai.isEmpty() || gia.isEmpty() || soluong.isEmpty() || mota.isEmpty()
//                || imageURL == null)
//        {
//            Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//        } else {
//                            user.updatePassword(medtdialogPass.getText().toString());
//            String imageName = UUID.randomUUID().toString() + ".jpg"; // tạo tên ảnh ngẫu nhiên
//            storageReference = FirebaseStorage.getInstance().getReference().child("images/" + imageName); // tham chiếu đến đường dẫn lưu trữ ảnh trên Firebase Storage
//            storageReference.putFile(imageUri) // tải ảnh lên Firebase Storage
//                    .addOnSuccessListener(taskSnapshot -> {
//                        // Lấy đường dẫn tới ảnh đã tải lên Firebase Storage
//                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
//                            // Sử dụng thư viện Glide để hiển thị ảnh
//                            // Lưu đường dẫn ảnh vào Firestore để có thể lấy lại ảnh sau này
//                            String imageUrl = uri.toString();
//                            // thêm đường dẫn ảnh vào map
//                            map.put("image", imageUrl);
//                        });
//                    })
//                    .addOnFailureListener(e -> Toast.makeText(addFragment.this.getActivity(), "Lỗi tải ảnh lên Firebase Storage", Toast.LENGTH_SHORT).show());
//            if(imageURL == null){
//                Toast.makeText(addFragment.this.getActivity(),"Chưa tìm thấy đường dẫn hình ảnh",Toast.LENGTH_SHORT);
//            }else {
//                CollectionReference collectionReference = firestore.collection("SP");
//                String id = UUID.randomUUID().toString();
//                map = new HashMap<>();
//                map.put("name", ten);
//                map.put("category", loai);
//                map.put("price", gi);
//                map.put("soluong", sl);
//                map.put("image", imageURL);
//                map.put("description", mota);
//                map.put("id", id);
////                    if (imageUrl != null) {
////                        map.put("imageUrl", imageUrl);
////                    }
//                collectionReference.document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(addFragment.this.getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
//                        getParentFragmentManager().popBackStack();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(addFragment.this.getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }

//                    dismiss();
//        }

    }

}
