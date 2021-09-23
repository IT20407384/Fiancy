package com.daemon.fiancy.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daemon.fiancy.R;
import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.profile;

import java.util.ArrayList;

public class RecyclerViewForMatchFinder extends RecyclerView.Adapter<ViewHolderForMatchFinder> {

    private ArrayList<Advertisements> dbAdList = new ArrayList<>();
    private ArrayList<String> adKeys = new ArrayList<>();
    private Context mContext;
    String user;

    public RecyclerViewForMatchFinder(ArrayList<Advertisements> dbAdList, ArrayList<String> adKeys, String user, Context mContext ) {
        this.dbAdList = dbAdList;
        this.adKeys = adKeys;
        this.mContext = mContext;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolderForMatchFinder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_matchfinder, parent, false);
        ViewHolderForMatchFinder viewHolderForMatchFinder = new ViewHolderForMatchFinder(view);
        return viewHolderForMatchFinder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderForMatchFinder holder, final int position) {

        Advertisements advertisement = dbAdList.get(position);

        if(advertisement.getImage1() != null) {
            Glide.with(mContext)
                    .asBitmap().load(advertisement.getImage1())
                    .into(holder.image);
        } else {
            // gender = male
            if (advertisement.getGender().equals("Male")) {
                Glide.with(mContext)
                        .asBitmap().load("https://cdn-icons-png.flaticon.com/512/2922/2922510.png")
                        .into(holder.image);
            }
            // gender = female
            if (advertisement.getGender().equals("Female")) {
                Glide.with(mContext)
                        .asBitmap().load("https://cdn-icons-png.flaticon.com/512/2922/2922561.png")
                        .into(holder.image);
            }
        }

        holder.imageName.setText(advertisement.getFullname());
        holder.location.setText(advertisement.getAddress());
        holder.age.setText(advertisement.getAge());
        holder.gender.setText(advertisement.getGender());
        holder.religion.setText(advertisement.getReligion());
        holder.profession.setText(advertisement.getProfession());

        holder.matchPercentage.setText(advertisement.getAge() + "%");

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, profile.class);
                intent.putExtra("documetKey", adKeys.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dbAdList.size();
    }
}

