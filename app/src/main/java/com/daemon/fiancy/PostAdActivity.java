package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PostAdActivity extends AppCompatActivity {

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);

        // back button in toolbar
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostAdActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void adConfirmation(View view) {
        Intent intent = new Intent(this, AdConfirmActivity.class);
        startActivity(intent);
    }
}