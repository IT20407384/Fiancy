package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.TabHost;

public class MyAdCollection extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ad_collection);

        // initiating the tabhost
        TabHost tabhost = (TabHost) findViewById(R.id.tabhost);

        // setting up the tab host
        tabhost.setup();

        // Code for adding Tab 1 to the tabhost
        TabHost.TabSpec spec = tabhost.newTabSpec("My Advertisement");
        spec.setContent(R.id.myadvertisement);

        // setting the name of the tab 1 as "Tab One"
        spec.setIndicator("My Advertisement");

        // adding the tab to tabhost
        tabhost.addTab(spec);

        // Code for adding Tab 2 to the tabhost
        spec = tabhost.newTabSpec("My Favorites");
        spec.setContent(R.id.myfavorites);

        // setting the name of the tab 1 as "Tab Two"
        spec.setIndicator("My Favorites");
        tabhost.addTab(spec);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    }
}