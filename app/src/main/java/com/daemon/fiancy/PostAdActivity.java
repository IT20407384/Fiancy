package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PostAdActivity extends AppCompatActivity {

    ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);

        back_img = findViewById(R.id.back);
    }

    public void adConfirmation(View view) {
        Intent intent = new Intent(this, AdConfirmActivity.class);
        startActivity(intent);
    }
}