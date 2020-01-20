package com.example.dhobiwala.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.dhobiwala.Activities.ClothsSelectActivity;
import com.example.dhobiwala.Fragments.BottomFragment;
import com.example.dhobiwala.Fragments.DressFragment;
import com.example.dhobiwala.Fragments.HouseholdFragment;
import com.example.dhobiwala.Fragments.TopFragment;

public class SwipeAdapter extends FragmentStatePagerAdapter {

    public SwipeAdapter(FragmentManager fm){
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment pageFragment = null;
        switch (position){
            case 0:
                pageFragment = new TopFragment();
                pageFragment.setArguments(ClothsSelectActivity.myArgs);
//                transaction.add(R.id.fragment_container, pageFragment, "tag").commit();
                break;
            case 1:
                pageFragment = new BottomFragment();
                break;
            case 2:
                pageFragment = new DressFragment();
                break;
            case 3:
                pageFragment = new HouseholdFragment();
                break;

        }
        return pageFragment;
    }

    @Override
    public int getCount(){
        return 4;
    }
}