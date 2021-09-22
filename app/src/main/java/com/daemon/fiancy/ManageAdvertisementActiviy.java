package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ManageAdvertisementActiviy extends AppCompatActivity {
    String TAG = "Fiancy manage advertisement    :   ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_advertisement);

        Log.d("lifecycle", "Application is created");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on resume");



    }

    // delete button
    public void deleteAd(View view) {
        Intent intent = new Intent(getApplicationContext(), deleteAdvertisementActivity.class);
        startActivity(intent);
    }
}