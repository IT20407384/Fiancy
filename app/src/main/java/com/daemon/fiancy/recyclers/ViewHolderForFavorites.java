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
    TextView imageName, location, age, gender, religion, profession;
    RelativeLayout parentLayout;

    public ViewHolderForFavorites(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.SMfavoritesprofile);
        imageName = itemView.findViewById(R.id.SMprofilefavoritename);
        location = itemView.findViewById(R.id.SMfavoritelocation);
        age = itemView.findViewById(R.id.SMfavoritesage);
        gender = itemView.findViewById(R.id.SMfavoritesgender);
        religion = itemView.findViewById(R.id.SMfavoritesreligion);
        profession = itemView.findViewById(R.id.SMfavoriteprofession);
        parentLayout = itemView.findViewById(R.id.SMfavoritesparent);
    }

    public CircleImageView getImage() {
        return image;
    }

    public TextView getImageName() {
        return imageName;
    }

    public TextView getLocation() {
        return location;
    }

    public TextView getAge() {
        return age;
    }

    public TextView getGender() {
        return gender;
    }

    public TextView getReligion() {
        return religion;
    }

    public TextView getProfession() {
        return profession;
    }

    public RelativeLayout getParentLayout() {
        return parentLayout;
    }
}
