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
        TextView imageName;
        RelativeLayout parentLayout;

public ViewHolderReported(@NonNull View itemView){
        super(itemView);
        image=itemView.findViewById(R.id.CSprofileimagehome);
        imageName=itemView.findViewById(R.id.CSprofileuser_name);
        parentLayout=itemView.findViewById(R.id.recycler_viewCS);
        }

public CircleImageView getImage(){
        return image;
        }
public TextView getImageName(){
        return imageName;
        }
}
