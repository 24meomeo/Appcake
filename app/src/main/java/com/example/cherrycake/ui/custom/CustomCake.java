package com.example.cherrycake.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cherrycake.data.model.Cake;
import com.example.cherrycake.databinding.ViewCakeCustomBinding;
import com.example.cherrycake.utils.ColorUtils;


public class CustomCake extends ConstraintLayout {
    private ViewCakeCustomBinding binding;
    private Cake cake = new Cake();

    public CustomCake(Context context) {
        super(context);
        init();
    }

    public CustomCake(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCake(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        binding = ViewCakeCustomBinding.inflate(LayoutInflater.from(getContext()), this, true);
        // Vẽ hình tròn mặc định khi khởi tạo lớp View
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(width);
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }


    public void setImageResource(int resId) {
        this.cake.setImage(resId);
        binding.imageView.setImageResource(resId);
    }

    public void changeSizeCake(int radius) {
        this.cake.setRadius(radius);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = radius;
        params.height = radius;
        setLayoutParams(params);
        // Yêu cầu CustomView vẽ lại
        requestLayout();
    }

    public void setColorCake(int colorCake) {
        this.cake.setColor(colorCake);
        binding.getRoot().setBackgroundTintList(ColorUtils.getBackgroundColorTintList(colorCake));
    }

    public void setCake(Cake cake){
        setColorCake(cake.getColor());
        changeSizeCake(cake.getRadius());
        setImageResource(cake.getImage());
    }

    public Cake getCake() {
        return cake;
    }
}
