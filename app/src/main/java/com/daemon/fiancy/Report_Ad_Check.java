package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    String downloadUrl1, downloadUrl2, downloadUrl3;

    DatabaseReference adref;
    DatabaseReference dbRef;

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

                    getDownloadUrl();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // get image download url in reported ad for delete
    private void getDownloadUrl() {
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("Advertisements").child(reportedAdKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("image1")){
                    downloadUrl1 = dataSnapshot.child("image1").getValue().toString();
                }
                if(dataSnapshot.hasChild("image2")){
                    downloadUrl2 = dataSnapshot.child("image2").getValue().toString();
                }
                if(dataSnapshot.hasChild("image3")){
                    downloadUrl3 = dataSnapshot.child("image3").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

            }
        });
    }

    // delete the reported photos
    private void deletePhotos() {
        if(!TextUtils.isEmpty(downloadUrl1)) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(downloadUrl1);
            storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("downloadURL", "Image deleted successful1");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                }
            });
        }
        if(!TextUtils.isEmpty(downloadUrl2)) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(downloadUrl2);
            storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("downloadURL", "Image deleted successful2");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                }
            });
        }
        if(!TextUtils.isEmpty(downloadUrl3)) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(downloadUrl3);
            storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("downloadURL", "Image deleted successful3");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                }
            });
        }
    }

    public  void buttondacceptrejection(View view){

        // first delete reported photos
        deletePhotos();

        // delete advertisement after the delete photos
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
