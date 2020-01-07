package com.example.dhobiwala.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhobiwala.Activities.ProfileActivity;
import com.example.dhobiwala.Activities.RegisterActivity;
import com.example.dhobiwala.Helper.SignInThrough;
import com.example.dhobiwala.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private ImageView mUserImage ;
    private TextView mUserName ;
    private TextView mUserEmail ;
    private TextView mPhoneNumber ;
    private TextView mUserAddress ;
    private TextView mRefer ;
    private TextView mHelp ;
    private TextView mAboutUs ;
    private TextView mTerms ;

    private Button mLogout ;


    private String userName1 ;
    private String userImageString1 ;
    private URL userImageUrl;
    private String userEmail1 ;
//    private SharedPreferences mSharedPreferences ;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_account,container,false);

        final Context thisContext = container.getContext();

        mUserImage = view.findViewById(R.id.accountF_userImageId);
        mUserName = view.findViewById(R.id.accountF_userNameId);
        mPhoneNumber=view.findViewById(R.id.accountF_userPhoneNumberId);
        mUserAddress=view.findViewById(R.id.accountF_userAddressId);
        mRefer=view.findViewById(R.id.accountF_referId);
        mHelp=view.findViewById(R.id.accountF_helpId);
        mAboutUs=view.findViewById(R.id.accountF_aboutId);
        mTerms=view.findViewById(R.id.accountF_termsId);
        mUserEmail=view.findViewById(R.id.accountF_userEmailId);
        mLogout=view.findViewById(R.id.accountF_logoutId);

        SharedPreferences mSharedPreferences =thisContext.getSharedPreferences("USER_DETAILS_SHARED_PREFERENCE",MODE_PRIVATE);

        userName1=mSharedPreferences.getString("NAME_OF_USER","");

//        if(SignInThrough.GOOGLE_SIGN_IN=true){
//            if(userImageString1 != null || userImageString1!="") {
//                Picasso.get().load(userImageString1).into(mUserImage);
//            }
//
//        }
        userImageString1=mSharedPreferences.getString("PHOTO_OF_USER","");


        userEmail1=mSharedPreferences.getString("EMAIL_OF_USER","") ;
        
        
        String[] fullName=userName1.split(" ");

        String nameToPrint = "";

        for(String i:fullName){
           nameToPrint+=" "+i;
        }

        if( userImageString1 != null && userImageString1.length()!=0 && (SignInThrough.GOOGLE_SIGN_IN)) {
            Picasso.get().load(userImageString1).into(mUserImage);
        }

        mUserEmail.setText(userEmail1);
        mUserName.setText(nameToPrint);

        //logout
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences mSharedPreferences = thisContext.getSharedPreferences("USER_DETAILS_SHARED_PREFERENCE",MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.clear().apply();
                    
                sendToLogin(thisContext);
                


                Intent intent =new Intent(thisContext, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }

        );

        return view;
    }
    private void sendToLogin(final Context thisContext) { //funtion
        GoogleSignInClient mGoogleSignInClient ;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(thisContext, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener((Activity) thisContext,
                new OnCompleteListener<Void>()  {  //signout Google
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseAuth.getInstance().signOut(); //signout firebase
                        Intent setupIntent = new Intent(thisContext, /*To ur activity calss*/RegisterActivity.class);
                        Toast.makeText(thisContext, "Logged Out", Toast.LENGTH_LONG).show(); //if u want to show some text
                        setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(setupIntent);
                    }
                });
    }
}