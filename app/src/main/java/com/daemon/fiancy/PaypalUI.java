package com.daemon.fiancy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class PaypalUI extends AppCompatActivity {
    DatabaseReference adDb;

    private  int PAYPAL_REQ_CODE = 12;

    private  static PayPalConfiguration paypalconfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PaypalConfigID.CONFIG_CLIENT_ID);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_ui);

        Button paybutton = findViewById(R.id.CSbutton7);

        Intent intent = new Intent(PaypalUI.this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalconfig);
        startService(intent);

        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paypalpaymentmethod();
            }

        });
    }
    private void paypalpaymentmethod() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(100),"USD","Test_Payment",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PaypalUI.this,PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalconfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);

        startActivityForResult(intent,PAYPAL_REQ_CODE);
    }

    protected void onActivityResult(int requestcode, int resultcode, @Nullable Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        if(requestcode == PAYPAL_REQ_CODE){
            if(resultcode == Activity.RESULT_OK){
                Toast.makeText(getApplicationContext(),"Payment made successfully",Toast.LENGTH_LONG).show();
//                adDb = FirebaseDatabase.getInstance().getReference().child("Advertisements").child();
//                adDb.child("paymentNeeded").setValue(true);

            }else{
                Toast.makeText(getApplicationContext(),"Payment is unsuccessful",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void onDestroy(){
        stopService(new Intent(PaypalUI.this,PayPalService.class));
        super.onDestroy();
    }
}