package com.example.cherrycake.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cherrycake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class otpactivity extends AppCompatActivity {
    private Button mbtntieptheo2;
    private TextView tvnhapotp, tvdnotp;
    private EditText medtmailotp;
    private FirebaseAuth mAuth;
    private boolean isResendClicked = false;   // Kiểm tra xem button resend đã được nhấn bao nhiêu lần
    private CountDownTimer countDownTimer;
    FirebaseUser mUser;
    Boolean verification;

    @Override
    protected void onStart() {
        super.onStart();
        String email = mUser.getEmail();
        medtmailotp.setText(email);
    //    String current_user_uid = mUser.getUid();
      //  medtotp.setText(current_user_uid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        tvdnotp = findViewById(R.id.tvdntrongotp);
        mbtntieptheo2 = findViewById(R.id.btntieptheo2);
        tvnhapotp = findViewById(R.id.tvnhanotp);
        medtmailotp = findViewById(R.id.edtemailotp);

        tvnhapotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isResendClicked) {   // Nếu button resend chưa được nhấn trong lần nào trước đó
                    mUser.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(otpactivity.this, "Vui lòng xác nhận gmail " + mUser.getEmail(), Toast.LENGTH_SHORT).show();
                                        isResendClicked = true;    // Đánh dấu là button resend đã được nhấn 1 lần
                                        setTimer();                // Thiết lập đếm ngược thời gian chờ giữa các lần gửi email xác minh
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Nhận OTP thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng đợi trong giây lát", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mbtntieptheo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            verification = mUser.isEmailVerified();
                            if (verification == true) {
                                Intent intent = new Intent(otpactivity.this, HoanThanhActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(otpactivity.this, "Vui lòng xác nhận gmail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
//                    if (verification == true) {
//                        Intent intent = new Intent(otpactivity.this, HomeActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(otpactivity.this, "Vui lòng xác nhận gmail", Toast.LENGTH_SHORT).show();
//                    }
            }
        });


        tvdnotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(otpactivity.this, MaincuaYenActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setTimer() {
        countDownTimer = new CountDownTimer(60000, 1000) {  // 60000 milliseconds = 1 phút
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                // Hiển thị thời gian đếm ngược trên button resend
                tvnhapotp.setText("Resend in " + seconds + "s");
            }
            @Override
            public void onFinish() {
                tvnhapotp.setText("Resend");  // Khi kết thúc đếm ngược, đổi lại nút gửi email xác minh thành "Resend"
                isResendClicked = false;         // Reset lại biến isResendClicked
            }
        }.start();
    }
}