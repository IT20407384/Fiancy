package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

public class MyAdCollection extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    SharedPreferences sharedpreferences;
    String emailShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ad_collection);

        // initiating the tabhost
        TabHost tabhost = (TabHost) findViewById(R.id.SMtabhost2);

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

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        emailShared = sharedpreferences.getString(EMAIL_KEY, null);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        ImageView backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAdCollection.this, Home.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MyAdCollection.this, Home.class);
        startActivity(intent);
        finish();
    }
}