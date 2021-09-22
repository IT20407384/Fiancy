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
import com.daemon.fiancy.recyclers.RecyclerViewForMatchFinder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MatchFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Advertisements> DBadList = new ArrayList<>();
    DatabaseReference dbAdRef;
    RecyclerViewForMatchFinder adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container,false);

        dbAdRef = FirebaseDatabase.getInstance().getReference().child("Advertisements");

        recyclerView = view.findViewById(R.id.recycler_matchSM);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new RecyclerViewForMatchFinder(DBadList, view.getContext());
        recyclerView.setAdapter(adapter);

        dbAdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DBadList.clear();
                for(DataSnapshot mySnap : snapshot.getChildren()) {
                    Advertisements advertisement = mySnap.getValue(Advertisements.class);
                    DBadList.add(advertisement);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        return view;
    }
}
