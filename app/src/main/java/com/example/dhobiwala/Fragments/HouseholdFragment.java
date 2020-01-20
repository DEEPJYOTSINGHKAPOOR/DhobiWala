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
public class HouseholdFragment extends Fragment {

    private int counter_1,counter_2,counter_3;
    private Button plus_1,minus_1,plus_2,minus_2,plus_3,minus_3;
    private TextView number_1,number_2,number_3;
    private OnAmountChanged_household onAmountChanged_household;

    int household_quntity=0;
    int household_amount=0;
    View view;

    public interface OnAmountChanged_household{
        public void amountChanged_household(int dress_amount, int dress_quntity);
    }


    private View.OnClickListener clickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.plus_1_household:
                    plusCounter(1);
                    break;
                case R.id.minus_1_household:
                    minusCounter(1);
                    break;
                case R.id.plus_2_household:
                    plusCounter(2);
                    break;
                case R.id.minus_2_household:
                    minusCounter(2);
                    break;
                case R.id.plus_3_household:
                    plusCounter(3);
                    break;
                case R.id.minus_3_household:
                    minusCounter(3);
                    break;

            }

        }
    };


    public HouseholdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_household, container, false);

        plus_1 = view.findViewById(R.id.plus_1_household);
        plus_1.setOnClickListener(clickListner);
        minus_1 = view.findViewById(R.id.minus_1_household);
        minus_1.setOnClickListener(clickListner);
        number_1 = view.findViewById(R.id.number_1_household);

        plus_2 = view.findViewById(R.id.plus_2_household);
        plus_2.setOnClickListener(clickListner);
        minus_2 = view.findViewById(R.id.minus_2_household);
        minus_2.setOnClickListener(clickListner);
        number_2 = view.findViewById(R.id.number_2_household);

        plus_3 = view.findViewById(R.id.plus_3_household);
        plus_3.setOnClickListener(clickListner);
        minus_3 = view.findViewById(R.id.minus_3_household);
        minus_3.setOnClickListener(clickListner);
        number_3 = view.findViewById(R.id.number_3_household);



        return view;
    }



    private void plusCounter(int button_number){
        switch(button_number){

            case 1:
                counter_1++;
                number_1.setText(counter_1+"");
                household_quntity = counter_1+counter_2+counter_3;
                household_amount = counter_1*20 + counter_2*30 + counter_3*25;
                onAmountChanged_household.amountChanged_household(household_amount,household_quntity);
                break;

            case 2:
                counter_2++;
                number_2.setText(counter_2+"");
                household_quntity = counter_1+counter_2+counter_3;
                household_amount = counter_1*20 + counter_2*30 + counter_3*25;
                onAmountChanged_household.amountChanged_household(household_amount,household_quntity);
                break;

            case 3:
                counter_3++;
                number_3.setText(counter_3+"");
                household_quntity = counter_1+counter_2+counter_3;
                household_amount = counter_1*20 + counter_2*30 + counter_3*25;
                onAmountChanged_household.amountChanged_household(household_amount,household_quntity);
                break;

        }

    }
    private void minusCounter(int button_number){
        switch(button_number){

            case 1:
                counter_1--;
                number_1.setText(counter_1+"");
                household_quntity = counter_1+counter_2+counter_3;
                household_amount = counter_1*20 + counter_2*30 + counter_3*25;
                onAmountChanged_household.amountChanged_household(household_amount,household_quntity);
                break;

            case 2:
                counter_2--;
                number_2.setText(counter_2+"");
                household_quntity = counter_1+counter_2+counter_3;
                household_amount = counter_1*20 + counter_2*30 + counter_3*25;
                onAmountChanged_household.amountChanged_household(household_amount,household_quntity);
                break;

            case 3:
                counter_3--;
                number_3.setText(counter_3+"");
                household_quntity = counter_1+counter_2+counter_3;
                household_amount = counter_1*20 + counter_2*30 + counter_3*25;
                onAmountChanged_household.amountChanged_household(household_amount,household_quntity);
                break;

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity)context;

        try{
            onAmountChanged_household = (OnAmountChanged_household) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()+"must implements that interface method");
        }
    }

}
