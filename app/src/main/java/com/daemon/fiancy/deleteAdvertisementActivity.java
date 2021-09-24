package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class deleteAdvertisementActivity extends AppCompatActivity {
    // initialization
    ImageView back;

    CheckBox res1, res2, res3;
    EditText otherRes, resConf;

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

    @Override
    protected void onResume() {
        super.onResume();
        
        //get instances
        getInstances();
    }

    private void getInstances() {
    }


    // buttons
    // permanatly delete
    public void permanantDelete(View view) {
        
    }
    
    public void cancelDelete(View view) {
        Intent intent = new Intent(deleteAdvertisementActivity.this, ManageAdvertisementActiviy.class);
        intent.putExtra("DocumentKey", documentKey);
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