package com.daemon.fiancy.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daemon.fiancy.R;
import com.daemon.fiancy.models.Advertisements;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class RecycleViewAdapterforPending extends RecyclerView.Adapter<ViewHolderforPending> {

    private ArrayList<Advertisements> list;
    private Context mContext;

    public  RecycleViewAdapterforPending (ArrayList<Advertisements> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolderforPending onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_pending, parent, false);
        ViewHolderforPending viewHolderforPending = new ViewHolderforPending(view);
        return viewHolderforPending;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderforPending holder, final int position) {
        Log.d(TAG,"onBindViewHolder:called");
        Advertisements advertisements = list.get(position);
        Glide.with(mContext)
                .asBitmap().load(advertisements.getImage1())
                .into(holder.image);

        holder.imageName.setText(advertisements.getFullname());
        holder.location.setText(advertisements.getAddress());
        holder.age.setText(advertisements.getAge());
        holder.gender.setText(advertisements.getGender());
//        holder.religion.setText(advertisements.getReligious());
        holder.proffesion.setText(advertisements.getProfession());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}



