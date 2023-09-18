package com.example.cherrycake.QuanLy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.cherrycake.QuanLy.Fragment.donhangFragment;
import com.example.cherrycake.QuanLy.Fragment.khoFragment;
import com.example.cherrycake.QuanLy.Fragment.userFragment;
import com.example.cherrycake.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeAdmin extends AppCompatActivity {

    MeowBottomNavigation btnav;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        btnav = findViewById(R.id.btnavadmin);
        loadFragment(new donhangFragment());
        btnav.show(1,true );
        btnav.add(new MeowBottomNavigation.Model(1, R.drawable.btnavhome));
        btnav.add(new MeowBottomNavigation.Model(2, R.drawable.btnavgrid));
        btnav.add(new MeowBottomNavigation.Model(3, R.drawable.btnavuserinf));
//        btnav.add(new MeowBottomNavigation.Model(4, R.drawable.btnavuserinf));
        btnav.setEnabled(false);

        btnav.getId();
        btnav.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch (model.getId()){
                    case 1:
                        loadFragment(new donhangFragment());
//                        loadFragment(new HomeFragmentActivity());

//                        Intent homeintent = new Intent(MainActivity.this,);
//                        startActivity(homeintent);
                        break;
                    case 2:
                        loadFragment(new khoFragment());
//                        Intent categoryintent = new Intent(HomeActivity.this,ProductActivity.class);
//                        startActivity(categoryintent);
                        break;
                    case 3:
                        loadFragment(new userFragment());
//                        loadFragment(new UserFragmentActivity());
//                        Intent favouritelistintent = new Intent(MainActivity.this, );
//                        startActivity(favouritelistintent);
                        break;
//                    case 4:
//
////                        mdrawLo.openDrawer(GravityCompat.START);
//                        break;
                }
                return null;
            }
        });
        btnav.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                return null;
            }
        });
    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction fmtrans = getSupportFragmentManager().beginTransaction();
        fmtrans.replace(R.id.homeadmin_fragment,fragment);
        fmtrans.addToBackStack(null);
        fmtrans.commit();
    }
}
