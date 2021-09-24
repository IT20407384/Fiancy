package com.daemon.fiancy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
    private ArrayList<String> adKey = new ArrayList<>();
    DatabaseReference dbAdRef;
    RecyclerViewForMatchFinder adapter;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    SharedPreferences sharedpreferences;
    String emailShared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE); }
        catch (NullPointerException e) { Log.d("Share Error", e.toString()); }

        emailShared = sharedpreferences.getString(EMAIL_KEY, null);

        View view = inflater.inflate(R.layout.fragment_match, container,false);

        dbAdRef = FirebaseDatabase.getInstance().getReference().child("Advertisements");

        recyclerView = view.findViewById(R.id.recycler_matchSM);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new RecyclerViewForMatchFinder(DBadList, adKey, emailShared, view.getContext());
        recyclerView.setAdapter(adapter);

        dbAdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DBadList.clear();
                for(DataSnapshot mySnap : snapshot.getChildren()) {
                    Advertisements advertisement = mySnap.getValue(Advertisements.class);
                    assert advertisement != null;
                    if(advertisement.getLiveAdvertisement()) {
                        DBadList.add(advertisement);
                        adKey.add(mySnap.getKey());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        return view;
    }
}
