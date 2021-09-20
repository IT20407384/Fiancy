package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.daemon.fiancy.recyclers.RecyclerViewAdapter;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
   //LinearLayout profile;
   private static final String TAG = "MainActivity";
    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: started");
        initImageBitmaps();

        // profile = findViewById(R.id.p1);
    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: started");
        mImageUrls.add("https://lp-cms-production.imgix.net/2019-06/b4fbc706dab2a70a96588309ed268a1a-sri-lanka.jpeg");
                mNames.add("Seegiriya");
        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Demodara-Nine-Arch-Bridge.jpg");
        mNames.add("Ella");
        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Train-ride-from-Kandy-to-Nuwara-Eliya.jpg");
        mNames.add("Nuwara Eliya");
        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Pinnawala-Elephant-Orphanage.jpg");
        mNames.add("Pinnawala Elephant Orphanage");
        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Ruins-of-Polonnaruwa.jpg");
        mNames.add("Polonnaruwa");
        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Adams-Peak.jpg");
        mNames.add("Adams Peak");
        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Mirissa-Fisheries-Harbor.jpg");
        mNames.add("Mirissa");
        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Leopards.jpg");
        mNames.add("Yala National Park");
        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Colombo.jpg");
        mNames.add("Colombo");
        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Jaffna.jpg");
        mNames.add("Jaffna");
        initRecyclerView();
    }
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: started");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mNames,mImageUrls,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

//    public void clicked(View view) {
//        if (view == profile) {
//            Intent intent = new Intent(this, profile.class);
//            startActivity(intent);
//        }
//    }

}