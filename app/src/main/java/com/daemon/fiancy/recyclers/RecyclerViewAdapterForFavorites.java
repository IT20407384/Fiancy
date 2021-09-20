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

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterForFavorites extends RecyclerView.Adapter<ViewHolderForFavorites> {

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterForFavorites(ArrayList<String> mImageNames, ArrayList<String>
            mImage, Context mContext) {
        this.mImageNames = mImageNames;
        this.mImage = mImage;
        this.mContext = mContext;
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
        holder.imageName.setText(mImageNames.get(position));
        Glide.with(mContext)
                .asBitmap().load(mImage.get(position))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }
}
