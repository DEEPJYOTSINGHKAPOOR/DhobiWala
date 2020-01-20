package com.example.dhobiwala.Fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dhobiwala.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    private Button plus_1,minus_1,plus_2,minus_2,plus_3,minus_3;
    private TextView number_1,number_2,number_3;
    private int counter_1,counter_2,counter_3;

    int bottom_quntity=0;
    int bottom_amount=0;

    View view;

    private OnAmountChanged_bottom onAmountChanged_bottom;


    public BottomFragment() {
        // Required empty public constructor
    }

    public interface OnAmountChanged_bottom{
        public void amountChanged_bottom(int bottom_amount, int bottom_quntity);
    }

    private View.OnClickListener clickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.plus_1_bottom:
                    plusCounter(1);
                    break;
                case R.id.minus_1_bottom:
                    minusCounter(1);
                    break;
                case R.id.plus_2_bottom:
                    plusCounter(2);
                    break;
                case R.id.minus_2_bottom:
                    minusCounter(2);
                    break;
                case R.id.plus_3_bottom:
                    plusCounter(3);
                    break;
                case R.id.minus_3_bottom:
                    minusCounter(3);
                    break;

            }

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom, container, false);

        plus_1 = view.findViewById(R.id.plus_1_bottom);
        plus_1.setOnClickListener(clickListner);
        minus_1 = view.findViewById(R.id.minus_1_bottom);
        minus_1.setOnClickListener(clickListner);
        number_1 = view.findViewById(R.id.number_1_bottom);

        plus_2 = view.findViewById(R.id.plus_2_bottom);
        plus_2.setOnClickListener(clickListner);
        minus_2 = view.findViewById(R.id.minus_2_bottom);
        minus_2.setOnClickListener(clickListner);
        number_2 = view.findViewById(R.id.number_2_bottom);

        plus_3 = view.findViewById(R.id.plus_3_bottom);
        plus_3.setOnClickListener(clickListner);
        minus_3 = view.findViewById(R.id.minus_3_bottom);
        minus_3.setOnClickListener(clickListner);
        number_3 = view.findViewById(R.id.number_3_bottom);


        return view;
    }



    private void plusCounter(int button_number){
        switch(button_number){

            case 1:
                counter_1++;
                number_1.setText(counter_1+"");
                bottom_quntity = counter_1+counter_2+counter_3;
                bottom_amount = counter_1*5 + counter_2*15 + counter_3*10;
                onAmountChanged_bottom.amountChanged_bottom(bottom_amount,bottom_quntity);
                break;

            case 2:
                counter_2++;
                number_2.setText(counter_2+"");
                bottom_quntity = counter_1+counter_2+counter_3;
                bottom_amount = counter_1*5 + counter_2*15 + counter_3*10;
                onAmountChanged_bottom.amountChanged_bottom(bottom_amount,bottom_quntity);
                break;

            case 3:
                counter_3++;
                number_3.setText(counter_3+"");
                bottom_quntity = counter_1+counter_2+counter_3;
                bottom_amount = counter_1*5 + counter_2*15 + counter_3*10;
                onAmountChanged_bottom.amountChanged_bottom(bottom_amount,bottom_quntity);
                break;

        }

    }
    private void minusCounter(int button_number){
        switch(button_number){

            case 1:
                counter_1--;
                number_1.setText(counter_1+"");
                bottom_quntity = counter_1+counter_2+counter_3;
                bottom_amount = counter_1*5 + counter_2*15 + counter_3*10;
                onAmountChanged_bottom.amountChanged_bottom(bottom_amount,bottom_quntity);
                break;

            case 2:
                counter_2--;
                number_2.setText(counter_2+"");
                bottom_quntity = counter_1+counter_2+counter_3;
                bottom_amount = counter_1*5 + counter_2*15 + counter_3*10;
                onAmountChanged_bottom.amountChanged_bottom(bottom_amount,bottom_quntity);
                break;

            case 3:
                counter_3--;
                number_3.setText(counter_3+"");
                bottom_quntity = counter_1+counter_2+counter_3;
                bottom_amount = counter_1*5 + counter_2*15 + counter_3*10;
                onAmountChanged_bottom.amountChanged_bottom(bottom_amount,bottom_quntity);
                break;

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity)context;

        try{
            onAmountChanged_bottom = (OnAmountChanged_bottom) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()+"must implements that interface method");
        }
    }



}
