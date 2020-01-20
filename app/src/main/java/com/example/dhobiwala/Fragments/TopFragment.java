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
public class TopFragment extends Fragment {

    private int counter_1,counter_2,counter_3;
    private Button plus_1,minus_1,plus_2,minus_2,plus_3,minus_3;
    private TextView number_1,number_2,number_3;
    private OnAmountChanged onAmountChanged;

    int top_quntity=0;
    int top_amount=0;
    View view;


    public interface OnAmountChanged{
        public void amountChanged_top(int top_amount, int top_quntity);
    }

    public TopFragment() {
        // Required empty public constructor
    }

    private View.OnClickListener clickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.plus_1_top:
                    plusCounter(1);
                    break;
                case R.id.minus_1_top:
                    minusCounter(1);
                    break;
                case R.id.plus_2_top:
                    plusCounter(2);
                    break;
                case R.id.minus_2_top:
                    minusCounter(2);
                    break;
                case R.id.plus_3_top:
                    plusCounter(3);
                    break;
                case R.id.minus_3_top:
                    minusCounter(3);
                    break;

            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top, container, false);

        plus_1 = view.findViewById(R.id.plus_1_top);
        plus_1.setOnClickListener(clickListner);
        minus_1 = view.findViewById(R.id.minus_1_top);
        minus_1.setOnClickListener(clickListner);
        number_1 = view.findViewById(R.id.number_1_top);

        plus_2 = view.findViewById(R.id.plus_2_top);
        plus_2.setOnClickListener(clickListner);
        minus_2 = view.findViewById(R.id.minus_2_top);
        minus_2.setOnClickListener(clickListner);
        number_2 = view.findViewById(R.id.number_2_top);

        plus_3 = view.findViewById(R.id.plus_3_top);
        plus_3.setOnClickListener(clickListner);
        minus_3 = view.findViewById(R.id.minus_3_top);
        minus_3.setOnClickListener(clickListner);
        number_3 = view.findViewById(R.id.number_3_top);


        return view;
    }

//    private void initCounter(int button_number){
//
//        switch(button_number){
//
//            case 1:
//                    counter_1 = 0;
//                    break;
//
//            case 2:
//                    counter_2 = 0;
//                    break;
//
//            case 3:
//                    counter_3 = 0;
//                    break;
//
//        }
//    }



    private void plusCounter(int button_number){
        switch(button_number){

            case 1:
                    counter_1++;
                    number_1.setText(counter_1+"");
                    top_quntity = counter_1+counter_2+counter_3;
                    top_amount = counter_1*5 + counter_2*6 + counter_3*5;
                    onAmountChanged.amountChanged_top(top_amount,top_quntity);
                    break;

            case 2:
                    counter_2++;
                    number_2.setText(counter_2+"");
                    top_quntity = counter_1+counter_2+counter_3;
                    top_amount = counter_1*5 + counter_2*6 + counter_3*5;
                    onAmountChanged.amountChanged_top(top_amount,top_quntity);
                    break;

            case 3:
                    counter_3++;
                    number_3.setText(counter_3+"");
                    top_quntity = counter_1+counter_2+counter_3;
                    top_amount = counter_1*5 + counter_2*6 + counter_3*5;
                    onAmountChanged.amountChanged_top(top_amount,top_quntity);
                    break;

        }

    }
    private void minusCounter(int button_number){
        switch(button_number){

            case 1:
                    counter_1--;
                    number_1.setText(counter_1+"");
                    top_quntity = counter_1+counter_2+counter_3;
                    top_amount = counter_1*5 + counter_2*6 + counter_3*5;
                    onAmountChanged.amountChanged_top(top_amount,top_quntity);
                    break;

            case 2:
                    counter_2--;
                    number_2.setText(counter_2+"");
                    top_quntity = counter_1+counter_2+counter_3;
                    top_amount = counter_1*5 + counter_2*6 + counter_3*5;
                    onAmountChanged.amountChanged_top(top_amount,top_quntity);
                    break;

            case 3:
                    counter_3--;
                    number_3.setText(counter_3+"");
                    top_quntity = counter_1+counter_2+counter_3;
                    top_amount = counter_1*5 + counter_2*6 + counter_3*5;
                    onAmountChanged.amountChanged_top(top_amount,top_quntity);
                    break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity)context;

        try{
            onAmountChanged = (OnAmountChanged)activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()+"must implements that interface method");
        }
    }
}