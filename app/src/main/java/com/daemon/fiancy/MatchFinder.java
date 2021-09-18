package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TabHost;

public class MatchFinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_finder);

        // initiating the tabhost
        TabHost tabhost = (TabHost) findViewById(R.id.tabhost10);

        // setting up the tab host
        tabhost.setup();

        // Code for adding Tab 1 to the tabhost
        TabHost.TabSpec spec = tabhost.newTabSpec("Possible Matches");
        spec.setContent(R.id.match_fnd);

        // setting the name of the tab 1 as "Tab One"
        spec.setIndicator("Possible Matches");

        // adding the tab to tabhost
        tabhost.addTab(spec);
    }
}