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
public class DressFragment extends Fragment {


    public DressFragment() {
        // Required empty public constructor
    }

    private Button plus_1,minus_1,plus_2,minus_2,plus_3,minus_3;
    private TextView number_1,number_2,number_3;
    private int counter_1,counter_2,counter_3;

    int dress_quntity=0;
    int dress_amount=0;

    View view;

    private OnAmountChanged_dress onAmountChanged_dress;



    public interface OnAmountChanged_dress{
        public void amountChanged_dress(int dress_amount, int dress_quntity);
    }

    private View.OnClickListener clickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.plus_1_dress:
                    plusCounter(1);
                    break;
                case R.id.minus_1_dress:
                    minusCounter(1);
                    break;
                case R.id.plus_2_dress:
                    plusCounter(2);
                    break;
                case R.id.minus_2_dress:
                    minusCounter(2);
                    break;
                case R.id.plus_3_dress:
                    plusCounter(3);
                    break;
                case R.id.minus_3_dress:
                    minusCounter(3);
                    break;

            }

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dress, container, false);

        plus_1 = view.findViewById(R.id.plus_1_dress);
        plus_1.setOnClickListener(clickListner);
        minus_1 = view.findViewById(R.id.minus_1_dress);
        minus_1.setOnClickListener(clickListner);
        number_1 = view.findViewById(R.id.number_1_dress);

        plus_2 = view.findViewById(R.id.plus_2_dress);
        plus_2.setOnClickListener(clickListner);
        minus_2 = view.findViewById(R.id.minus_2_dress);
        minus_2.setOnClickListener(clickListner);
        number_2 = view.findViewById(R.id.number_2_dress);

        plus_3 = view.findViewById(R.id.plus_3_dress);
        plus_3.setOnClickListener(clickListner);
        minus_3 = view.findViewById(R.id.minus_3_dress);
        minus_3.setOnClickListener(clickListner);
        number_3 = view.findViewById(R.id.number_3_dress);


        return view;

    }


    private void plusCounter(int button_number){
        switch(button_number){

            case 1:
                counter_1++;
                number_1.setText(counter_1+"");
                dress_quntity = counter_1+counter_2+counter_3;
                dress_amount = counter_1*15 + counter_2*10 + counter_3*20;
                onAmountChanged_dress.amountChanged_dress(dress_amount,dress_quntity);
                break;

            case 2:
                counter_2++;
                number_2.setText(counter_2+"");
                dress_quntity = counter_1+counter_2+counter_3;
                dress_amount = counter_1*15 + counter_2*10 + counter_3*20;
                onAmountChanged_dress.amountChanged_dress(dress_amount,dress_quntity);
                break;

            case 3:
                counter_3++;
                number_3.setText(counter_3+"");
                dress_quntity = counter_1+counter_2+counter_3;
                dress_amount = counter_1*15 + counter_2*10 + counter_3*20;
                onAmountChanged_dress.amountChanged_dress(dress_amount,dress_quntity);
                break;

        }

    }
    private void minusCounter(int button_number){
        switch(button_number){

            case 1:
                counter_1--;
                number_1.setText(counter_1+"");
                dress_quntity = counter_1+counter_2+counter_3;
                dress_amount = counter_1*15 + counter_2*10 + counter_3*20;
                onAmountChanged_dress.amountChanged_dress(dress_amount,dress_quntity);
                break;

            case 2:
                counter_2--;
                number_2.setText(counter_2+"");
                dress_quntity = counter_1+counter_2+counter_3;
                dress_amount = counter_1*15 + counter_2*10 + counter_3*20;
                onAmountChanged_dress.amountChanged_dress(dress_amount,dress_quntity);
                break;

            case 3:
                counter_3--;
                number_3.setText(counter_3+"");
                dress_quntity = counter_1+counter_2+counter_3;
                dress_amount = counter_1*15 + counter_2*10 + counter_3*20;
                onAmountChanged_dress.amountChanged_dress(dress_amount,dress_quntity);
                break;

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity)context;

        try{
            onAmountChanged_dress = (OnAmountChanged_dress) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()+"must implements that interface method");
        }
    }

}
