package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_review);

        ImageView image1 = findViewById(R.id.CSimageView3);
        ImageView image2 = findViewById(R.id.CSimageView);
        ImageView image3 = findViewById(R.id.CSimageView2);
        TextView profileName = findViewById(R.id.textView24);
        TextView age = findViewById(R.id.CStextage);
        TextView profession = findViewById(R.id.CStextproffesion);
        TextView address = findViewById(R.id.textView22);
        TextView description = findViewById(R.id.textView23);

        Intent intent = getIntent();
        String selectedAdId = intent.getStringExtra("SelectedAD");

        DatabaseReference adDb = FirebaseDatabase.getInstance().getReference().child("Advertisements").child(selectedAdId);

        adDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild("image1"))
                    Glide.with(ProfileReview.this).asBitmap().load(snapshot.child("image1").getValue().toString()).into(image1);

                if(snapshot.hasChild("image2"))
                    Glide.with(ProfileReview.this).load(snapshot.child("image2").getValue().toString()).into(image2);

                if(snapshot.hasChild("image3"))
                    Glide.with(ProfileReview.this).load(snapshot.child("image3").getValue().toString()).into(image3);

                    profileName.setText(snapshot.child("fullname").getValue().toString());
                    age.setText(snapshot.child("age").getValue().toString());
                    profession.setText(snapshot.child("profession").getValue().toString());
                    address.setText(snapshot.child("address").getValue().toString());
                    description.setText(snapshot.child("description").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_reject_reason, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);



        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.setFocusable(true);
                return true;
            }
        });

    }
}