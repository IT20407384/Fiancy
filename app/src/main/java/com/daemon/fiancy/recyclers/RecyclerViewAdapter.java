package com.daemon.fiancy.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daemon.fiancy.R;
import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "test.sliit.recyclerview.RecyclerViewAdapter";

    public List<Advertisements> list = new ArrayList<>();
    public List<Advertisements> getAdvertisementFilter = new ArrayList<>();
    public Context mContext;


    public RecyclerViewAdapter(Context mContext, List<Advertisements> list) {
        this.mContext = mContext;
        this.list = list;
        this.getAdvertisementFilter = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_profile, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Advertisements advertisements = list.get(position);
        // set default image for who not upload a photo
        if(advertisements.getImage1() != null) {
            Glide.with(mContext)
                    .asBitmap().load(advertisements.getImage1())
                    .into(holder.image);
        } else {
            // gender = male
            if (advertisements.getGender().equals("Male")) {
                Glide.with(mContext)
                        .asBitmap().load("https://cdn-icons-png.flaticon.com/512/2922/2922510.png")
                        .into(holder.image);
            }
            // gender = female
            if (advertisements.getGender().equals("Female")) {
                Glide.with(mContext)
                        .asBitmap().load("https://cdn-icons-png.flaticon.com/512/2922/2922561.png")
                        .into(holder.image);
            }
        }

        holder.imageName.setText(advertisements.getFullname());
        holder.location.setText(advertisements.getAddress());
        holder.age.setText(advertisements.getAge());
        holder.gender.setText(advertisements.getGender());
        holder.religion.setText(advertisements.getReligion());
        holder.profession.setText(advertisements.getProfession());

        // single post document key
        String key = advertisements.getDocumentKey();

        // see one post at home
        holder.singlepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, profile.class);
                intent.putExtra("documetKey", key);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0) {
                    filterResults.values = getAdvertisementFilter;
                    filterResults.count = getAdvertisementFilter.size();
                } else {
                    String searchStr = constraint.toString().toLowerCase();
                    List<Advertisements> adverts = new ArrayList<>();
                    for(Advertisements advert : getAdvertisementFilter) {
                        if(advert.getFullname().toLowerCase().contains(searchStr)) {
                            adverts.add(advert);
                        }
                    }

                    filterResults.values = adverts;
                    filterResults.count = adverts.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<Advertisements>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView imageName, location, age, gender, religion, profession;
        RelativeLayout parentLayout;
        LinearLayout singlepost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profileimagehome);
            imageName = itemView.findViewById(R.id.profileuser_name);
            location = itemView.findViewById(R.id.NPlocation);
            age = itemView.findViewById(R.id.NPAge);
            gender = itemView.findViewById(R.id.NPGender);
            profession = itemView.findViewById(R.id.NPProfession);
            religion = itemView.findViewById(R.id.NPReligion);
            singlepost = itemView.findViewById(R.id.p1);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}