//package com.example.cherrycake.ui.choosecolo;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.cherrycake.R;
//import com.example.cherrycake.data.model.Cake;
//import com.example.cherrycake.data.shaf.LocalSharedPreferences;
//import com.example.cherrycake.data.shaf.LocalSharedPreferencesImp;
//import com.example.cherrycake.databinding.ActivityMainBinding;
//import com.example.cherrycake.ui.chooseimage.ChooseImageActivity;
//import com.example.cherrycake.utils.DisplayUtils;
//import com.example.cherrycake.ui.custom.ColorSelectionSectionView;
//import com.flask.colorpicker.ColorPickerView;
//import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
//
//
//public class MainActivity extends AppCompatActivity implements ColorSelectionSectionView.OnColorSelectedChangedListener {
//    ActivityMainBinding binding;
//    LocalSharedPreferences sharedPreferences;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        sharedPreferences = new LocalSharedPreferencesImp(this);
//        initViews();
//        initActions();
//    }
//
//    private void initViews() {
//        binding.ivHome.setOnClickListener(view -> finish());
//        binding.cake.setColorCake(binding.selectColorView.getSelectedColor());
//    }
//
//    private void initActions() {
//        binding.selectColorView.setListener(this);
//
//        binding.radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
//            switch (checkedId) {
//                case R.id.size1:
//                    binding.cake.changeSizeCake(DisplayUtils.intToDp(140, this));
//                    break;
//                case R.id.size2:
//                    binding.cake.changeSizeCake(DisplayUtils.intToDp(160, this));
//                    break;
//                case R.id.size3:
//                    binding.cake.changeSizeCake(DisplayUtils.intToDp(180, this));
//                    break;
//                case R.id.size4:
//                    binding.cake.changeSizeCake(DisplayUtils.intToDp(200, this));
//                    break;
//            }
//        });
//
//
//        binding.btnOk.setOnClickListener(v -> {
//            Cake cake = new Cake(binding.cake.getWidth(), binding.selectColorView.getSelectedColor(), R.drawable.bear);
//            Intent intent = new Intent(this, ChooseImageActivity.class);
//            intent.putExtra("cake", cake);
//            startActivity(intent);
//        });
//    }
//
//    private void onColorSelected(int color) {
//        binding.cake.setColorCake(color);
//    }
//
//    @Override
//    public void onColorSelected(@NonNull ColorSelectionSectionView view, int color) {
//        onColorSelected(color);
//        binding.selectColorView.setSelectedColor(color);
//    }
//
//    @Override
//    public void onOtherColorSelected(@NonNull ColorSelectionSectionView view) {
//        ColorPickerDialogBuilder
//                .with(this)
//                .setTitle("Choose color")
//                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
//                .density(12)
//                .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
//                    sharedPreferences.saveOtherColor(selectedColor);
//                    onColorSelected(selectedColor);
//                    binding.selectColorView.setSelectedColor(selectedColor);
//                }).build().show();
//    }
//}