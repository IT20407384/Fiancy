package com.daemon.fiancy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

public class BuyPremium extends Fragment{

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    SharedPreferences sharedpreferences;
    String emailShared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE); }
        catch (NullPointerException e) { Log.d("Share Error", e.toString()); }

        emailShared = sharedpreferences.getString(EMAIL_KEY, null);

        View view = inflater.inflate(R.layout.fragment_buy_premium, container, false);
        Button button = (Button) view.findViewById(R.id.SMbuyprime);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), PaypalUI.class);
                intent.putExtra("PaymentGateway", "Premium");
                intent.putExtra("UserID",emailShared);
                startActivity(intent);
            }
        });

        return view;
    }
}