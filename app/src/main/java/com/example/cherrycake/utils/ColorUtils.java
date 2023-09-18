package com.example.cherrycake.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.example.cherrycake.R;

public class ColorUtils {

    public static int colorBrighter(int originalColor, float ratio) {
        int whiteColor = Color.parseColor("#FFFFFF");
        return androidx.core.graphics.ColorUtils.blendARGB(originalColor, whiteColor, ratio);
    }

    public static int colorWithOpacity(int color, float opacity) {
        return Color.argb(
                (int) (opacity * 255),
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        );
    }

    public static ColorStateList getBackgroundColorTintList(int color) {
        return ColorStateList.valueOf(color);
    }

    public static ColorStateList getColorDashboardTab(Context context, int color) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_selected},
                new int[]{}
        };

        int[] colors = new int[]{
                color,
                ContextCompat.getColor(context, R.color.teal_200)
        };

        return new ColorStateList(states, colors);
    }

    public static ColorStateList getColorTab(Context context, int color) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_selected},
                new int[]{}
        };

        int[] colors = new int[]{
                color,
                ContextCompat.getColor(context, R.color.teal_200)
        };

        return new ColorStateList(states, colors);
    }

    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = (int) (Color.red(color) * factor);
        int g = (int) (Color.green(color) * factor);
        int b = (int) (Color.blue(color) * factor);
        return Color.argb(
                a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255)
        );
    }
}
