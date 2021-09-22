package com.daemon.fiancy.recyclers;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daemon.fiancy.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderForMatchFinder extends RecyclerView.ViewHolder {

    CircleImageView image;
    TextView imageName, location, age, gender, religion, profession;
    RelativeLayout parentLayout;

    public ViewHolderForMatchFinder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.SMmatchprofile);
        imageName = itemView.findViewById(R.id.SMprofilematchname);
        location = itemView.findViewById(R.id.SMmatchlocation);
        age = itemView.findViewById(R.id.SMmatchage);
        gender = itemView.findViewById(R.id.SMmatchgender);
        religion = itemView.findViewById(R.id.SMmatchreligion);
        profession = itemView.findViewById(R.id.SMmatchprofession);
        parentLayout = itemView.findViewById(R.id.SMMatchfinderParent);
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
