//package com.example.cherrycake.ui.result;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AppCompatButton;
//
//import com.example.cherrycake.Home.HomeActivity;
//import com.example.cherrycake.GioHang.MyCartModel;
//import com.example.cherrycake.R;
//import com.example.cherrycake.data.model.Cake;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//
//public class ResultActivity extends AppCompatActivity {
//    ActivityResultBinding binding;
//    int count = 1;
//    TextView tvQuantity;
//    AppCompatButton btnOrder;
//    FirebaseAuth auth;
//    private FirebaseFirestore firestore;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityResultBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        Cake cake = (Cake) getIntent().getSerializableExtra("cake");
//        tvQuantity = (TextView) findViewById(R.id.tvQuantity);
//        btnOrder = (AppCompatButton) findViewById(R.id.btnOrder);
//        if (cake != null) {
//            binding.cake.setCake(cake);
//        }
//
//        binding.tvTitle.setText(getString(R.string.reult));
//        // firebase
//        firestore = FirebaseFirestore.getInstance();
//        auth = FirebaseAuth.getInstance();
//        initActions();
//    }
//
//    public void increment(View v) {
//        count++;
//        tvQuantity.setText("" + count);
//    }
//
//    // Giảm số lượng //
//    public void decrement(View v) {
//        // Nếu nhập số lượng nhỏ hơn bằng 1 thì set mặt định số lượng bằng 1
//        if (count <= 1) {
//            count = 1;
//        } else {
//            count--;
//        }
//        tvQuantity.setText("" + count);
//    }
//
//    private void initActions() {
//        btnOrder.setOnClickListener(view -> {
//            final HashMap<String, Object> cartMap = new HashMap<>();
//
////        lấy hình ảnh khi người dùng click chọn sản phẩm
//            String anh = "https://firebasestorage.googleapis.com/v0/b/cherrycake-31cce.appspot.com/o/B%C3%A1nh%20kem%2Fbk8.jpg?alt=media&token=a0067463-15fc-4c6e-bdd4-8cb119053d11";
//            cartMap.put("name", "Chiếc bánh của bạn thật là đẹp");
//            cartMap.put("price", 250000);
//            cartMap.put("totalQuantity", Integer.parseInt(tvQuantity.getText().toString()));
//            cartMap.put("anh", anh);  // thêm field hình ảnh để lưu trữ trong firebase
//
//            CollectionReference collectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid()).collection("AddToCart");
//
//            collectionReference.get().addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    List<DocumentSnapshot> collect = task.getResult().getDocuments().stream().
//                            filter(s -> Objects.requireNonNull(s.toObject(MyCartModel.class))
//                                    .getName().contains("Chiếc bánh của bạn thật là đẹp"))
//                            .collect(Collectors.toList());
//
//                    Log.d("Catr", "collect: " + collect.isEmpty());
//                    if (!collect.isEmpty()) {
//                        Log.d("Catr", "addToCart: " + collect.get(0).getId());
//                        MyCartModel myCartModel = collect.get(0).toObject(MyCartModel.class);
//                        myCartModel.setId(collect.get(0).getId());
//                        myCartModel.setTotalQuantity(myCartModel.getTotalQuantity() + Integer.parseInt(tvQuantity.getText().toString()));
//                        collectionReference.document(myCartModel.getId()).set(myCartModel);
//                        Toast.makeText(ResultActivity.this, "Cập nhật giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//                    } else {
//                        collectionReference.add(cartMap);
//                        Toast.makeText(ResultActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        });
//
//        binding.ivBack.setOnClickListener(view -> finish());
//
//        binding.ivHome.setOnClickListener(view -> {
//            Intent i = new Intent(this, HomeActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(i);
//        });
//    }
//
//}