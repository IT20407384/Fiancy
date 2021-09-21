package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    }
}