package com.daemon.fiancy.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daemon.fiancy.R;
import com.daemon.fiancy.models.Advertisements;

import java.util.ArrayList;

public class RecyclerViewforReported extends RecyclerView.Adapter<ViewHolderReported>{
    private ArrayList<Advertisements> advertisementsArrayList = new ArrayList<>();
    private ArrayList<String> rpkey = new ArrayList<>();
    private Context mContext;

    public RecyclerViewforReported (ArrayList<Advertisements> advertisementsArrayList, ArrayList<String>
            rpkey , Context mContext) {
        this.advertisementsArrayList = advertisementsArrayList;
        this.rpkey = rpkey;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderReported onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_reported, parent, false);
        ViewHolderReported viewHolderReported = new ViewHolderReported(view);
        return viewHolderReported;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderReported holder, final int position) {
        Advertisements advertisement = advertisementsArrayList.get(position);

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
    }

    @Override
    public int getItemCount() {
        return advertisementsArrayList.size();
    }
}


