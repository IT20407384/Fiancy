package com.daemon.fiancy.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daemon.fiancy.Home;
import com.daemon.fiancy.MyAdCollection;
import com.daemon.fiancy.R;
import com.daemon.fiancy.UpdateProfile;
import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterForFavorites extends RecyclerView.Adapter<ViewHolderForFavorites> {

    private ArrayList<Advertisements> dbAdList = new ArrayList<>();
    private ArrayList<String> adKeys = new ArrayList<>();
    private Context mContext;
    String user;

    public RecyclerViewAdapterForFavorites(ArrayList<Advertisements> dbAdList, ArrayList<String> adKeys, String user, Context mContext) {
        this.dbAdList = dbAdList;
        this.adKeys = adKeys;
        this.mContext = mContext;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolderForFavorites onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_favorites, parent, false);
        ViewHolderForFavorites viewHolderForFavorites = new ViewHolderForFavorites(view);
        return viewHolderForFavorites;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderForFavorites holder, final int position) {

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

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, profile.class);
                intent.putExtra("documetKey", adKeys.get(position));
                mContext.startActivity(intent);
            }
        });

        holder.favDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure want to remove ?");
                builder.setTitle("Alert !");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adKeys.remove(position);
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Favorites");
                        databaseReference.child(user).child("advertisementKeys").setValue(adKeys);
                        Intent intent = new Intent(mContext, MyAdCollection.class);
                        mContext.startActivity(intent);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return dbAdList.size();
    }
}
