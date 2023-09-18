//package com.example.cherrycake.Home;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.bumptech.glide.Glide;
//import com.example.cherrycake.QuanLy.Fragment.khoFragment;
//import com.example.cherrycake.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import java.util.HashMap;
//
//public class ReProduct extends Dialog{
//    ImageView medtdialogImage;
//    ImageButton medtdialogImageBtn;
//    EditText medtdialogName, medtdialogDes, medtdialogCate, medtdialogPrice, medtdialogSoLuong;
//    //        EditText medtdialogImage;
//    Button mbtDialogChange, mbtDialogExit;
//
//    private Uri imageUri = null;
//    private String imageURL;
//    private HashMap<String, Object> map;
//    String tenne, loaine, giane, motane;
//    int gi;
//    FirebaseStorage storage;
//    StorageReference storageReference;
//    private com.example.cherrycake.QuanLy.Fragment.khoFragment khoFragment;
//
//    public ReProduct(khoFragment khoFragment, Context context, Bundle bundle) {
//        super(context);
//        this.khoFragment = khoFragment;
//        setContentView(R.layout.custom_dialog_editproduct);
//        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getWindow().setDimAmount(0.6f);
//
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
//
//        medtdialogImage = findViewById(R.id.imgdialog);
//        String image = bundle.getString("image");
//        Glide.with(context).load(image).into(medtdialogImage);
//
//        medtdialogImageBtn = findViewById(R.id.imgdialogReImage);
//        medtdialogImageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openGalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                    startActivityForResult(openGalleryintent, 1000);
//                khoFragment.startActivityForResult(openGalleryintent, 1000);
////                    imageUri = updateImage();
////                    Glide.with(context).load(imageUri).override(512, 512).centerCrop().into(medtdialogImage);
//
//            }
//        });
//        String ten = bundle.getString("ten");
//        String loai = bundle.getString("loai");
////            String id = bundle.getString("id");
//        Integer gia = bundle.getInt("gia");
//        Integer soluong = bundle.getInt("soluong");
//        String mota = bundle.getString("mota");
//        String id = bundle.getString("id");
//
//        medtdialogName = findViewById(R.id.edtdialogReName);
//        medtdialogDes = findViewById(R.id.edtdialogReDes);
//        medtdialogCate = findViewById(R.id.edtdialogReCate);
//        medtdialogPrice = findViewById(R.id.edtdialogRePrice);
//        medtdialogSoLuong = findViewById(R.id.edtdialogReSoLuong);
//
//
//        medtdialogName.setText(ten);
//        medtdialogCate.setText(loai);
//        medtdialogPrice.setText("" + gia);
//        medtdialogSoLuong.setText("" + soluong);
//        medtdialogDes.setText(mota);
//
//        mbtDialogExit = findViewById(R.id.btnDialogProductExit);
//        mbtDialogExit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//
//        mbtDialogChange = findViewById(R.id.btnDialogProductChange);
//        mbtDialogChange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tenne = medtdialogName.getText().toString().trim();
//                loaine = medtdialogCate.getText().toString().trim();
//                giane = medtdialogPrice.getText().toString().trim();
//                gi = Integer.parseInt(giane);
//                motane = medtdialogDes.getText().toString().trim();
//                if (tenne.isEmpty() || loaine.isEmpty() || giane.isEmpty() || motane.isEmpty()
//                    /*|| imageUri == null*/) {
//                    Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                } else {
//                    uploadInfo(id);
//                    dismiss();
//                }
////                    if(medtdialogName.getText().toString().isEmpty()
////                            || medtdialogCate.getText().toString().isEmpty() || medtdialogPrice.getText().toString().isEmpty()
////                        || medtdialogDes.getText().toString().isEmpty()){
////                        Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
////                    } else {
//////                            user.updatePassword(medtdialogPass.getText().toString());
////                        CollectionReference collectionReference = firestore.collection("PRODUCTS");
////                        collectionReference.whereEqualTo("name", nameitem).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////                            @Override
////                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
////                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
////                                    DocumentReference documentReference = collectionReference.document(documentSnapshot.getId());
////                                    Map<String,Object> edited = new HashMap<>();
////                                    edited.put("name",medtdialogName.getText().toString());
////                                    edited.put("category",medtdialogCate.getText().toString());
////                                    edited.put("price",Integer.valueOf(medtdialogPrice.getText().toString()));
////                                    edited.put("description",medtdialogDes.getText().toString());
//////                                    edited.put("image",medtdialogImage);
////                                    documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
////                                        @Override
////                                        public void onSuccess(Void unused) {
////                                            Toast.makeText(v.getContext(), "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
////                                        }
////                                    }).addOnFailureListener(new OnFailureListener() {
////                                        @Override
////                                        public void onFailure(@NonNull Exception e) {
////                                            Toast.makeText(v.getContext(), "Lỗi cập nhật dữ liệu", Toast.LENGTH_SHORT).show();
////                                        }
////                                    });
////                                }
////                            }
////                        });
////                    Toast.makeText(v.getContext(), "Thay đổi thông tin sản phẩm thành công", Toast.LENGTH_SHORT).show();
////                    dismiss();
//            }
//        });
//    }
//        @Override
//        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            if(requestCode == 1000){
//                if(resultCode == Activity.RESULT_OK){
//                    imageUri = data.getData();
//                    Glide.with(SanPhamAdapter.this.context).load(imageUri).override(512, 512).centerCrop().into(medtdialogImage);
//                }
//            }
//        }
//
//
//    private void uploadInfo(String id){
//        CollectionReference collectionReference = firestore.collection("SP");
//        map = new HashMap<>();
//        map.put("name", tenne);
//        map.put("category", loaine);
//        map.put("price", gi);
////            map.put("image", imageURL);
//        map.put("description", motane);
//        map.put("id", id);
//        collectionReference.document(id).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(getContext(),"Thay đổi thông tin sản phẩm thành công",Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(),"Thay đổi thông tin sản phẩm thất bại",Toast.LENGTH_SHORT).show();
//            }
//        });
////            if(imageUri != null){
////                StorageReference myRef = storageReference.child("images/" + imageUri.getLastPathSegment()); //Lấy tên file ảnh cuối cùng
////                myRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////                    @Override
////                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                        myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                            @Override
////                            public void onSuccess(Uri uri) {
////                                if (uri != null){
////                                    imageURL = uri.toString();
////                                    if(imageURL == null){
////                                        Toast.makeText(context,"Chưa tìm thấy đường dẫn hình ảnh",Toast.LENGTH_SHORT).show();
////                                    }else {
////                                        CollectionReference collectionReference = firestore.collection("SP");
////                                        map = new HashMap<>();
////                                        map.put("name", tenne);
////                                        map.put("category", loaine);
////                                        map.put("price", gi);
////                                        map.put("image", imageURL);
////                                        map.put("description", motane);
////                                        map.put("id", id);
////                                        collectionReference.document(id).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
////                                            @Override
////                                            public void onComplete(@NonNull Task<Void> task) {
////                                                Toast.makeText(context,"Thay đổi thông tin sản phẩm thành công",Toast.LENGTH_SHORT).show();
////                                                dismiss();
////                                            }
////                                        }).addOnFailureListener(new OnFailureListener() {
////                                            @Override
////                                            public void onFailure(@NonNull Exception e) {
////                                                Toast.makeText(context,"Thay đổi thông tin sản phẩm thất bại",Toast.LENGTH_SHORT).show();
////                                            }
////                                        });
////                                    }
////                                }
////                                else{
////                                    Toast.makeText(context,"Không tìm thấy uri",Toast.LENGTH_SHORT).show();
////                                }
////                            }
////                        }).addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception e) {
////                                Toast.makeText(context, "Lỗi lấy đường link ảnh", Toast.LENGTH_SHORT).show();
////                            }
////                        });
////                    }
////                }).addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        Toast.makeText(context, "Lỗi tải ảnh lên Firebase Storage", Toast.LENGTH_SHORT).show();
////                    }
////                });
////            }
//
//    }
//
//}
