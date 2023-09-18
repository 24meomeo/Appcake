package com.example.cherrycake.utils;

import android.content.Context;
import android.util.TypedValue;

public class DisplayUtils {
    public static int intToDp(int number, Context context) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, number, context.getResources().getDisplayMetrics()));
    }
}
