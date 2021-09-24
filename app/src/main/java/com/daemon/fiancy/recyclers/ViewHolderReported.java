package com.daemon.fiancy.recyclers;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daemon.fiancy.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderReported extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName, location, age, gender, religion, profession;
        RelativeLayout parentLayout;

        public ViewHolderReported(@NonNull View itemView){
        super(itemView);
        image = itemView.findViewById(R.id.CSprofileimagehome);
        imageName = itemView.findViewById(R.id.CSprofileuser_name);
        location = itemView.findViewById(R.id.CSreportlocation);
        age = itemView.findViewById(R.id.CSreportyear);
        gender = itemView.findViewById(R.id.CSreportgender);
        religion = itemView.findViewById(R.id.CSreportedreligion);
        profession = itemView.findViewById(R.id.CSreportedproffesion);
        parentLayout = itemView.findViewById(R.id.CSRecyclerViewreported);
        }

        public CircleImageView getImage(){
        return image;
        }

        public TextView getImageName(){
        return imageName;
        }

        public TextView getLocation() { return location; }

        public TextView getAge() { return age; }

        public TextView getGender() { return gender; }

        public TextView getReligion() { return religion; }

        public TextView getProfession() { return profession; }

        public RelativeLayout getParentLayout() { return parentLayout; }

}
