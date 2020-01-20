package com.example.dhobiwala.Activities;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dhobiwala.Helper.UserDetailsSharedPrefernces;
import com.example.dhobiwala.R;



public class PastAddressActivity extends AppCompatActivity {
    private static final String TAG = "PastAddressActivity";
    private RadioButton mAddress1;
    private RadioButton mAddress2 ;
    private Button mAddAddress;
    private Button mPayment ;
    String addressCurrent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_address);
        Log.d(TAG, "onCreate: ");
        mAddress1=findViewById(R.id.past_address1Id);
        mAddress2=findViewById(R.id.past_address2Id);
        mAddAddress=findViewById(R.id.past_addAddressId);
        mPayment=findViewById(R.id.past_paymentId);


        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(UserDetailsSharedPrefernces.sharedPreferences,MODE_PRIVATE);
        mAddress1.setText(sharedPreferences.getString(UserDetailsSharedPrefernces.addressOfUser,"Default Address."));
//        mAddress2.setText();

        clickedAddAddressButton();
        clickedPaymentButton();
    }

    private void clickedAddAddressButton() {
        mAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(PastAddressActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clickedPaymentButton() {
        mPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAddress1.isChecked()){
                    addressCurrent=mAddress1.getText().toString() ;
                    Toast.makeText(PastAddressActivity.this, "current Address is "+addressCurrent, Toast.LENGTH_LONG).show();
                }
                else if(mAddress2.isChecked()){
                    addressCurrent=mAddress2.getText().toString();
                    Toast.makeText(PastAddressActivity.this, "current Address is  "+addressCurrent, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}