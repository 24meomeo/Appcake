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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.cherrycake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class nhanvienFragment extends Fragment {
    View v;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();

    ImageView mimgSP;
    ImageButton mimgbtSPChange;
    EditText mtenSP, mloaiSP, mgiaSP;
    Button mbtnThem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.add_fragment, container, false);

        mimgSP = v.findViewById(R.id.imgSP);
        mimgbtSPChange = v.findViewById(R.id.imgbtSPChange);
        mtenSP = v.findViewById(R.id.edtTenSP);
        mloaiSP = v.findViewById(R.id.edtLoaiSP);
        mgiaSP = v.findViewById(R.id.edtGiaSP);
        mbtnThem = v.findViewById(R.id.btnThem);

        mimgbtSPChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryintent, 1000);
            }
        });

        mbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
//                mimgUserChange.setImageURI(imageUri);
                uploadImg(imageUri);
            }
        }
    }
    private void uploadImg(Uri imageUri) {
        Glide.with(nhanvienFragment.this).load(imageUri).override(100, 100).centerCrop().into(mimgSP);

    }
}
