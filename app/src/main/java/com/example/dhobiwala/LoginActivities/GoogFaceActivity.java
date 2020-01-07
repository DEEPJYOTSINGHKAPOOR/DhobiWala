package com.example.dhobiwala.LoginActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dhobiwala.Activities.EnterMobileNumberActivity;
import com.example.dhobiwala.Activities.HomeActivity;
import com.example.dhobiwala.Helper.SignInThrough;
import com.example.dhobiwala.Helper.UserDetailsSharedPrefernces;
import com.example.dhobiwala.DatabaseModels.UsersModel;
import com.example.dhobiwala.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.dhobiwala.Activities.RegisterActivity.PHOTO_URI;

public class GoogFaceActivity extends AppCompatActivity {
    private static final String TAG = "GoogFaceActivity";
    private static final int RC_SIGN_IN = 2;
    private static String ID_TOKEN_OF_USER ;
    private static  String PHOTO_OF_USER  ;
    private static String EMAIL_OF_USER ;
    private static String NAME_OF_USER ;
    GoogleSignInClient mGoogleSignInClient ;
    SignInButton mSignInButton ;
    FirebaseAuth mFirebaseAuth ;

    DatabaseReference mDatabaseReference ;


    SharedPreferences mSharedPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goog_face);
        Log.d(TAG, "onCreate: Sucessful");
        init();
    }

    private void init() {
        mSignInButton=findViewById(R.id.googleSignInButtonId);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mFirebaseAuth= FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("Users");

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);// account will get all auth info.we can use it
                firebaseAuthWithGoogle(account);
                NAME_OF_USER=account.getDisplayName();
                EMAIL_OF_USER=account.getEmail();
                PHOTO_URI=account.getPhotoUrl();

                if(PHOTO_URI==null){
                    Log.e(TAG, "onActivityResult: Photo URL IS NULL" );
                    PHOTO_OF_USER=" ";
                }else{
                    SignInThrough.GOOGLE_SIGN_IN=true ;  //sign in by google.
                    Log.d(TAG, "onActivityResult: Photo url not null");
                    PHOTO_OF_USER=PHOTO_URI.toString();
                }
                ID_TOKEN_OF_USER=account.getIdToken() ;

                sendDataToFireBase();
                mSharedPreferences= getBaseContext().getSharedPreferences("USER_DETAILS_SHARED_PREFERENCE",MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(UserDetailsSharedPrefernces.userName,NAME_OF_USER);
                editor.putString(UserDetailsSharedPrefernces.userEmail,EMAIL_OF_USER);
                editor.putString(UserDetailsSharedPrefernces.userProfilePhoto,PHOTO_OF_USER);
                editor.putString(UserDetailsSharedPrefernces.userIdToken,ID_TOKEN_OF_USER);
                editor.apply();

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Log.e(TAG, "onActivityResult: "+e.getLocalizedMessage() );
                Toast.makeText(GoogFaceActivity.this, "Sign in failed "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(GoogFaceActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();

                            Intent intent=new Intent(GoogFaceActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
                            intent.putExtra("user_name",NAME_OF_USER);
                            intent.putExtra("user_email",EMAIL_OF_USER);
                            intent.putExtra("user_photo",PHOTO_OF_USER);
                            intent.putExtra("user_google_authentication_id",ID_TOKEN_OF_USER);
                            startActivity(intent);
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            View parentLayout = findViewById(android.R.id.content);
                            Snackbar.make(parentLayout/*findViewById(R.id.main_layout)*/, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    private void sendDataToFireBase() {
        UsersModel user =new UsersModel();

        user.setUserName(NAME_OF_USER);
        user.setUserEmail(EMAIL_OF_USER);

        mDatabaseReference.push().setValue(user);

        Log.d(TAG, "sendDataToFireBase: DATA sent to server successfully.");

    }


}
