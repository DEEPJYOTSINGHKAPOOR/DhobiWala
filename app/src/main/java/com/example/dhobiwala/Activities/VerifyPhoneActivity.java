package com.example.dhobiwala.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dhobiwala.DatabaseModels.UsersModel;
import com.example.dhobiwala.Helper.UserDetailsSharedPrefernces;
import com.example.dhobiwala.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {
    private static final String TAG = "VerifyPhoneActivity";


    private EditText mOtp ;
    private Button mVerify ;
    private FirebaseAuth mFirebaseAuth ;
    private ProgressBar mProgressBar ;

    private String phoneNumber1;
    private String userName1 ;
    public String userEmail1 ;
    private String userKey1 ;
    private String verificationId1;
    private String userPhoto1;
    private String userAuthId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        Log.d(TAG, "onCreate: Successful");
        init();
    }

    private void init() {
        mOtp=findViewById(R.id.verify_otpId);
        mVerify=findViewById(R.id.verify_verifyId);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mProgressBar=findViewById(R.id.verify_progressBarId);

        phoneNumber1=getIntent().getStringExtra("phone_number");

        userName1=getIntent().getStringExtra("user_name");

        userEmail1=getIntent().getStringExtra("user_email");

        userPhoto1 = getIntent().getStringExtra("user_photo");

        userAuthId = getIntent().getStringExtra("user_google_authentication_id");

        //to send verification code on to the users phone.
        sendVerificationCode(phoneNumber1);
//        detectedCodeOrEnteredVerify();

        // idhar tabhi click karna padega jab auto-detect of OTP  nahi horra hai .
        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code=mOtp.getText().toString().trim();

                if(code.isEmpty() || code.length()<6){
                    mOtp.setError("Enter code ...");
                    mOtp.requestFocus();
                    return;
                }
                verifyCode(code);

            }
        });

    }

    private void verifyCode(String code){

        try {
            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId1,code);
            signInWithCredential(credential);
        }catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(), "Verification Code is wrong, try again", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(UserDetailsSharedPrefernces.sharedPreferences,MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit() ;
                            editor.putString("phone_no",phoneNumber1);

                            sendToDatabase();


                            Intent intent =new Intent(VerifyPhoneActivity.this,HomeActivity.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }else{
                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendToDatabase()
    {

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("USERS") ;
        DatabaseReference newRefernce = mDatabaseReference.push();
         userKey1=mDatabaseReference.push().getKey();

        UsersModel user =new UsersModel() ;

        user.setUserName(userName1);
        user.setUserEmail(userEmail1);
        user.setUserPhoneNumber(phoneNumber1);
        user.setUserKey(userKey1);
//        user.setUserPhoneNumber(phoneNumber1);

        newRefernce.setValue(user);

    }

    private void sendVerificationCode(String phoneNumber1){
        Log.d(TAG, "sendVerificationCode: ");

        mProgressBar.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber1,
                120,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallBack= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        //this method is called when code is sent .s contains verification ID sent to user.
        @Override
        public void onCodeSent(@NotNull String s, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d(TAG, "onCodeSent: ");
            super.onCodeSent(s, forceResendingToken);
            verificationId1=s;
        }

        //if this succedds ,user do not have to send code manually .
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted: ");
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null){
                mOtp.setText(code);
                verifyCode(code);
            }
        }
        //if verification fails this method is called.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e(TAG, "onVerificationFailed: "+e.getLocalizedMessage());
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}
