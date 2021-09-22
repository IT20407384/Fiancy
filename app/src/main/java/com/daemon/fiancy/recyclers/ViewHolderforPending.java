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
    TextView imageName,location,age,gender,religion,proffesion;
    RelativeLayout parentLayout;

    public ViewHolderforPending(@NonNull View itemView) {
        super(itemView);
//        image = itemView.findViewById(R.id.CSprofileimagehome);
//        imageName = itemView.findViewById(R.id.CSprofileuser_name);
//        location = itemView.findViewById(R.id.CSlocation);
//        age = itemView.findViewById(R.id.CSage);
//        gender = itemView.findViewById(R.id.CSreligion);
//        religion = itemView.findViewById(R.id.CSreligion);
//        proffesion = itemView.findViewById(R.id.CSproffetion);
//        parentLayout = itemView.findViewById(R.id.parent_layout);
    }

    public CircleImageView getImage() {
        return image;
    }
    public TextView getImageName() {
        return imageName;
    }
}
