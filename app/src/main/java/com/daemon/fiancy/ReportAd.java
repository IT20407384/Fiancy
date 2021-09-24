package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.daemon.fiancy.models.ReportedADModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ReportAd extends AppCompatActivity {

    EditText NPEmail, NPMessage, etReason;
    Button NPReportAD;

    DatabaseReference ReportReason;
    String reportedAdKey;

    String email, message, reason;
    ReportedADModel reportedADModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_ad);

        reportedAdKey = getIntent().getExtras().getString("ReportedAdKey");


        NPEmail = findViewById(R.id.NPEmail);
        NPMessage = findViewById(R.id.NPMessage);
        etReason = findViewById(R.id.etReason);
        NPReportAD = findViewById(R.id.NPReportAD);

        ReportReason = FirebaseDatabase.getInstance().getReference().child("ReportedAdvertisements");

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportAd.this, profile.class);
                intent.putExtra("documetKey", reportedAdKey);
                startActivity(intent);
            }
        });
    }

    //Sent reportAd details to database
    private void InsertReportAd() {

        ReportReason.push().setValue(reportedADModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ReportAd.this, "Report Sent", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReportAd.this, Home.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(ReportAd.this, "Report Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //ReportedAd button click event
    public void ReportAdvertisements(View view) {
        email = NPEmail.getText().toString();
        message = NPMessage.getText().toString();
        reason = etReason.getText().toString();

        reportedADModel = new ReportedADModel(reportedAdKey, email, message, reason);

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(message) &&!TextUtils.isEmpty(reason)) {
            InsertReportAd();
        } else {
            Toast.makeText(ReportAd.this, "Please provide a reason for report this advertisement!", Toast.LENGTH_SHORT).show();
        }

    }
}