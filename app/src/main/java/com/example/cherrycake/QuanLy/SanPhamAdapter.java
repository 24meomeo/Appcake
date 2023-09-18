package com.example.cherrycake.QuanLy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.example.cherrycake.QuanLy.Fragment.khoFragment;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder>{
    public SanPhamAdapter() {
    }
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Context context;
    List<SanPhamModel> list;
    khoFragment khoFragment;
    public SanPhamAdapter(Context context, List<SanPhamModel> list) {
        this.context = context;
        this.list = list;
    }
    public SanPhamAdapter(Context context, khoFragment khoFragment, List<SanPhamModel> list) {
        this.khoFragment = khoFragment;
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // nạp layout cho view biểu diễn phần từ gio hang item
        View userView = inflater.inflate(R.layout.sanphamitem,parent,false);
        //
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SanPhamModel item = list.get(position);
        Glide.with(context).load(item.getImage()).into(holder.ivSanPham);
        holder.tenSanPham.setText(item.getName());
        holder.loaiSanPham.setText("Loại: "+ item.getCategory());
        holder.giaSanPham.setText(NumberFormat.getNumberInstance(Locale.US).format(item.getPrice())+" VNĐ");
        holder.motaSanPham.setText("Mô tả: " + item.getDescription());
        holder.soluongSanPham.setText("Số lượng: " + item.getSoluong() + " cái");

        Bundle bundle = new Bundle();
        bundle.putString("image", item.getImage());
        bundle.putString("id", item.getId());
        bundle.putString("ten", item.getName());
        bundle.putString("loai", item.getCategory());
        bundle.putInt("gia", item.getPrice());
        bundle.putString("mota",item.getDescription());
        bundle.putInt("soluong", item.getSoluong());



        holder.mbtnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReProduct dialog = new ReProduct(khoFragment, context, bundle);
                dialog.show();
//                updateImage(khoFragment, dialog, bundle);
            }
        });
        holder.mbtnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("PRODUCTS").document(item.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(v.getContext(), "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                int ptt = holder.getAdapterPosition();
                list.remove(ptt);
                notifyDataSetChanged();
            }
        });

    }
    public Uri updateImage(Uri imageUri) {

//        Glide.with(context).load(imageUri).override(512, 512).centerCrop().into(medtdialogImage);
        return imageUri;
//        medtdialogImage.setImageURI(imageUri);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSanPham;
        ImageView mbtnChinhSua, mbtnXoa;
        TextView tenSanPham, giaSanPham, loaiSanPham, motaSanPham, soluongSanPham;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSanPham = itemView.findViewById(R.id.ivSP);
            giaSanPham = itemView.findViewById(R.id.tvgiabanh);
            loaiSanPham = itemView.findViewById(R.id.tvloaibanh);
            tenSanPham = itemView.findViewById(R.id.tvtenbanh);
            motaSanPham = itemView.findViewById(R.id.tvMoTa);
            soluongSanPham = itemView.findViewById(R.id.tvSoLuong);

            motaSanPham.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            motaSanPham.setHorizontallyScrolling(true);
            motaSanPham.setMarqueeRepeatLimit(-1);
            motaSanPham.setSelected(true);
            motaSanPham.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        motaSanPham.post(new Runnable() {
                            @Override
                            public void run() {
                                if (motaSanPham.getWidth() < motaSanPham.getLayout().getLineWidth(0)) {
                                    motaSanPham.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                    motaSanPham.setHorizontallyScrolling(true);
                                    motaSanPham.setMarqueeRepeatLimit(-1);
                                    motaSanPham.setSelected(true);
                                } else {
                                    motaSanPham.setEllipsize(null);
                                    motaSanPham.setHorizontallyScrolling(false);
                                    motaSanPham.setMarqueeRepeatLimit(0);
                                    motaSanPham.setSelected(false);
                                }
                            }
                        });
                    }
                }
            });

            mbtnChinhSua = itemView.findViewById(R.id.btnChinhSua);
            mbtnXoa = itemView.findViewById(R.id.btnXoa);

            mbtnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public class ReProduct extends Dialog{
        ImageView medtdialogImage;
        ImageButton medtdialogImageBtn;
        EditText medtdialogName, medtdialogDes, medtdialogCate, medtdialogPrice, medtdialogSoLuong;
//        EditText medtdialogImage;
        Button mbtDialogChange, mbtDialogExit;

        private Uri imageUri = null;
        private String imageURL;
        private HashMap<String, Object> map;
        String tenne, loaine, giane, motane;
        Long gi;
        FirebaseStorage storage;
        StorageReference storageReference;
        private khoFragment khoFragment;

        public ReProduct(khoFragment khoFragment, Context context, Bundle bundle) {
            super(context);
            this.khoFragment = khoFragment;
            setContentView(R.layout.custom_dialog_editproduct);
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setDimAmount(0.6f);

            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            medtdialogImage = findViewById(R.id.imgdialog);
            String image = bundle.getString("image");
            Glide.with(context).load(image).into(medtdialogImage);

            medtdialogImageBtn = findViewById(R.id.imgdialogReImage);
            medtdialogImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openGalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(openGalleryintent, 1000);
                    khoFragment.startActivityForResult(openGalleryintent, 1000);
//                    imageUri = updateImage();
//                    Glide.with(context).load(imageUri).override(512, 512).centerCrop().into(medtdialogImage);

                }
            });
            String ten = bundle.getString("ten");
            String loai = bundle.getString("loai");
//            String id = bundle.getString("id");
            Integer gia = bundle.getInt("gia");
            Integer soluong = bundle.getInt("soluong");
            String mota = bundle.getString("mota");
            String id = bundle.getString("id");

            medtdialogName = findViewById(R.id.edtdialogReName);
            medtdialogDes = findViewById(R.id.edtdialogReDes);
            medtdialogCate = findViewById(R.id.edtdialogReCate);
            medtdialogPrice = findViewById(R.id.edtdialogRePrice);
            medtdialogSoLuong = findViewById(R.id.edtdialogReSoLuong);


            medtdialogName.setText(ten);
            medtdialogCate.setText(loai);
            medtdialogPrice.setText("" + gia);
            medtdialogSoLuong.setText("" + soluong);
            medtdialogDes.setText(mota);

            mbtDialogExit = findViewById(R.id.btnDialogProductExit);
            mbtDialogExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            mbtDialogChange = findViewById(R.id.btnDialogProductChange);
            mbtDialogChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tenne = medtdialogName.getText().toString().trim();
                    loaine = medtdialogCate.getText().toString().trim();
                    giane = medtdialogPrice.getText().toString().trim();
                    gi = Long.parseLong(giane);
                    motane = medtdialogDes.getText().toString().trim();
                    if (tenne.isEmpty() || loaine.isEmpty() || giane.isEmpty() || motane.isEmpty()
                            /*|| imageUri == null*/) {
                        Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadInfo(id);
                        dismiss();
                    }
//                    if(medtdialogName.getText().toString().isEmpty()
//                            || medtdialogCate.getText().toString().isEmpty() || medtdialogPrice.getText().toString().isEmpty()
//                        || medtdialogDes.getText().toString().isEmpty()){
//                        Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    } else {
////                            user.updatePassword(medtdialogPass.getText().toString());
//                        CollectionReference collectionReference = firestore.collection("PRODUCTS");
//                        collectionReference.whereEqualTo("name", nameitem).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                                    DocumentReference documentReference = collectionReference.document(documentSnapshot.getId());
//                                    Map<String,Object> edited = new HashMap<>();
//                                    edited.put("name",medtdialogName.getText().toString());
//                                    edited.put("category",medtdialogCate.getText().toString());
//                                    edited.put("price",Integer.valueOf(medtdialogPrice.getText().toString()));
//                                    edited.put("description",medtdialogDes.getText().toString());
////                                    edited.put("image",medtdialogImage);
//                                    documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Toast.makeText(v.getContext(), "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(v.getContext(), "Lỗi cập nhật dữ liệu", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            }
//                        });
//                    Toast.makeText(v.getContext(), "Thay đổi thông tin sản phẩm thành công", Toast.LENGTH_SHORT).show();
//                    dismiss();
                }
            });
        }
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


        private void uploadInfo(String id){
            CollectionReference collectionReference = firestore.collection("SP");
            map = new HashMap<>();
            map.put("name", tenne);
            map.put("category", loaine);
            map.put("price", gi);
//            map.put("image", imageURL);
            map.put("description", motane);
            collectionReference.document(id).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context,"Thay đổi thông tin sản phẩm thành công",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context,"Thay đổi thông tin sản phẩm thất bại",Toast.LENGTH_SHORT).show();
                }
            });
//            if(imageUri != null){
//                StorageReference myRef = storageReference.child("images/" + imageUri.getLastPathSegment()); //Lấy tên file ảnh cuối cùng
//                myRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                if (uri != null){
//                                    imageURL = uri.toString();
//                                    if(imageURL == null){
//                                        Toast.makeText(context,"Chưa tìm thấy đường dẫn hình ảnh",Toast.LENGTH_SHORT).show();
//                                    }else {
//                                        CollectionReference collectionReference = firestore.collection("SP");
//                                        map = new HashMap<>();
//                                        map.put("name", tenne);
//                                        map.put("category", loaine);
//                                        map.put("price", gi);
//                                        map.put("image", imageURL);
//                                        map.put("description", motane);
//                                        map.put("id", id);
//                                        collectionReference.document(id).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                Toast.makeText(context,"Thay đổi thông tin sản phẩm thành công",Toast.LENGTH_SHORT).show();
//                                                dismiss();
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(context,"Thay đổi thông tin sản phẩm thất bại",Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                    }
//                                }
//                                else{
//                                    Toast.makeText(context,"Không tìm thấy uri",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(context, "Lỗi lấy đường link ảnh", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, "Lỗi tải ảnh lên Firebase Storage", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
        }
    }

    public void add(SanPhamModel SanPhamModel){
        list.add(SanPhamModel);
        notifyDataSetChanged();
    }
}
