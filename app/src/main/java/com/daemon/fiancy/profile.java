package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.models.Favorites;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {
    // firebase
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    // model class
    Advertisements singleAdvertisement;
    // initializations
    ImageView profileimageinAd, Gender, heartFav;
    TextView location, fullname, age, profession, religion, minEducation,
    description, contact, hobbieListView;

    String documentKey;
    List<String> hobbieList;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    SharedPreferences sharedpreferences;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedpreferences.getString(EMAIL_KEY, null);

        //get Instance()
        getInstance();
    }

    private void getInstance() {
        profileimageinAd = findViewById(R.id.NPImage);
        Gender = findViewById(R.id.ic_gender);
        fullname = findViewById(R.id.NPfullname);
        location = findViewById(R.id.NPlocation);
        age = findViewById(R.id.NPAge);
        profession = findViewById(R.id.NPProfession);
        religion = findViewById(R.id.NPReligion);
        minEducation = findViewById(R.id.NPMinEducationLevel);
        description = findViewById(R.id.NPDescription);
        contact = findViewById(R.id.NPContact);
        hobbieListView = findViewById(R.id.NPhobieList);
        heartFav = findViewById(R.id.user_profileHeart);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // get document key of current post
        Intent intent = getIntent();
        documentKey = intent.getExtras().getString("documetKey");

        // call functions
        getAdvertisementData(documentKey);
        setFavorites(documentKey);
    }

    // get data from database to single post
    private void getAdvertisementData(String docKey) {

        //get & set data from firebase
        databaseReference.child("Advertisements").child(docKey).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                singleAdvertisement = dataSnapshot.getValue(Advertisements.class);
                setData();
            }
        });
    }

    private void setData() {
        // set profile picture
        if(singleAdvertisement.getImage1() != null) {
            Glide.with(getApplicationContext())
                    .asBitmap().load(singleAdvertisement.getImage1())
                    .into(profileimageinAd);
        } else {
            // gender = male
            if (singleAdvertisement.getGender().equals("Male")) {
                Glide.with(getApplicationContext())
                        .asBitmap().load("https://cdn-icons-png.flaticon.com/512/2922/2922510.png")
                        .into(profileimageinAd);
            }
            // gender = female
            if (singleAdvertisement.getGender().equals("Female")) {
                Glide.with(getApplicationContext())
                        .asBitmap().load("https://cdn-icons-png.flaticon.com/512/2922/2922561.png")
                        .into(profileimageinAd);
            }
        }

        // set gender icon
        if (singleAdvertisement.getGender().equals("Male")) {
            Glide.with(getApplicationContext())
                    .asBitmap().load("https://img.icons8.com/emoji/50/000000/male-sign-emoji.png")
                    .into(Gender);
        } else if(singleAdvertisement.getGender().equals("Female")) {
            Glide.with(getApplicationContext())
                    .asBitmap().load("https://img.icons8.com/external-flatart-icons-flat-flatarticons/50/000000/external-female-womens-day-flatart-icons-flat-flatarticons.png")
                    .into(Gender);
        }

        fullname.setText(singleAdvertisement.getFullname());
        location.setText(singleAdvertisement.getAddress());
        age.setText(String.format(singleAdvertisement.getAge()+" Years old"));
        religion.setText(singleAdvertisement.getReligion());
        minEducation.setText(singleAdvertisement.getMinEducatuinLevel());
        description.setText(singleAdvertisement.getDescription());
        contact.setText(singleAdvertisement.getPhone());

        // set hobbielist to list array
        hobbieList = singleAdvertisement.getHobbiesList();
        sethobbiestoListView();
    }

    // set hobbies list to list view
    private void sethobbiestoListView() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i<hobbieList.size(); i++) {
            str.append(hobbieList.get(i)+", ");
        }

        hobbieListView.setText(str);
    }

    // go to report ad activty
    public void report(View view) {
        Intent intent = new Intent(profile.this, ReportAd.class);
        intent.putExtra("ReportedAdKey", documentKey);
        startActivity(intent);
    }

    public void setFavorites(String docKey) {

        DatabaseReference favDbRef = FirebaseDatabase.getInstance().getReference().child("Favorites").child(email);

        favDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    ArrayList<String> existingFavorites = (ArrayList<String>) snapshot.child("advertisementKeys").getValue();

                    assert existingFavorites != null;
                    for(String savedAdkey : existingFavorites) {
                        if(savedAdkey.equals(docKey)) {
                            heartFav.setImageResource(R.drawable.ic_baseline_favorite_24);
                            heartFav.setEnabled(false);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


        heartFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                favDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()) {
                            ArrayList<String> existingFavorites = (ArrayList<String>) snapshot.child("advertisementKeys").getValue();
                            assert existingFavorites != null;
                            existingFavorites.add(docKey);
                            favDbRef.child("advertisementKeys").setValue(existingFavorites);
                        }
                        else {
                            ArrayList<String> adKeys = new ArrayList<>();
                            adKeys.add(docKey);
                            favDbRef.child("advertisementKeys").setValue(adKeys);
                        }
                        heartFav.setImageResource(R.drawable.ic_baseline_favorite_24);
                        heartFav.setEnabled(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });
    }
}