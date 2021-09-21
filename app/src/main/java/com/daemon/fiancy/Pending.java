package com.daemon.fiancy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.recyclers.RecycleViewAdapterforPending;
import com.daemon.fiancy.recyclers.RecyclerViewAdapterForFavorites;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Pending extends Fragment {
    DatabaseReference databaseReference;
    RecycleViewAdapterforPending recycleViewAdapterforPending;
    ArrayList<Advertisements> list;


    private RecyclerView recyclerView;
//    private ArrayList<String> mNames = new ArrayList<>();
//    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending, container,false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Advertisements");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                list.add(snapshot.getValue(Advertisements.class));
            }
                recycleViewAdapterforPending.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        initImageBitmaps();

        recyclerView = view.findViewById(R.id.recycler_viewCS1);
        recyclerView.setLayoutManager((new LinearLayoutManager(view.getContext())));
        recyclerView.setAdapter(new RecycleViewAdapterforPending(list, view.getContext()));

        return view;
    }

    private void initImageBitmaps(){

//        mImageUrls.add("https://lp-cms-production.imgix.net/2019-06/b4fbc706dab2a70a96588309ed268a1a-sri-lanka.jpeg");
//        mNames.add("Seegiriya");
//        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Demodara-Nine-Arch-Bridge.jpg");
//        mNames.add("Ella");
//        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Train-ride-from-Kandy-to-Nuwara-Eliya.jpg");
//        mNames.add("Nuwara Eliya");
//        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Pinnawala-Elephant-Orphanage.jpg");
//        mNames.add("Pinnawala Elephant Orphanage");
//        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Ruins-of-Polonnaruwa.jpg");
//        mNames.add("Polonnaruwa");
//        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Adams-Peak.jpg");
//        mNames.add("Adams Peak");
//        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Mirissa-Fisheries-Harbor.jpg");
//        mNames.add("Mirissa");
//        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Leopards.jpg");
//        mNames.add("Yala National Park");
//        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Colombo.jpg");
//        mNames.add("Colombo");
//        mImageUrls.add("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h400/uploads/2015/06/Jaffna.jpg");
//        mNames.add("Jaffna");
    }
}
