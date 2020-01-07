package com.example.dhobiwala.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.dhobiwala.Helper.ValidatePhoneNumber;
import com.example.dhobiwala.DatabaseModels.UsersModel;
import com.example.dhobiwala.LoginActivities.GoogFaceActivity;
import com.example.dhobiwala.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private CountryCodePicker mCountryCodePicker;
    private EditText mFullName ;
    private EditText mEmail ;
    private EditText mPhoneNumber ;
    private ProgressBar mProgressBar;
    private FirebaseAuth mFirebaseAuth ;

    private Button mRegister ;
    private Button mGoogleAcoount  ;
    private String fullNumber1 = "";
    private DatabaseReference mDatabaseReference;

    SharedPreferences mSharedPreferenceManager ;

    public static String NAME_OF_USER ="";
    public static String EMAIL_OF_USER="" ;
    public static String ID_TOKEN_OF_USER="";
    public static Uri PHOTO_URI ;
    public static String PHOTO_OF_USER;

    private Context mContext ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: Successful");
//        googleSignInit();
        init();
    }



    private void init()
    {
        mCountryCodePicker =findViewById(R.id.register_countryCodeId);
        mFullName=findViewById(R.id.register_fullNameId);
        mEmail=findViewById(R.id.register_emailId);
        mPhoneNumber=findViewById(R.id.register_phoneNumberId);
        mRegister=findViewById(R.id.register_registerId);
        mProgressBar=findViewById(R.id.register_progressBarId);
        mContext=getBaseContext();
        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("UsersModel");
        mGoogleAcoount=findViewById(R.id.register_googleAccountId) ;

//___________________GOOGLE SIGN____________________________________________________________________

//GOOGLE SIGN IN ENDS>>>>>>>>>>>>>>>>>_________________________________________________

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Register Button Clicked.");
//-----------------------------Storing values for non-google register. ----------------------------------

                if(mFullName.getText().toString().isEmpty() || mFullName.getText().toString().length()==0 ){
                    mFullName.setError("Valid UserName is required");
                    mFullName.requestFocus();
                    return;
                }else{
                    NAME_OF_USER=mFullName.getText().toString();
                }

                if(mEmail.getText().toString().isEmpty() || mEmail.getText().toString().length()==0){
                    mEmail.setError("Valid email is required");
                    mEmail.requestFocus();
                    return;
                }else {
                    EMAIL_OF_USER = mEmail.getText().toString().trim();
                }
   //___________________________________________________________________________________________________________________


               //jaise h mene register button click kia, set progress bar visibility =true ;
                mProgressBar.setVisibility(View.VISIBLE);

                ValidatePhoneNumber validatePhoneNumber=new ValidatePhoneNumber();
                validatePhoneNumber.sPhoneNumber=mPhoneNumber.getText().toString();

                if(mPhoneNumber.getText().toString().isEmpty() || mPhoneNumber.getText().toString().length()<10){
                        mPhoneNumber.setError("Valid number is required");
                        mPhoneNumber.requestFocus();
                        mProgressBar.setVisibility(View.INVISIBLE);
                        return ;

                }
                else{

                    //#Phone_valid_regex not working .
//                    if(!validatePhoneNumber.getPhoneNumberValid()){
//                        mPhoneNumber.setError("Valid number is required");
//                        mPhoneNumber.requestFocus();
//                        mProgressBar.setVisibility(View.INVISIBLE);
//                        return ;
//                    }


                    fullNumber1="+" + getNumber();
                    Intent intent=new Intent(RegisterActivity.this,VerifyPhoneActivity.class);
                    intent.putExtra("phone_number",fullNumber1);
                    intent.putExtra("user_name",NAME_OF_USER);
                    intent.putExtra("user_email",EMAIL_OF_USER);
                    //dusre activity pe jane se pehle set visibiility of progress bar =invisible.
                    mProgressBar.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                }
            }
        });


        mGoogleAcoount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, GoogFaceActivity.class));
            }
        });
    }


    private String getNumber() {
        return (mCountryCodePicker.getFullNumber()+mPhoneNumber.getText().toString());
    }


//    private void sendDataToFireBase() {
//        UsersModel user =new UsersModel();
//
//        DatabaseReference push = mDatabaseReference.push();
//
//        String myKey =push.getKey();
//
//        user.setUserName(NAME_OF_USER);
//        user.setUserEmail(EMAIL_OF_USER);
//        user.setUserKey(myKey);
//
//        mDatabaseReference.push().setValue(user);
//
//        Log.d(TAG, "sendDataToFireBase: DATA sent to server successfully.");
//
//    }

    //______________________________________________________
    //For saving the user login .
    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
            Log.d(TAG, "onStart: In if");
            Intent intent=new Intent(RegisterActivity.this,MapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
            startActivity(intent);
        }
    }
}