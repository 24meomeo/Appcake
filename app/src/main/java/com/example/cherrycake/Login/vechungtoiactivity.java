package com.example.cherrycake.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cherrycake.Home.HomeActivity;
import com.example.cherrycake.R;

public class vechungtoiactivity extends AppCompatActivity {
    private Button mbtntrangchu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vechungtoiactivity);

        mbtntrangchu = findViewById(R.id.btntrangchu);
        trovetrangchu();

    }

    private void trovetrangchu() {
        mbtntrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vechungtoiactivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });
    }
}