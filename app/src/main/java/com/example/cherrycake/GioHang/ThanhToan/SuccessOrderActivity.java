package com.example.cherrycake.GioHang.ThanhToan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cherrycake.Home.HomeActivity;
import com.example.cherrycake.R;

public class SuccessOrderActivity extends AppCompatActivity {
    Button btnTiepTuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_order);

        btnTiepTuc = findViewById(R.id.btnTiepTucMuahang);

        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuccessOrderActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });


    }
}