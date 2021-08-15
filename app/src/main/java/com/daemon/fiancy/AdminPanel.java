package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TabHost;

public class AdminPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        TabHost tabHost =(TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("Pending");
        spec.setContent(R.id.pending);
        spec.setIndicator("Pending");
        tabHost.addTab(spec);

         spec = tabHost.newTabSpec("Reported");
        spec.setContent(R.id.reported);
        spec.setIndicator("Reported");
        tabHost.addTab(spec);

    }
}