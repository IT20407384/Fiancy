package com.daemon.fiancy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class PaypalUI extends AppCompatActivity {
    DatabaseReference adDb,apuser;
    PayPalPayment payPalPayment;
    String payment;
    String userid;
    String adkey;

    private  int PAYPAL_REQ_CODE = 12;

    private  static PayPalConfiguration paypalconfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PaypalConfigID.CONFIG_CLIENT_ID);

    double paymentForAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_ui);

        Intent intent2 = getIntent();
        payment = intent2.getStringExtra("PaymentGateway");
        if(payment.equals("Premium")){
            paymentForAd = 5000.0;
           userid = intent2.getStringExtra("UserID");

        }else{
            paymentForAd = Double.parseDouble(payment);
            adkey = intent2.getStringExtra("AdvertisementId");
        }



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

        ImageView backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaypalUI.this, Home.class);
                startActivity(intent);
            }
        });
    }
    private void paypalpaymentmethod() {
        payPalPayment = new PayPalPayment(new BigDecimal(paymentForAd), "USD", "Test_Payment", PayPalPayment.PAYMENT_INTENT_SALE);
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
                if(paymentForAd ==5000.0){
                    apuser = FirebaseDatabase.getInstance().getReference().child("AppUser").child(userid);
                    apuser.child("uPremium").setValue(true);
                }else if(paymentForAd == Double.parseDouble(payment)){

                    adDb = FirebaseDatabase.getInstance().getReference().child("Advertisements").child(userid);
                    adDb.child("liveAdvertisement").setValue(true);
                }


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