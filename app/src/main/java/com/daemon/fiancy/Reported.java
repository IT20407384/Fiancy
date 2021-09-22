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
import com.daemon.fiancy.recyclers.RecyclerViewforReported;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Reported extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Advertisements>dblist = new ArrayList<>();
    private ArrayList<String> rpkey = new ArrayList<>();
    DatabaseReference dbrefference;
    RecyclerViewforReported rcadpter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reported, container,false);

        dbrefference = FirebaseDatabase.getInstance().getReference().child("Advertisements");

        recyclerView = view.findViewById(R.id.recycler_viewCS2);
        recyclerView.setLayoutManager((new LinearLayoutManager(view.getContext())));

        rcadpter = new RecyclerViewforReported(dblist,rpkey,view.getContext());
        recyclerView.setAdapter(rcadpter);

        dbrefference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dblist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Advertisements advertisement = dataSnapshot.getValue(Advertisements.class);
                    assert advertisement != null;
                    if(!advertisement.getPaymentNeeded() && !advertisement.getLiveAdvertisement()) {
                        dblist.add(advertisement);
                        rpkey.add(dataSnapshot.getKey());
                    }
                }
                rcadpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }

}
