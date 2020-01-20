package com.example.dhobiwala.Activities;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.dhobiwala.Adapters.SwipeAdapter;
import com.example.dhobiwala.Fragments.BottomFragment;
import com.example.dhobiwala.Fragments.DressFragment;
import com.example.dhobiwala.Fragments.HouseholdFragment;
import com.example.dhobiwala.Fragments.TopFragment;
import com.example.dhobiwala.Helper.PutExtraHelper;
import com.example.dhobiwala.R;

public class ClothsSelectActivity extends AppCompatActivity implements TopFragment.OnAmountChanged, BottomFragment.OnAmountChanged_bottom, DressFragment.OnAmountChanged_dress, HouseholdFragment.OnAmountChanged_household {
    int amount_top,quantity_top,amount_bottom,quantity_bottom,amount_dress,quantity_dress,amount_household,quantity_household;
    TextView quantity_wash_fold,amount_wash_fold;
    ViewPager viewPager;
    public static Bundle myArgs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloths_select);


        myArgs.putString(PutExtraHelper.service_type, getIntent().getStringExtra(PutExtraHelper.service_type));

        quantity_wash_fold = findViewById(R.id.quantity_wash_fold);
        amount_wash_fold = findViewById(R.id.amount_wash_fold);
        viewPager = findViewById(R.id.cloth_pager_container);
        viewPager.setOffscreenPageLimit(4);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(0);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void amountChanged_top(int change_in_amt,int change_in_quantity) {

        quantity_top = change_in_quantity;
        amount_top = change_in_amt;

        int total_amt = amount_top+amount_bottom+amount_dress+amount_household;
        int total_qut = quantity_top+quantity_bottom+quantity_dress+quantity_household;;
        amount_wash_fold.setText("Total:"+total_amt);
        quantity_wash_fold.setText(total_qut+" Items");
    }


    @Override
    public void amountChanged_bottom(int bottom_amount, int bottom_quntity) {
        amount_bottom = bottom_amount;
        quantity_bottom = bottom_quntity;
        int total_amt = amount_top+amount_bottom+amount_dress+amount_household;
        int total_qut = quantity_top+quantity_bottom+quantity_dress+quantity_household;
        amount_wash_fold.setText("Total:"+total_amt);
        quantity_wash_fold.setText(total_qut+" Items");
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void amountChanged_dress(int dress_amount, int dress_quntity) {
        amount_dress = dress_amount;
        quantity_dress = dress_quntity;
        int total_amt = amount_top+amount_bottom+amount_dress+amount_household;
        int total_qut = quantity_top+quantity_bottom+quantity_dress+quantity_household;
        amount_wash_fold.setText("Total:"+total_amt);
        quantity_wash_fold.setText(total_qut+" Items");
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void amountChanged_household(int household_amount, int household_quntity) {
        amount_household = household_amount;
        quantity_household = household_quntity;
        int total_amt = amount_top+amount_bottom+amount_dress+amount_household;
        int total_qut = quantity_top+quantity_bottom+quantity_dress+quantity_household;
        amount_wash_fold.setText("Total:"+total_amt);
        quantity_wash_fold.setText(total_qut+" Items");
    }
}