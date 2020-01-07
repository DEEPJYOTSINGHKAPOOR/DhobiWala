package com.example.dhobiwala.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dhobiwala.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
        private Button mLogout ;
        private ImageView mUserImage ;
        private EditText mUserName ;
        private EditText mEmail;


        private String userName1 ;
        private String userImageString1 ;
        private URL userImageUrl;
        private String userEmail1 ;

        private Bitmap bitmap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: ");
        try {
            init();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(ProfileActivity.this,RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


    private void init() throws MalformedURLException {
        mLogout=findViewById(R.id.profile_logoutId);
        mUserImage=findViewById(R.id.profile_userImageId);
        mEmail=findViewById(R.id.profile_userEmailId);
        mUserName=findViewById(R.id.profile_userNameId);

        SharedPreferences mSharedPreferences =getSharedPreferences("USER_DETAILS_SHARED_PREFERENCE",MODE_PRIVATE);
        userName1=mSharedPreferences.getString("NAME_OF_USER","");
        userImageString1=mSharedPreferences.getString("PHOTO_OF_USER","");
//        userImage1=Integer.parseInt(userImageString1);
        userEmail1=mSharedPreferences.getString("EMAIL_OF_USER","") ;

//        userImageUrl= new Uri(userImageString1);

        Picasso.get().load(userImageString1).into(mUserImage);
        mEmail.setText(userEmail1);
        mUserName.setText(userName1);
    }

}
