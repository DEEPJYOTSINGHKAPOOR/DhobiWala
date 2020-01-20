package com.example.dhobiwala.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dhobiwala.Helper.UserDetailsSharedPrefernces;
import com.example.dhobiwala.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    private EditText mPhoneNumber ;
    private Button mLogin;
    private CountryCodePicker mCountryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();

        loginInButtonClicked();

    }

    private void loginInButtonClicked() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber="+"+mCountryCodePicker.getFullNumber()+mPhoneNumber.getText().toString();
                final String[] userEmail = {null};
                final String[] userPhoneNumber = {null};
                final String[] userName={null};

                Query mDatabaseRefernce= FirebaseDatabase.getInstance().getReference().child("USERS").orderByChild("userPhoneNumber");

                final DatabaseReference firebaseDatabase=FirebaseDatabase.getInstance().getReference().child("USERS");

                mDatabaseRefernce.equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {

                    String key=null;
                    SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences(UserDetailsSharedPrefernces.sharedPreferences,MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            key=dataSnapshot1.getKey();
                            Toast.makeText(SignInActivity.this, "Key from DB is "+key, Toast.LENGTH_SHORT).show();
                        }
                        if(key!=null){
                            firebaseDatabase.child(key).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    userPhoneNumber[0]=dataSnapshot.child("userPhoneNumber").getValue(String.class);
                                    Toast.makeText(SignInActivity.this, "userPhoneNumber is : "+userPhoneNumber[0], Toast.LENGTH_SHORT).show();
                                    userEmail[0]=dataSnapshot.child("userEmail").getValue(String.class);
                                    userName[0]=dataSnapshot.child("userName").getValue(String.class);

                                    editor.putString(UserDetailsSharedPrefernces.userEmail,userEmail[0]);
                                    editor.putString(UserDetailsSharedPrefernces.userName,userName[0]);
                                    editor.putString(UserDetailsSharedPrefernces.userKeyOfDatabase,key);
                                    editor.putString(UserDetailsSharedPrefernces.userPhoneNumber,userPhoneNumber[0]);
                                    editor.apply();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(SignInActivity.this, "In database Error: ", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "onCancelled: ",databaseError.toException());
                                }
                            });



                            editor.apply();


                            Toast.makeText(SignInActivity.this, "Welcome to DhobiWala", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SignInActivity.this,HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SignInActivity.this, "phone number is not registered.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this,SignInActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    private void init() {
        mPhoneNumber=findViewById(R.id.signIn_phoneNumberId);
        mLogin=findViewById(R.id.signIn_loginButtonId);
        mCountryCodePicker=findViewById(R.id.signIn_countryCodeId);
    }
}