package com.example.dhobiwala.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dhobiwala.R;
import com.hbb20.CountryCodePicker;

public class EnterMobileNumberActivity extends AppCompatActivity {
    private static final String TAG = "EnterMobileNumber";

    private EditText mPhoneNumber ;
    private Button mContinue ;

    private static String ID_TOKEN_OF_USER ;
    private static  String PHOTO_OF_USER  ;
    private static String EMAIL_OF_USER ;
    private static String NAME_OF_USER ;



    private CountryCodePicker mCountryCodePicker;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile_number);

        Log.d(TAG, "onCreate: Successful. ");
        init();
    }

    private void init() {
        mPhoneNumber=findViewById(R.id.enterMoileNumber_phoneNumberId);
        mContinue=findViewById(R.id.enterMobile_continueButtonId);
        mCountryCodePicker=findViewById(R.id.countryCodeId);


        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //taking input of the phone number .
                String phoneNumber1 =mPhoneNumber.getText().toString();


                if(phoneNumber1.length() < 10){
                    mPhoneNumber.setError("Enter valid phone number.");
                    mPhoneNumber.requestFocus();
                    return;
                }else{
                    String fullPhoneNumber1="+"+mCountryCodePicker.getFullNumber()+ phoneNumber1 ;
                    String userName1=getIntent().getStringExtra("user_name");
                    String userEmail1=getIntent().getStringExtra("user_email");
                    String userAuthId1=getIntent().getStringExtra("user_google_authentication_id");
                    String userPhoto1=getIntent().getStringExtra("user_photo");

                    Intent intent =new Intent(EnterMobileNumberActivity.this, VerifyPhoneActivity.class);

                    Log.d(TAG, "onClick: "+"Continue button Clicked" );
                    intent.putExtra("phone_number",fullPhoneNumber1);
                    intent.putExtra("user_name",userName1);
                    intent.putExtra("user_email",userEmail1);
                    intent.putExtra("user_photo",userPhoto1);
                    intent.putExtra("user_google_authentication_id",userAuthId1);
                    startActivity(intent);
                }
            }
        });

    }
}
