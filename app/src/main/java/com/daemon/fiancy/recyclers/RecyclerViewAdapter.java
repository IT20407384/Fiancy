package com.daemon.fiancy.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daemon.fiancy.R;
import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.profile;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {
    private static final String TAG = "test.sliit.recyclerview.RecyclerViewAdapter";

    public ArrayList<Advertisements> list;
    public ArrayList<Advertisements> arrayListCopy;
    private Context mContext;


    public RecyclerViewAdapter(Context mContext,ArrayList<Advertisements> list) {
        this.mContext = mContext;
        this.list = list;
        this.arrayListCopy = new ArrayList<>();
        arrayListCopy.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_profile, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Advertisements advertisements = list.get(position);
//        Glide.with(mContext)
//                .asBitmap().load(advertisements.getImage1())
//                .into(holder.image);

        holder.imageName.setText(advertisements.getFullname());
        holder.location.setText(advertisements.getAddress());
        holder.age.setText(advertisements.getAge());
        holder.gender.setText(advertisements.getGender());
        holder.profession.setText(advertisements.getProfession());
//        holder.imageName.setText(mImageNames.get(position));

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, profile.class);
//                intent.putExtra("FullName",advertisements.getFullname());
//                intent.putExtra("age",advertisements.getAge());
//                intent.putExtra("gender",advertisements.getGender());
//                intent.putExtra("status",advertisements.getStatus());
//                intent.putExtra("description",advertisements.getDescription());
//                intent.putExtra("profession",advertisements.getProfession());
//                intent.putExtra("address",advertisements.getAddress());
//                intent.putExtra("phone",advertisements.getPhone());
//                intent.putExtra("email",advertisements.getEmail());
//                intent.putExtra(" minEducatuinLevel",advertisements.getMinEducatuinLevel());
//                intent.putExtra("hobbiesList",advertisements.getHobbiesList());
//               mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

  
    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView imageName,location,age,gender,religion,profession;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profileimagehome);
            imageName = itemView.findViewById(R.id.profileuser_name);
            location = itemView.findViewById(R.id.NPlocation);
            age = itemView.findViewById(R.id.NPAge);
            gender = itemView.findViewById(R.id.NPGender);
            profession = itemView.findViewById(R.id.NPProfession);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
    //Search Filter
    public void filter(CharSequence charSequence){
            ArrayList<Advertisements> tempArrayList = new ArrayList<>();
            if(!TextUtils.isEmpty(charSequence)){
               for(Advertisements advertisements : list){
                    if(advertisements.getFullname().toLowerCase().contains(charSequence)){
                        tempArrayList.add(advertisements);

                    }
               }
            }else {
                tempArrayList.addAll(arrayListCopy);
            }

            list.clear();
            list.addAll(tempArrayList);
            notifyDataSetChanged();
            tempArrayList.clear();

    }


}