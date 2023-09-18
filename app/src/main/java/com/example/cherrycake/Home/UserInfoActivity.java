package com.example.cherrycake.Home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cherrycake.Login.MaincuaYenActivity;
import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {
    TextView mtvProUserName, mtvProUserMail, mtvProUserPhone;
    EditText medtProUserName, medtProUserMail, medtProUserPhone;
    ImageView mimgProfileImage;
//    Button mbtChangeImage;
    Button mbtChangeInfoProfile, mbtChangePassProfile, mbtnDeleteAccount;
    ImageButton mbtnUserBack, mimgbtImageChange;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String userID = firebaseAuth.getCurrentUser().getUid();
    boolean verification;
    private boolean isResendClicked = false;   // Kiểm tra xem button resend đã được nhấn bao nhiêu lần
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_info);
        mbtnUserBack = findViewById(R.id.btnUserBack);
        mbtnUserBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mbtnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        mbtnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog deleteDialog = new DeleteDialog(UserInfoActivity.this);
                deleteDialog.show();
            }
        });


//        firebaseAuth = FirebaseAuth.getInstance();
//        firestore = FirebaseFirestore.getInstance();
//        storageReference = FirebaseStorage.getInstance().getReference();
//        userID = firebaseAuth.getCurrentUser().getUid();
//        user = firebaseAuth.getCurrentUser();

//        mbtChangeImage = findViewById(R.id.btnChaneImage);
        mimgProfileImage = findViewById(R.id.imgProfileImage);
        mimgbtImageChange = findViewById(R.id.imgbtProfileImageChange);
        //Read ảnh lên sẵn
        StorageReference profileRef = storageReference.child("Users/"+ userID +"/Profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(mimgProfileImage);
                Glide.with(UserInfoActivity.this).load(uri).override(150, 150).centerCrop().into(mimgProfileImage);
            }
        });

        mimgbtImageChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryintent, 1000);
            }
        });


        medtProUserName = findViewById(R.id.edtProfileUserName);
        medtProUserMail = findViewById(R.id.edtProfileUserMail);
        medtProUserPhone = findViewById(R.id.edtProfileUserPhone);
//        mtvProUserName = findViewById(R.id.tvProfileUserName);
//        mtvProUserMail = findViewById(R.id.tvProfileUserMail);
//        mtvProUserPhone = findViewById(R.id.tvProfileUserPhone);

//        CollectionReference InfoProfilecollectionReference = firestore.collection("USERS").document(userID).collection("Info");
//        InfoProfilecollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                for (DocumentSnapshot document : task.getResult()) {
//                    String name = document.getString("Fullname");
//                    String mail = document.getString("Mail");
//                    String phone = document.getString("Phone");
//                    medtProUserName.setText(name);
//                    medtProUserMail.setText(mail);
//                    medtProUserPhone.setText(phone);
////                    mtvProUserName.setText(name);
////                    mtvProUserMail.setText(mail);
////                    mtvProUserPhone.setText(phone);
//                }
//            }
//        });

//        InfoProfilecollectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                String name = value.getDocuments().toString("a");
//                mtvProUserName.setText(value.toString());
//            }
//        });
        DocumentReference InfoProfiledocumentReference = firestore.collection("USERS").document(userID);
        InfoProfiledocumentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                medtProUserName.setText(value.getString("Fullname"));
                medtProUserMail.setText(value.getString("Mail"));
                medtProUserPhone.setText(value.getString("Phone"));
            }
        });

        mbtChangeInfoProfile = findViewById(R.id.btnChaneInfoProfile);
        mbtChangeInfoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = medtProUserMail.getText().toString();
                String currentmail = user.getEmail();
                DocumentReference ChangeInfodocumentReference = firestore.collection("USERS").document(userID);
                Map<String,Object> edited = new HashMap<>();
                if(!currentmail.equals(email)){
                    user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            user.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    user.sendEmailVerification();
                                    VerifyMailDialog dialog = new VerifyMailDialog(UserInfoActivity.this);
                                    dialog.show();
//                                    verification = user.isEmailVerified();
//                                    if (verification == false) {
//
//                                    }
                                }
                            });
//                            user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        verification = user.isEmailVerified();
//                                        if (verification == true) {
//
//                                        } else {
//
//                                        }
//                                    } else {
//                                    }
//                                }
//                            });
//                            user.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    user.isEmailVerified()
//                                }
//                            });
                            edited.put("Mail", email);
                            ChangeInfodocumentReference.update(edited);
//                            Toast.makeText(UserInfoActivity.this,"Thay đổi mail thành công", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserInfoActivity.this,"Thay đổi không thành công", Toast.LENGTH_SHORT);
                        }
                    });
                }
                if(!medtProUserName.getText().toString().isEmpty()
                        || !medtProUserPhone.getText().toString().isEmpty()){
                    edited.put("Fullname", medtProUserName.getText().toString());
                    edited.put("Phone", medtProUserPhone.getText().toString());
                    ChangeInfodocumentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(UserInfoActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserInfoActivity.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    return;
                }


//                CollectionReference ChangeInfocollectionReference = firestore.collection("USERS").document(userID).collection("Info");
//                ChangeInfocollectionReference.
            }
        });

        mbtChangePassProfile = findViewById(R.id.btnChangePassUser);
        mbtChangePassProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RePassDialog dialog = new RePassDialog(UserInfoActivity.this);
                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
//                mimgUserChange.setImageURI(imageUri);

                uploadImgToFB(imageUri);
            }
        }
    }
    private void uploadImgToFB(Uri imageUri){
        final StorageReference fileRef = storageReference.child("Users/"+ userID +"/Profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
//                        Picasso.get().load(uri).into(mimgProfileImage);
                        Glide.with(UserInfoActivity.this).load(uri).override(100, 100).centerCrop().into(mimgProfileImage);
                    }
                });
                Toast.makeText(UserInfoActivity.this, "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserInfoActivity.this, "Tải ảnh thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class RePassDialog extends Dialog{
        EditText medtdialogPass = findViewById(R.id.edtdialogPass);
        EditText medtdialogRePass = findViewById(R.id.edtdialogRePass);
        Button mbtDialogChange = findViewById(R.id.btnDialogChange);
        public RePassDialog(Context context){
            super(context);
            setContentView(R.layout.custom_dialog_repass);
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setDimAmount(0.6f);

            medtdialogPass = findViewById(R.id.edtdialogPass);
            medtdialogRePass = findViewById(R.id.edtdialogRePass);
            mbtDialogChange = findViewById(R.id.btnDialogChange);
            mbtDialogChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(medtdialogPass.getText().toString().isEmpty() || medtdialogRePass.getText().toString().isEmpty()){
                        Toast.makeText(UserInfoActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }else if (!medtdialogPass.getText().toString().equals(medtdialogRePass.getText().toString())){
                        Toast.makeText(UserInfoActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        user.updatePassword(medtdialogPass.getText().toString());
                        Toast.makeText(UserInfoActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            });
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
        }
    }
    public class DeleteDialog extends Dialog{
        Button mbtnXN, mbtnHuy;
        public DeleteDialog(Context context){
            super(context);
            setContentView(R.layout.custom_dialog_delete);
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setDimAmount(0.6f);

            mbtnXN = findViewById(R.id.btnDialogXacNhan);
            mbtnXN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DeleteDialog.this != null && DeleteDialog.this.isShowing()) {
                        dismiss();
                    }
                    Intent intent = new Intent(UserInfoActivity.this, MaincuaYenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    user.delete();
                }
            });
            mbtnHuy = findViewById(R.id.btnDialogXoa);
            mbtnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DeleteDialog.this != null && DeleteDialog.this.isShowing()) {
                        dismiss();
                    }

                }
            });
        }
    }
    public class VerifyMailDialog extends Dialog{
        TextView mtvDialogReVerifyMail;
        Button mbtnDialogContinue;
        public VerifyMailDialog(Context context) {
            super(context);
            setContentView(R.layout.custom_dialog_verifymail);
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setDimAmount(0.6f);

            mtvDialogReVerifyMail = findViewById(R.id.tvDialogReVerifyMail);
            mbtnDialogContinue = findViewById(R.id.btnDialogContinue);

            mtvDialogReVerifyMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isResendClicked){
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                isResendClicked = true;    // Đánh dấu là button resend đã được nhấn 1 lần
                                setTimer();                // Thiết lập đếm ngược thời gian chờ giữa các lần gửi email xác minh
                                Toast.makeText(UserInfoActivity.this, "Yêu cầu gửi lại thành công", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserInfoActivity.this, "Yêu cầu gửi lại thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

            mbtnDialogContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            verification = user.isEmailVerified();
                            if(verification == false){
                                if (VerifyMailDialog.this != null && VerifyMailDialog.this.isShowing()) {
                                    dismiss();
                                }
                                firebaseAuth.signOut();

                                Toast.makeText(UserInfoActivity.this, "Email tài khoản chưa được xác nhận", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UserInfoActivity.this, MaincuaYenActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(UserInfoActivity.this, "Email đã được xác nhận", Toast.LENGTH_SHORT).show();
                                if (VerifyMailDialog.this != null && VerifyMailDialog.this.isShowing()) {
                                    dismiss();
                                }
                            }
                        }
                    });
                }
            });

        }
        private void setTimer() {
            countDownTimer = new CountDownTimer(60000, 1000) {  // 60000 milliseconds = 1 phút
                @Override
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / 1000;
                    // Hiển thị thời gian đếm ngược trên button resend
                    mtvDialogReVerifyMail.setText("Gửi lại trong " + seconds + "giây");
                }
                @Override
                public void onFinish() {
                    mtvDialogReVerifyMail.setText("Gửi lại xác nhận mail");  // Khi kết thúc đếm ngược, đổi lại nút gửi email xác minh thành "Resend"
                    isResendClicked = false;         // Reset lại biến isResendClicked
                }
            }.start();
        }
    }
    private void openChangePassDialog(int gravity){
        final Dialog dialog = new Dialog(UserInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_repass);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);
        EditText medtdialogPass = findViewById(R.id.edtdialogPass);
        EditText medtdialogRePass = findViewById(R.id.edtdialogRePass);
        Button mbtDialogChange = findViewById(R.id.btnDialogChange);

        mbtDialogChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
    }

}
