package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.models.RejectedAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ProfileReview extends AppCompatActivity {
    EditText reason ;
    DatabaseReference dbref;
    String selectedAdId;
    DatabaseReference adDb;
    String owner;



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
       selectedAdId = intent.getStringExtra("SelectedAD");

        adDb = FirebaseDatabase.getInstance().getReference().child("Advertisements").child(selectedAdId);

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
                    owner = snapshot.child("owner").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void buttonaccept(View view){
        adDb = FirebaseDatabase.getInstance().getReference().child("Advertisements").child(selectedAdId);
        adDb.child("paymentNeeded").setValue(true);

        Toast.makeText(getApplicationContext(),"Now advertisment diaplay in the home page",Toast.LENGTH_SHORT).show();

    }

    public void buttonsave(String data){

        dbref = FirebaseDatabase.getInstance().getReference().child("RejectAds");
        RejectedAds rejectedAds;
        rejectedAds = new RejectedAds();
        rejectedAds.setReason(data);
        dbref.child(owner).setValue(rejectedAds);

        Toast.makeText(getApplicationContext(), "Data successfully inserted", Toast.LENGTH_SHORT).show();
    }

    public void showAlertDialogButtonClicked(View view)
    {

        // Create an alert builder
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setTitle("Reason");

        // set the custom layout
        final View customLayout
                = getLayoutInflater()
                .inflate(
                        R.layout.custom_layout,
                        null);
        builder.setView(customLayout);

        // add a button
        builder
                .setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which)
                            {

                                // send data from the
                                // AlertDialog to the Activity
                                EditText editText
                                        = customLayout
                                        .findViewById(
                                                R.id.editText);
                                sendDialogDataToActivity(
                                        editText
                                                .getText()
                                                .toString());
                            }
                        });

        // create and show
        // the alert dialog
        AlertDialog dialog
                = builder.create();
        dialog.show();
    }

    // Do something with the data
    // coming from the AlertDialog
    private void sendDialogDataToActivity(String data)
    {
        buttonsave(data);

    }

}
