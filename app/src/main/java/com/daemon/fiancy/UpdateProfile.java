package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daemon.fiancy.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String emailShared;

    UserModel userModel;
    EditText updateName;
    EditText updateEmail;
    EditText updatePhone;
    EditText oldPassword;
    EditText newPassword;
    Button updateProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        userModel = new UserModel();
        updateName = findViewById(R.id.SMupdatename);
        updateEmail = findViewById(R.id.SMupdateemail);
        updatePhone = findViewById(R.id.SMupdatephone);
        oldPassword = findViewById(R.id.SMupdatecurrentpassword);
        newPassword = findViewById(R.id.SMupdatenewpassword);
        updateProfile = findViewById(R.id.SMupdateprofilebtn);

        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        emailShared = sharedpreferences.getString(EMAIL_KEY, null);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("AppUser");

        final String[] docChild = {null};

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot mySnap : snapshot.getChildren()) {
                    userModel = mySnap.getValue(UserModel.class);

                    assert userModel != null;
                    if(userModel.getEmail().equalsIgnoreCase(emailShared)) {
                        docChild[0] = mySnap.getChildren().toString();
                        break;
                    }
                }
                updateName.setText(userModel.getFullName());
                updatePhone.setText(userModel.getPhoneNumber());
                updateEmail.setText(userModel.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("AppUser").child(docChild[0]);
                if(TextUtils.isEmpty(oldPassword.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter Your Current Password", Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(newPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), docChild[0], Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}