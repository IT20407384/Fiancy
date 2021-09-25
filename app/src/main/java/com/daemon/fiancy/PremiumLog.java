package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

public class PremiumLog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premiumlog);

        // initiating the tabhost
        TabHost tabhost = (TabHost) findViewById(R.id.SMtabhost1);

        // setting up the tab host
        tabhost.setup();

        // Code for adding Tab 1 to the tabhost
        TabHost.TabSpec spec = tabhost.newTabSpec("Fiancy Premium");
        spec.setContent(R.id.premium);

        // setting the name of the tab 1 as "Tab One"
        spec.setIndicator("Fiancy Premium");

        // adding the tab to tabhost
        tabhost.addTab(spec);


        ImageView backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PremiumLog.this, Home.class);
                startActivity(intent);
            }
        });
    }
}