package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.models.ReportedADModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Report_Ad_Check extends AppCompatActivity {

    String reportadid;
    String image1,image2,image3;
    DatabaseReference reportadreferrence;
    ReportedADModel reportedAdvertisements;
    String reportedAdKey;

    DatabaseReference adref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_ad_check);

        TextView rejecttext = findViewById(R.id.CStextView26);
        TextView description = findViewById(R.id.CStextView28);

        Intent intent = getIntent();
        reportadid = intent.getStringExtra("SelectedRAD");

        reportadreferrence = FirebaseDatabase.getInstance().getReference().child("ReportedAdvertisements").child(reportadid);

        reportadreferrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    rejecttext.setText(snapshot.child("reason").getValue().toString());
                    description.setText(snapshot.child("message").getValue().toString());
                    reportedAdKey = snapshot.child("reportedAdKey").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public  void buttondacceptrejection(View view){

        reportadreferrence = FirebaseDatabase.getInstance().getReference().child("ReportedAdvertisements").child(reportadid);
        adref = FirebaseDatabase.getInstance().getReference().child("Advertisements").child(reportedAdKey);


        adref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                reportadreferrence.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Advertisement get reject permanently", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Report_Ad_Check.this,AdminPanel.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
    public void buttondiscardrejection(View view){
        reportadreferrence = FirebaseDatabase.getInstance().getReference().child("ReportedAdvertisements").child(reportadid);
        reportadreferrence.removeValue();

        Toast.makeText(getApplicationContext(),"No valid reason to reject this ad",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Report_Ad_Check.this,AdminPanel.class);
        startActivity(intent);
    }

}
