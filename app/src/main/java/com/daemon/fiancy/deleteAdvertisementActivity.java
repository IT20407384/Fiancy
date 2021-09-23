package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class deleteAdvertisementActivity extends AppCompatActivity {
    ImageView back;

    String documentKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_advertisement);
        // document key
        documentKey = getIntent().getExtras().getString("DocumentKey");

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(deleteAdvertisementActivity.this, ManageAdvertisementActiviy.class);
                intent.putExtra("DocumentKey", documentKey);
                startActivity(intent);
            }
        });
    }

    public void cancelDelete(View view) {
        Intent intent = new Intent(deleteAdvertisementActivity.this, ManageAdvertisementActiviy.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(deleteAdvertisementActivity.this, ManageAdvertisementActiviy.class);
        intent.putExtra("DocumentKey", documentKey);
        startActivity(intent);
    }
}