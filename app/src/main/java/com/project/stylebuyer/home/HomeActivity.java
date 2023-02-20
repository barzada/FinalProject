package com.project.stylebuyer.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.stylebuyer.R;

public class HomeActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private OrdersFragment ordersFragment;
    private BottomNavigationView bottom_navigation;
    private FrameLayout homeFrame, profileFrame, ordersFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViews();
        initVars();

    }

    private void initVars() {
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_home:
                        homeFrame.setVisibility(View.VISIBLE);
                        ordersFrame.setVisibility(View.INVISIBLE);
                        profileFrame.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.menu_orders:
                        ordersFrame.setVisibility(View.VISIBLE);
                        homeFrame.setVisibility(View.INVISIBLE);
                        profileFrame.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.menu_profile:
                        profileFrame.setVisibility(View.VISIBLE);
                        homeFrame.setVisibility(View.INVISIBLE);
                        ordersFrame.setVisibility(View.INVISIBLE);
                        break;
                }
                return true;
            }
        });

        homeFragment = new HomeFragment(this);
        profileFragment = new ProfileFragment(this);
        ordersFragment = new OrdersFragment(this);

        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_home, homeFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_profile, profileFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_orders, ordersFragment).commit();
        ordersFrame.setVisibility(View.INVISIBLE);
        profileFrame.setVisibility(View.INVISIBLE);
    }


    public void findViews(){
        bottom_navigation = findViewById(R.id.bottom_navigation);
        homeFrame = findViewById(R.id.home_frame_home);
        ordersFrame = findViewById(R.id.home_frame_orders);
        profileFrame = findViewById(R.id.home_frame_profile);

    }
}