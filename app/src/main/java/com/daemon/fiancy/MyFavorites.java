package com.daemon.fiancy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.recyclers.RecyclerViewAdapterForFavorites;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyFavorites extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Advertisements> DBadList = new ArrayList<>();
    private ArrayList<String> adKey = new ArrayList<>();
    DatabaseReference dbFavRef, dbAdRef;
    RecyclerViewAdapterForFavorites adapter;
    Advertisements advertisements = new Advertisements();

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    SharedPreferences sharedpreferences;
    String emailShared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE); }
        catch (NullPointerException e) { Log.d("Share Error", e.toString()); }

        emailShared = sharedpreferences.getString(EMAIL_KEY, null);

        View view = inflater.inflate(R.layout.fragment_my_favorites, container,false);

        dbFavRef = FirebaseDatabase.getInstance().getReference().child("Favorites").child(emailShared);
        dbAdRef = FirebaseDatabase.getInstance().getReference().child("Advertisements");

        recyclerView = view.findViewById(R.id.recycler_favoriteSM);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new RecyclerViewAdapterForFavorites(DBadList, adKey, emailShared, view.getContext());
        recyclerView.setAdapter(adapter);

        dbFavRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren()) {
                    ArrayList<String> favouredADs = new ArrayList<>();
                    favouredADs = (ArrayList<String>) snapshot.child("advertisementKeys").getValue();

                    DBadList.clear();

                    assert favouredADs != null;
                    for(String adID : favouredADs) {
                        dbAdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(adID)) {
                                    advertisements = snapshot.child(adID).getValue(Advertisements.class);
                                    DBadList.add(advertisements);
                                    adKey.add(adID);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        return view;
    }
}