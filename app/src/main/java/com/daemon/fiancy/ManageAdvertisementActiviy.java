package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManageAdvertisementActiviy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_advertisement);
    }

    public void deleteAd(View view) {
        Intent intent = new Intent(ManageAdvertisementActiviy.this, deleteAdvertisementActivity.class);
        startActivity(intent);
    }
}