package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

public class AdminPanel extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

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

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main1);
        NavController navController1 = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);

    }
    public void Checkpendingadd(View view){
        Intent intent = new Intent(AdminPanel.this,ProfileReview.class);
        startActivity(intent);
    }

    public void Checkreportedadd(View view){
        Intent intent = new Intent(AdminPanel.this,Report_Ad_Check.class);
        startActivity(intent);
    }
}