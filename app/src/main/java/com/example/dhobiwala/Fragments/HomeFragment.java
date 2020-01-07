package com.example.dhobiwala.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.dhobiwala.Activities.HomeActivity;
import com.example.dhobiwala.Adapters.HomeAdapter;
import com.example.dhobiwala.Models.HomeModel;
import com.example.dhobiwala.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    
    private GridView mGridView;
    private HomeAdapter mHomeAdapter;
    private ArrayList<HomeModel> mArrayList;

    private TextView mUserName;

    
    private String userName1 ="AMAN" ;

    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_home,container,false);

        Context thisContext = container.getContext() ;

        mGridView=view.findViewById(R.id.home_gridViewId);
        mUserName =view.findViewById(R.id.homeFragment_helloTextId);



        mArrayList=new ArrayList<>();
        mArrayList.add(new HomeModel(R.drawable.wash_and_fold));
        mArrayList.add(new HomeModel(R.drawable.iron));
        mArrayList.add(new HomeModel(R.drawable.wash_and_iron));
        mArrayList.add(new HomeModel(R.drawable.dry_clean));
        mHomeAdapter=new HomeAdapter(thisContext,mArrayList);
        mGridView.setAdapter(mHomeAdapter);
        
        
        //Shared prefernce for name : 
        SharedPreferences mSharedPreferences =thisContext.getSharedPreferences("USER_DETAILS_SHARED_PREFERENCE",MODE_PRIVATE);
        userName1=mSharedPreferences.getString("NAME_OF_USER","");

        String[] fullName=userName1.split(" ");

        mUserName.setText(fullName[0]);
        
        return view;

    }

}
