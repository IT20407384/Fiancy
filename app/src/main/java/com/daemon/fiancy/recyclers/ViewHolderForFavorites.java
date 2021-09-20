package com.daemon.fiancy.recyclers;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daemon.fiancy.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderForFavorites extends RecyclerView.ViewHolder {

    CircleImageView image;
    TextView imageName;
    RelativeLayout parentLayout;

    public ViewHolderForFavorites(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.SMfavoritesprofile);
        imageName = itemView.findViewById(R.id.SMprofilefavoritename);
        parentLayout = itemView.findViewById(R.id.recycler_viewSM);
    }

    public CircleImageView getImage() {
        return image;
    }
    public TextView getImageName() {
        return imageName;
    }
}
