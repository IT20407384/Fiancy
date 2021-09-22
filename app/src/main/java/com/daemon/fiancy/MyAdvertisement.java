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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    SharedPreferences sharedpreferences;
    String emailShared;

    String documentKey;

    public MyAdvertisement() {}

    public static MyAdvertisement newInstance(String param1, String param2) {
        MyAdvertisement fragment = new MyAdvertisement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE); }
        catch (NullPointerException e) { Log.d("Share Error", e.toString()); }

        emailShared = sharedpreferences.getString(EMAIL_KEY, null);

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_advertisement, container, false);

        Button matchfinder = view.findViewById(R.id.SMmatchfinderbtn);
        matchfinder.setOnClickListener(this);

        Button editAdvertise = view.findViewById(R.id.SMeditadbtn);
        editAdvertise.setOnClickListener(this);

        Button payForAdvertise = view.findViewById(R.id.SMpayforad);
        payForAdvertise.setOnClickListener(this);

        TextView nameHead = view.findViewById(R.id.SMnameinaccount);
        TextView adState = view.findViewById(R.id.SMadstatus);
        TextView accState = view.findViewById(R.id.SMaccountstatus);
        TextView payState = view.findViewById(R.id.SMpaymentstatus);

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

                Glide.with(getContext()).load("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Demodara-Nine-Arch-Bridge.jpg").into((ImageView) view.findViewById(R.id.SMaccimage));
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
                    if(advertisements.getOwner().equalsIgnoreCase(emailShared)) {
                        documentKey = advertisements.getDocumentKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

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
                Toast.makeText(getContext(), "pay for ad", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}