package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.daemon.fiancy.models.ReportedADModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ReportAd extends AppCompatActivity {

    EditText NPEmail,NPMessage;
    Spinner SPReason;
    Button NPReportAD;

    DatabaseReference ReportReason;
    String reportedAdKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_ad);

        reportedAdKey = getIntent().getExtras().getString("ReportedAdKey");


        NPEmail = findViewById(R.id.NPEmail);
        NPMessage = findViewById(R.id.NPMessage);
        SPReason = findViewById(R.id.SPReason);
        NPReportAD = findViewById(R.id.NPReportAD);

        ReportReason = FirebaseDatabase.getInstance().getReference().child("ReportedAdvertisements");





    }
    //ReportedAd button click event
    public void ReportAdvertisements(View view) {
        InsertReportAd();
    }
    //Sent reportAd details to database
    private void InsertReportAd() {
        String email = NPEmail.getText().toString();
        String message = NPMessage.getText().toString();
        String reason = SPReason.getSelectedItem().toString();

        ReportedADModel reportedADModel = new ReportedADModel(reportedAdKey,email,message,reason);

        ReportReason.push().setValue(reportedADModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ReportAd.this, "Report Sent", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(ReportAd.this, "Report Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}