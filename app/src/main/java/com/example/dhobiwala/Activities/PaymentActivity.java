package com.example.dhobiwala.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dhobiwala.Helper.DatabaseHelper;
import com.example.dhobiwala.Helper.PutExtraHelper;
import com.example.dhobiwala.Helper.UserDetailsSharedPrefernces;
import com.example.dhobiwala.R;
import com.example.dhobiwala.Services.WashAndFoldModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentActivity extends AppCompatActivity {

    private String service_id;
    private int bill =0;

    private TextView mShowBill ;
    private Button mCalculateBill ;
    private int shirtCount=2;
    private int pantCount=2 ;
    private int towelCount=0 ;
    private int suitCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        service_id=getIntent().getStringExtra(PutExtraHelper.service_type);

        //calculating bill for this service .
        init();
        calculateBill(service_id);
        printBill();

        mCalculateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDoneButtonClicked();
            }
        });

    }

    private void init() {
        mShowBill=findViewById(R.id.payment_billTextId);
        mCalculateBill=findViewById(R.id.payment_calculateButtonId);
    }

    private void printBill() {

    }

    private void calculateBill(String service_id) {
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("SERVICE_TYPE").child(service_id);


        //final int shirtCount=2;
        mDatabaseReference.child("shirt_id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value =dataSnapshot.getValue(String.class);
                assert value != null;
                int itemPrice= Integer.parseInt(value);
//                Toast.makeText(PaymentActivity.this, "item price is  : "+itemPrice, Toast.LENGTH_SHORT).show();
                bill+=(itemPrice*shirtCount) ;
            }
//
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // final int pantCount=2;
        mDatabaseReference.child("pant_id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value =dataSnapshot.getValue(String.class);
                assert value != null;
                int itemPrice= Integer.parseInt(value);
                bill+=(itemPrice*pantCount) ;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void paymentDoneButtonClicked() {
        DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference(DatabaseHelper.transactionTable);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(UserDetailsSharedPrefernces.sharedPreferences,MODE_PRIVATE);
        String userId=sharedPreferences.getString(UserDetailsSharedPrefernces.userKeyOfDatabase,"Default USER_ID");

        DatabaseReference newDatabaseRefernce= databaseReference.child(userId).push();
        String trans_id =newDatabaseRefernce.getKey() ;

        if(service_id.equals(DatabaseHelper.washAndFoldId)){
            WashAndFoldModel washAndFoldModel = new WashAndFoldModel();

            washAndFoldModel.setPant_count(String.valueOf(pantCount));
            washAndFoldModel.setShirt_count(String.valueOf(shirtCount));
            washAndFoldModel.setTowel_count(String.valueOf(towelCount));
            washAndFoldModel.setSuit_count(String.valueOf(suitCount));
            washAndFoldModel.setTransactionId(trans_id);

            newDatabaseRefernce.child("service_id").child(service_id).setValue(washAndFoldModel);
            mShowBill.setText("Your Bill is : "+bill);
        }
    }
    // take the service type :

    // For now, assume : 1.wash and fold ->2shirts and 2pants(40rs)
    //                   2.wash and iron-> 2pants(20rs)
    //-------------------------------------------------------
    // Your bill is ----------------------> 60rs .
 //   Steps:
    //take prices from database and calculate prices accordingly .
    // make a payment done button and display bill in the log when button is clicked.
    // will update the bill in the transaction table and also in the cost table.
}
