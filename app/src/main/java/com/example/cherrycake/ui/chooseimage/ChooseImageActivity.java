//package com.example.cherrycake.ui.chooseimage;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.cherrycake.Home.HomeActivity;
//import com.example.cherrycake.data.model.Cake;
//import com.example.cherrycake.ui.result.ResultActivity;
//import com.example.thietkebanh.ui.custom.GridSpacingItemDecoration;
//
//public class ChooseImageActivity extends AppCompatActivity {
//    ActivityChooseImageBinding binding;
//
//    ChooseImageAdapter adapter = new ChooseImageAdapter();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityChooseImageBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        initActions();
//        Cake cake = (Cake) getIntent().getSerializableExtra("cake");
//        if (cake != null) {
//            binding.cake.setCake(cake);
//        }
//
//        binding.tvTitle.setText("");
//
//        initViews();
//    }
//
//    private void initViews() {
//        adapter.setItemClick((ChooseImageAdapter.OnItemClick) image -> {
//            binding.cake.setImageResource(image.getResource());
//        });
//        binding.rvChooseImage.setAdapter(adapter);
//        binding.rvChooseImage.addItemDecoration(new GridSpacingItemDecoration(3, 25, 15, false));
//    }
//
//    private void initActions() {
//        binding.ivBack.setOnClickListener(view -> finish());
//
//        binding.ivHome.setOnClickListener(view -> {
//            Intent i = new Intent(ChooseImageActivity.this, HomeActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(i);
//        });
//
//        binding.tvNext.setOnClickListener(view -> {
//            Cake cake = binding.cake.getCake();
//            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
//            intent.putExtra("cake", cake);
//            startActivity(intent);
//        });
//    }
//}