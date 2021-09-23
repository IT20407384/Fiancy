package com.daemon.fiancy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.recyclers.RecycleViewAdapterforPending;
import com.daemon.fiancy.recyclers.RecyclerViewAdapterForFavorites;
import com.daemon.fiancy.recyclers.RecyclerViewforReported;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class Reported extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Advertisements>dblist = new ArrayList<>();
    private ArrayList<String> rpkey = new ArrayList<>();
    DatabaseReference dbrefference,reporteddbref;
    RecyclerViewforReported rcadpter;
    String reportadkey;
    Advertisements advertisements;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reported, container,false);

        dbrefference = FirebaseDatabase.getInstance().getReference().child("Advertisements");
        reporteddbref = FirebaseDatabase.getInstance().getReference().child("ReportedAdvertisements");

        recyclerView = view.findViewById(R.id.recycler_viewCS2);
        recyclerView.setLayoutManager((new LinearLayoutManager(view.getContext())));

        rcadpter = new RecyclerViewforReported(dblist,rpkey,view.getContext());
        recyclerView.setAdapter(rcadpter);


        reporteddbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        reportadkey = dataSnapshot.child("reportedAdKey").getValue().toString();
                        dbrefference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                advertisements = snapshot.child(reportadkey).getValue(Advertisements.class);
                                dblist.add(advertisements);
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                }

                rcadpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });



//        dbrefference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                dblist.clear();
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Advertisements advertisement = dataSnapshot.getValue(Advertisements.class);
//                    assert advertisement != null;
//                    if(!advertisement.getPaymentNeeded() && !advertisement.getLiveAdvertisement()) {
//                        dblist.add(advertisement);
//                        rpkey.add(dataSnapshot.getKey());
//                    }
//                }
//                rcadpter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        return view;

    }

}
