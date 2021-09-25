package com.daemon.fiancy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAdvertisement extends Fragment implements View.OnClickListener {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    SharedPreferences sharedpreferences;
    String emailShared;
    String documentKey;
    String paymentForAd;

    boolean paymentNeed = false;
    boolean liveAdvertise = false;
    boolean adHasPosted = false;
    boolean adHasRejected = false;

    TextView nameHead ;
    TextView adState ;
    TextView accState ;
    TextView payState ;

    Button matchfinder;
    Button editAdvertise;
    Button payForAdvertise;

    public MyAdvertisement() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE); }
        catch (NullPointerException e) { Log.d("Share Error", e.toString()); }

        emailShared = sharedpreferences.getString(EMAIL_KEY, null);

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_advertisement, container, false);

        matchfinder = view.findViewById(R.id.SMmatchfinderbtn);
        matchfinder.setOnClickListener(this);

        editAdvertise = view.findViewById(R.id.SMeditadbtn);
        editAdvertise.setOnClickListener(this);

        payForAdvertise = view.findViewById(R.id.SMpayforad);
        payForAdvertise.setOnClickListener(this);

        nameHead = view.findViewById(R.id.SMnameinaccount);
        adState = view.findViewById(R.id.SMadstatus);
        accState = view.findViewById(R.id.SMaccountstatus);
        payState = view.findViewById(R.id.SMpaymentstatus);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("AppUser");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserModel userPrime = new UserModel();
                boolean prime = false;

                for(DataSnapshot mySnap : snapshot.getChildren()) {
                    userPrime = mySnap.getValue(UserModel.class);

                    if(userPrime.getEmail().equalsIgnoreCase(emailShared)) {
                        prime = userPrime.isuPremium();
                        break;
                    }
                }
                matchfinder.setEnabled(prime);
                if(!prime) matchfinder.setTextColor(Color.parseColor("#B8B8B8"));

                nameHead.setText(userPrime.getFullName());
                if(prime) accState.setText("Premium Account");

                Glide.with(getContext()).load("https://st2.depositphotos.com/5142301/8014/v/950/depositphotos_80144862-stock-illustration-d-letter-logo-with-blue.jpg").into((ImageView) view.findViewById(R.id.SMaccimage));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        DatabaseReference advertiseDB = FirebaseDatabase.getInstance().getReference().child("Advertisements");
        advertiseDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Advertisements advertisements = new Advertisements();

                for(DataSnapshot mySnap : snapshot.getChildren()) {
                    advertisements = mySnap.getValue(Advertisements.class);
                    assert advertisements != null;
                    if(advertisements.getOwner().equalsIgnoreCase(emailShared)) {
                        documentKey = mySnap.getKey();
                        paymentForAd = advertisements.getTotatlFee();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        setPaymentTextAndButton();
        setAdStateTextAndEditBtn();

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.SMmatchfinderbtn:
                Intent intent = new Intent(getActivity(), MatchFinder.class);
                startActivity(intent);
                break;
            case R.id.SMeditadbtn:
                Intent intent1 = new Intent(getActivity(), ManageAdvertisementActiviy.class);
                intent1.putExtra("DocumentKey", documentKey);
                startActivity(intent1);
                break;
            case R.id.SMpayforad:
                Intent intent2 = new Intent(getActivity(), PaypalUI.class);
                intent2.putExtra("PaymentGateway", paymentForAd);
                intent2.putExtra("AdvertisementId",documentKey );
                startActivity(intent2);
                break;
        }
    }

    public void setPaymentTextAndButton() {

        try{
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Advertisements");
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Advertisements advertisements = new Advertisements();

                    boolean paymentNeeded = false;
                    boolean liveAdvertisement = false;

                    for(DataSnapshot mySnap : snapshot.getChildren()) {
                        advertisements = mySnap.getValue(Advertisements.class);
                        assert advertisements != null;
                        if(advertisements.getOwner().equalsIgnoreCase(emailShared)) {
                            paymentNeeded = advertisements.getPaymentNeeded();
                            liveAdvertisement = advertisements.getLiveAdvertisement();
                        }
                    }

                    if(paymentNeeded) {
                        payForAdvertise.setEnabled(true);
                        payForAdvertise.setTextColor(Color.BLACK);
                        payState.setText("Payment Needed");
                    }else {
                        payForAdvertise.setEnabled(false);
                    }

                    if(liveAdvertisement) {
                        payState.setText("Payment Successfull");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }catch(Exception e){
            Log.d("Error with DB : ", e.toString());
        }
    }

    public void setAdStateTextAndEditBtn() {
        try{
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Advertisements");
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Advertisements advertisements = new Advertisements();

                    for(DataSnapshot mySnap : snapshot.getChildren()) {
                        advertisements = mySnap.getValue(Advertisements.class);
                        assert advertisements != null;
                        if(advertisements.getOwner().equalsIgnoreCase(emailShared)) {
                            paymentNeed = advertisements.getPaymentNeeded();
                            liveAdvertise = advertisements.getLiveAdvertisement();
                            adHasPosted = true;
                        }
                    }
                    DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference().child("RejectAds");
                    dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(emailShared))
                                adHasRejected = true;

                            if(!adHasPosted) {
                                adState.setText("Status : Haven't posted any Ads");
                                matchfinder.setEnabled(false);
                                matchfinder.setTextColor(Color.parseColor("#B8B8B8"));
                            }
                            else if(!paymentNeed && !liveAdvertise)
                                adState.setText("Status : Ad is in pending for approval");
                            else if(paymentNeed && !liveAdvertise)
                                adState.setText("Status : Advertisement approved");
                            else {
                                adState.setText("Status : Advertise is now live.");
                                editAdvertise.setEnabled(true);
                                editAdvertise.setTextColor(Color.BLACK);
                            }
                            if(adHasRejected && !adHasPosted) {
                                adState.setText("Status : Ad has been rejected ! \nReason : " + snapshot.child(emailShared).child("reason").getValue().toString());
                                adState.setTextColor(Color.RED);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }catch(Exception e){
            Log.d("Error with DB : ", e.toString());
        }

    }
}