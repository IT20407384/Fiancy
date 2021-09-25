package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.models.Favorites;
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
    ImageView backBtn;

    String oldPasswordHolder, key;
    boolean primeState;

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
        backBtn = findViewById(R.id.back);

        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        emailShared = sharedpreferences.getString(EMAIL_KEY, null);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("AppUser");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot mySnap : snapshot.getChildren()) {
                    userModel = mySnap.getValue(UserModel.class);

                    assert userModel != null;
                    if(userModel.getEmail().equalsIgnoreCase(emailShared)) {
                        updateName.setText(userModel.getFullName());
                        updatePhone.setText(userModel.getPhoneNumber());
                        updateEmail.setText(userModel.getEmail());
                        oldPasswordHolder = userModel.getPassword();
                        key = mySnap.getKey();
                        primeState = userModel.isuPremium();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((TextUtils.isEmpty(updateName.getText().toString())) || (TextUtils.isEmpty(updateEmail.getText().toString())))
                    Toast.makeText(getApplicationContext(), "Cannot be any Empty Fields", Toast.LENGTH_SHORT).show();

                else if(TextUtils.isEmpty(oldPassword.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter Your Current Password", Toast.LENGTH_SHORT).show();

                else if(TextUtils.isEmpty(newPassword.getText().toString())) {

                    UserModel updatedUser = new UserModel();
                    DatabaseReference updateRef;
                    updateRef = FirebaseDatabase.getInstance().getReference().child("AppUser").child(key);

                    updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            updatedUser.setFullName(updateName.getText().toString());
                            updatedUser.setPhoneNumber(updatePhone.getText().toString());
                            updatedUser.setEmail(updateEmail.getText().toString());
                            updatedUser.setuPremium(primeState);

                            if(oldPasswordHolder.equals(oldPassword.getText().toString())) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
                                builder.setMessage("Are you sure want to update ?");
                                builder.setTitle("Alert !");
                                builder.setCancelable(false);

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        updatedUser.setPassword(oldPasswordHolder);
                                        updateRef.setValue(updatedUser);
                                        updateUserFavoritesCollection();
                                        updateUserPostedAdvertise();

                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(EMAIL_KEY, updatedUser.getEmail());
                                        editor.apply();

                                        Intent intent = new Intent(UpdateProfile.this, Home.class);
                                        startActivity(intent);
                                    }
                                });

                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Password is Incorrect.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }

                else {
                    UserModel updatedUser = new UserModel();
                    DatabaseReference updateRef;
                    updateRef = FirebaseDatabase.getInstance().getReference().child("AppUser").child(key);

                    updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            updatedUser.setFullName(updateName.getText().toString());
                            updatedUser.setPhoneNumber(updatePhone.getText().toString());
                            updatedUser.setEmail(updateEmail.getText().toString());
                            updatedUser.setuPremium(primeState);

                            if(oldPasswordHolder.equals(oldPassword.getText().toString())) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
                                builder.setMessage("Are you sure want to update ?");
                                builder.setTitle("Alert !");
                                builder.setCancelable(false);

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        updatedUser.setPassword(newPassword.getText().toString());
                                        updateRef.setValue(updatedUser);
                                        updateUserFavoritesCollection();
                                        updateUserPostedAdvertise();

                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(EMAIL_KEY, updatedUser.getEmail());
                                        editor.apply();

                                        Intent intent = new Intent(UpdateProfile.this, Home.class);
                                        startActivity(intent);
                                    }
                                });

                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            }
                            else
                                Toast.makeText(getApplicationContext(), "Password is Incorrect.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfile.this, Home.class);
                startActivity(intent);
            }
        });

    }

    public void updateUserFavoritesCollection() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Favorites");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Favorites favorites = snapshot.child(emailShared).getValue(Favorites.class);
                dbRef.child(emailShared).removeValue();

                dbRef.child(updateEmail.getText().toString()).setValue(favorites);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateUserPostedAdvertise() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Advertisements");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Advertisements advertisement;
                for(DataSnapshot mySnap : snapshot.getChildren()) {
                    advertisement = mySnap.getValue(Advertisements.class);

                    if(advertisement.getOwner().equalsIgnoreCase(emailShared))
                        dbRef.child(mySnap.getKey()).child("owner").setValue(updateEmail.getText().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}