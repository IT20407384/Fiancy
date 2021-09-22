package com.daemon.fiancy;

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

    private RecyclerView recyclerView;
    private ArrayList<Advertisements> dbAdList = new ArrayList<>();
    private ArrayList<String> adKey = new ArrayList<>();
    DatabaseReference dbadvertisementRef;
    RecycleViewAdapterforPending adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending, container,false);

        dbadvertisementRef = FirebaseDatabase.getInstance().getReference().child("Advertisements");

        recyclerView = view.findViewById(R.id.recycler_pendingCS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(view.getContext())));

        adapter = new RecycleViewAdapterforPending(dbAdList, adKey, view.getContext());
        recyclerView.setAdapter(adapter);

        dbadvertisementRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbAdList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Advertisements advertisement = dataSnapshot.getValue(Advertisements.class);
                    assert advertisement != null;
                    if(!advertisement.getPaymentNeeded() && !advertisement.getLiveAdvertisement()) {
                        dbAdList.add(advertisement);
                        adKey.add(dataSnapshot.getKey());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
