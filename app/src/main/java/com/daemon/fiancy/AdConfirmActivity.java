package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daemon.fiancy.models.Advertisements;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdConfirmActivity extends AppCompatActivity {
    // initialization
    ImageView back;
    TextView advertID, adFee, adDiscount, adTotFee;
    CheckBox TandC;
    ProgressBar progressBar;

    // firebase
    DatabaseReference myRef;

    // ad id
    String adID = "AD001";

    double getFee;
    double fee;
    double discount = 155.65;
    double totFee;

    // get from previous activity
    Advertisements advertisements;
    Uri filePath1;
    Uri filePath2;
    Uri filePath3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_confirm);

        // back button in toolbar
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdConfirmActivity.this, PostAdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        // get form filled details in previous activity (activity_post_ad)
        advertisements = getIntent().getParcelableExtra("advertisements");
        filePath1 = getIntent().getParcelableExtra("image1");
        filePath2 = getIntent().getParcelableExtra("image2");
        filePath3 = getIntent().getParcelableExtra("image3");

        // call functions for calculate total fee
        int words = countWords(advertisements.getDescription());
        double total = calculateAdFee(words);
        totFee = roundToTwodecimal(total);
        fee = roundToTwodecimal(getFee);

        // get instance()
        getInstances();

        // setting up data functions
        setData();
    }

    // get instances
    private void getInstances() {
        advertID = findViewById(R.id.advertID);
        adFee = findViewById(R.id.ADfee);
        adDiscount = findViewById(R.id.adDiscount);
        adTotFee = findViewById(R.id.adTotFee);
        progressBar = findViewById(R.id.adUploadingProgress);
        TandC = findViewById(R.id.TandC);
    }

    // set data for textViews
    private void setData() {
        advertID.setText(String.format("AD ID :- %s", adID));
        adFee.setText(String.format(": Rs. %s", fee));
        adDiscount.setText(String.format(": Rs. %s", discount));
        adTotFee.setText(String.format(": Rs. %s", totFee));
    }

    // get description word count for calculate the advertisement fee
    private int countWords(String description) {
        String words = description.trim();
        if (words.isEmpty())
            return 0;
        return words.split("\\s+").length; // separate string around spaces
    }

    // calculate total fee
    private double calculateAdFee(int wordCount) {
        int fixedFee = 2500;
        double feePerWord = 15.50;
        double countFeeForWord = feePerWord * wordCount;
        getFee = fixedFee + countFeeForWord;
        return getFee - discount;
    }

    // get image file extension
    private String getFileExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));
    }

    // upload data to firebase
    private void uploadData() {

        progressBar.setVisibility(View.VISIBLE);
        final StorageReference imageFolder = FirebaseStorage.getInstance().getReference().child("Images");

        Boolean n1 = (filePath1!=null && filePath2 != null && filePath3 != null);
        Boolean n2 = (filePath1!=null && filePath2 != null);
        Boolean n3 = (filePath1!=null && filePath3 != null);


        // check images is null
        if(filePath1 != null) {
            // generate name for images
            StorageReference imageName1 = imageFolder.child(System.currentTimeMillis()+"Image01."+getFileExt(filePath1));
            imageName1.putFile(filePath1);
            advertisements.setImage1(imageName1.toString());

        }

        if(filePath2 != null) {
            StorageReference imageName2 = imageFolder.child(System.currentTimeMillis()+20+"Image02."+getFileExt(filePath2));
            imageName2.putFile(filePath2);
            advertisements.setImage2(imageName2.toString());
        }

        if(filePath3 != null) {
            StorageReference imageName3 = imageFolder.child(System.currentTimeMillis()+40+"Image03."+getFileExt(filePath3));
            imageName3.putFile(filePath3);
            advertisements.setImage3(imageName3.toString());

        }



        // upload to the firebase
        myRef =  FirebaseDatabase.getInstance().getReference().child("Advertisements");

        // when uploaded success
        myRef.push().setValue(advertisements).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.INVISIBLE);

                View adconfirmlayout = findViewById(R.id.adconfirmlayout);

                Snackbar snackbar = Snackbar
                        .make(adconfirmlayout, "Your advertisement posting as pending", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    // convert to two decimal
    private double roundToTwodecimal(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

    public void adConfirmation(View view) {
        if(TandC.isChecked()) {
            uploadData();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }, 2000);

        } else {
            Toast.makeText(this, "Agree to the T & C", Toast.LENGTH_SHORT).show();
        }

    }

    public void btnCancel(View view) {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
    }
}