package com.example.dhobiwala.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.dhobiwala.Fragments.AccountFragment;
import com.example.dhobiwala.Fragments.CartFragment;
import com.example.dhobiwala.Fragments.HomeFragment;
import com.example.dhobiwala.Fragments.OrdersFragment;
import com.example.dhobiwala.Fragments.PromotionsFragment;
import com.example.dhobiwala.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "HomeActivity";

    private BottomNavigationView bottomNavigationView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        setContentView(R.layout.fit_in_grid_layout);
        Log.d(TAG, "onCreate: ");
        init();

//        calculate_PrintCost();

    }

    private void init() {
        bottomNavigationView=findViewById(R.id.home_bottomNavgationViewID);
        bottomNavigationView.setOnNavigationItemSelectedListener(HomeActivity.this);
        bottomNavigationView.setSelectedItemId(R.id.home_navigaionButtonId);
    }

    HomeFragment mHomeFragment = new HomeFragment();
    OrdersFragment mOrdersFragment= new OrdersFragment();
    CartFragment mCartFragment =new CartFragment();
    PromotionsFragment mPromotionsFragment =new PromotionsFragment();
    AccountFragment mAccountFragment =new AccountFragment() ;


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d(TAG, "onNavigationItemSelected: ");
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction() ;
        switch(menuItem.getItemId()){
            case R.id.home_navigaionButtonId:
                fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.containerFrame_homeID,mHomeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit() ;
                return true ;
            case R.id.myOrders_navigaionButtonId:
                fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.containerFrame_homeID,mOrdersFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true ;

            case R.id.promotions_navigaionButtonId:
                fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.containerFrame_homeID,mPromotionsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;

            case R.id.cart_navigaionButtonId:
                fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.containerFrame_homeID,mCartFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;


            case R.id.account_navigaionButtonId:
                fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.containerFrame_homeID,mAccountFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        bottomNavigationView.setSelectedItemId(R.id.home_navigaionButtonId);
    }







}