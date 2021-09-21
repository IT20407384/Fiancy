package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.recyclers.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerViewAdapter adapter;
    ArrayList<Advertisements> list;
    EditText Search;


    //LinearLayout profile;
    private static final String TAG = "MainActivity";
    //vars
//    private ArrayList<String> mNames = new ArrayList<>();
//    private ArrayList<String> mImageUrls = new ArrayList<>();

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing email.
    public static final String EMAIL_KEY = "email_key";
    SharedPreferences sharedpreferences;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: started");

        databaseReference = FirebaseDatabase.getInstance().getReference("PendingAdvertisements");


        initImageBitmaps();
        searchView();


        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedpreferences.getString(EMAIL_KEY, null);

        profile = findViewById(R.id.p1);

        DrawerLayout drawerLayout = findViewById(R.id.navDrawer);
        ImageView profileNav = findViewById(R.id.menuInflator);
        ImageView closeNav = findViewById(R.id.pro_Pic);

        profileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

        closeNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(drawerLayout);
            }
        });
    }

    private void searchView() {
        Search = findViewById(R.id.Search);
        //Search function
        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        // open Drawer
        drawerLayout.openDrawer(GravityCompat.START);
    }

    // close Drawer
    private void closeDrawer(DrawerLayout drawerLayout) {
        //close Drawer
        //check
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // when drawer is open
            // close Drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: started");

        initRecyclerView();
    }

    private void initRecyclerView(){


    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: started");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mNames,mImageUrls,this);
//        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this, list);
        recyclerView.setAdapter(adapter);


        //get & set data from firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Advertisements advertisements = dataSnapshot.getValue(Advertisements.class);

                    list.add(advertisements);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }

    public void clicked(View view) {
        if (view == profile) {
            Intent intent = new Intent(this, profile.class);
            startActivity(intent);
        }
    }

    public void clickedMyProfile(View view) {
        Intent intent = new Intent(Home.this, UpdateProfile.class);
        startActivity(intent);
    }
    //    public void clicked(View view) {
//        if (view == profile) {
//            Intent intent = new Intent(this, profile.class);
//            startActivity(intent);
//        }
//    }
    public void Checkprofile(View view) {
        Intent intent = new Intent(Home.this, profile.class);
        startActivity(intent);

    public void clickedMyAdvertisement(View view) {
        Intent intent = new Intent(Home.this, MyAdCollection.class);
        startActivity(intent);
    }

    public void clickedBecomePremium(View view) {
        Intent intent = new Intent(Home.this, PremiumLog.class);
        startActivity(intent);
    }

    public void clickedLogOut(View view) {

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(Home.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    }

    public void Checkfilter(View view) {
        Intent intent = new Intent(Home.this, Filter.class);
        startActivity(intent);
    }
}