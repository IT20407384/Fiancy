package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManageAdvertisementActiviy extends AppCompatActivity {
    String TAG = "Fiancy manage advertisement    :   ";
    // initialization
    Spinner EducationDropdown;
    CheckBox cbReading, cbCollecting, cbMusic, cbGardening, cbGames, cbFishing,
            cbWalking, cbShopping, cbTraveling, cbWatchingSports, cbEatingOut, cbDancing;
    EditText fullname, age, description, profession, address, phone, upReligion;
    ImageView postImage1, postImage2, postImage3;
    RadioGroup radioGroupGender, radioGroupStatus;
    RadioButton gender, statusRadioBtn;
    ArrayList<String> hobbieList;
    Uri filePath1, filePath2, filePath3;

    String FullName, Age, Gender, Status, Description, Profession, Address, Phone,
            EduLevel, religion;

    // firebase
    FirebaseDatabase db;
    DatabaseReference dbRef;

    // get document relevant document key
    String documentKey = getIntent().getExtras().getString("DocumentKey");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_advertisement);

        Log.d(TAG, "Application is created");

        // instances()
        getInstances();
    }

    private void getInstances() {
        // edittexts
        fullname = findViewById(R.id.etFullName);
        age = findViewById(R.id.etAge);
        description = findViewById(R.id.etDescription);
        profession = findViewById(R.id.etProffesion);
        address = findViewById(R.id.etAddress);
        phone = findViewById(R.id.etPhone);
        upReligion = findViewById(R.id.SPReligion);
        // spinner
        EducationDropdown = findViewById(R.id.SPEducationLevel);
        // image views
        postImage1 = findViewById(R.id.insertImg1);
        postImage2 = findViewById(R.id.insertImg2);
        postImage3 = findViewById(R.id.insertImg3);
        // radios
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on resume");
        
        
        // functions
        getAdvertisementFilleddetails();


    }

    private void getAdvertisementFilleddetails() {
        Log.d("docKey", documentKey);
    }

    // delete button
    public void deleteAd(View view) {
        Intent intent = new Intent(getApplicationContext(), deleteAdvertisementActivity.class);
        startActivity(intent);
    }
}