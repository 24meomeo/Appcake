package com.example.cherrycake.QuanLy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.ViewHolder>{
    Context context;
    List<TaiKhoanModel> list;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser user;
    String userID;
    public TaiKhoanAdapter() {
    }

    public TaiKhoanAdapter(Context context, List<TaiKhoanModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // nạp layout cho view biểu diễn phần từ gio hang item
        View userView = inflater.inflate(R.layout.khachhangitem,parent,false);
        //
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        int ptt = holder.getAdapterPosition();

//        user = auth.getCurrentUser();
//        userID = auth.getCurrentUser().getUid();
        TaiKhoanModel item = list.get(position);
        holder.tenKhachHang.setText(item.getFullname());
        holder.emailKhachHang.setText(item.getMail());
        holder.soDTKhachHang.setText(item.getPhone());

        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = item.getIdUser();
//                FirebaseUser user = auth
                list.remove(ptt);
                notifyItemRemoved(ptt);
                DocumentReference InfoProfiledocumentReference = firestore.collection("USERS").document(id);
                InfoProfiledocumentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                            }
//                        });
                        Toast.makeText(v.getContext(), "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), "Xóa tài khoản thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
//                final int initialPosition = holder.getAdapterPosition();
//                list.remove(holder.getAdapterPosition());
//                notifyItemRemoved(holder.getAdapterPosition());
//                String mail = holder.emailKhachHang.getText().toString();
//                DocumentReference InfoProfiledocumentReference = firestore.collection("USERS").document(userID);
//                auth.fetchSignInMethodsForEmail(mail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//
//                    }
//                });
//                if (user != null){
//                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
////                            InfoProfiledocumentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
////                                @Override
////                                public void onSuccess(Void unused) {
////                                    Toast.makeText(v.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
////                                }
////                            }).addOnFailureListener(new OnFailureListener() {
////                                @Override
////                                public void onFailure(@NonNull Exception e) {
////                                    Toast.makeText(v.getContext(), "Xóa không thành công", Toast.LENGTH_SHORT).show();
////                                }
////                            });
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(v.getContext(), "Xóa không thành công", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenKhachHang,emailKhachHang, soDTKhachHang ;
        ImageView imgXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenKhachHang = itemView.findViewById(R.id.tvhienten);
            emailKhachHang = itemView.findViewById(R.id.tvhienmail);
            soDTKhachHang = itemView.findViewById(R.id.tvhiensodienthoai);
            imgXoa = itemView.findViewById(R.id.btnXoaSP);
        }
    }
    public void add(TaiKhoanModel taiKhoanModel){
        list.add(taiKhoanModel);
        notifyDataSetChanged();
    }
}
