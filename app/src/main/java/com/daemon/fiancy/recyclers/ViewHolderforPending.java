package com.daemon.fiancy.recyclers;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daemon.fiancy.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderforPending extends RecyclerView.ViewHolder {

    CircleImageView image;
    TextView imageName, location, age, gender, religion, profession;
    RelativeLayout parentLayout;

    public ViewHolderforPending(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.CSprofileimagepending);
        imageName = itemView.findViewById(R.id.CSprofileuser_namepending);
        location = itemView.findViewById(R.id.CSpendinglocation);
        age = itemView.findViewById(R.id.CSpendingage);
        gender = itemView.findViewById(R.id.CSpendinggender);
        religion = itemView.findViewById(R.id.CSpendingreligion);
        profession = itemView.findViewById(R.id.CSpendingprofession);
        parentLayout = itemView.findViewById(R.id.CSRecyclerView);
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
