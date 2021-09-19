package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daemon.fiancy.models.AdminLog;
import com.daemon.fiancy.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginBtn;
    TextView signUp;

    UserModel userModel;
    AdminLog adminLog;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String emailShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmail = findViewById(R.id.SMupdatename);
        loginPassword = findViewById(R.id.SMupdatephone);
        loginBtn = findViewById(R.id.SMloginbutton);
        signUp = findViewById(R.id.SMifwantSignup);

        adminLog = new AdminLog();
        userModel = new UserModel();

        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // in shared prefs inside get string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        emailShared = sharedpreferences.getString(EMAIL_KEY, null);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String email = loginEmail.getText().toString();
               String password = loginPassword.getText().toString();

               if(email.length()>=6) {
                   if (email.substring(0, 6).equalsIgnoreCase("fiancy")) {
                       DatabaseReference readRef1 = FirebaseDatabase.getInstance().getReference().child("AdminLog");

                       readRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               if(snapshot.hasChildren()) {
                                   int x = 0;

                                   for(DataSnapshot mySnap : snapshot.getChildren()) {
                                       adminLog = mySnap.getValue(AdminLog.class);

                                       assert adminLog != null;
                                       if(adminLog.getAdminEmail().equalsIgnoreCase(email)){
                                           if(adminLog.getAdminPassword().equals(password)) {
                                               Intent intent = new Intent(MainActivity.this, AdminPanel.class);
                                               startActivity(intent);
                                               x = 1;
                                           }
                                           else
                                               Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                       }
                                       if(x != 1)
                                           Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                   }
                               }
                               else
                                   Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });

                   }

                   else {
                       DatabaseReference readRef2 = FirebaseDatabase.getInstance().getReference().child("AppUser");

                       readRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               if(snapshot.hasChildren()) {
                                   int x = 0;
                                   for(DataSnapshot mySnap : snapshot.getChildren()) {
                                       userModel = mySnap.getValue(UserModel.class);

                                       assert userModel != null;
                                       if(userModel.getEmail().equalsIgnoreCase(email)) {
                                           if(userModel.getPassword().equals(password)) {

                                               SharedPreferences.Editor editor = sharedpreferences.edit();

                                               // below two lines will put values for
                                               // email and password in shared preferences.
                                               editor.putString(EMAIL_KEY, email);

                                               // to save our data with key and value.
                                               editor.apply();

                                               Intent intent = new Intent(MainActivity.this, Home.class);
                                               startActivity(intent);
                                               x = 1;
                                           }
                                           else
                                               Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                       }
                                   }
                                   if(x != 1)
                                       Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                               }
                               else
                                   Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });
                   }
               }

               else {
                   DatabaseReference readRef2 = FirebaseDatabase.getInstance().getReference().child("AppUser");

                   readRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if(snapshot.hasChildren()) {
                               int x = 0;
                               for(DataSnapshot mySnap : snapshot.getChildren()) {
                                   userModel = mySnap.getValue(UserModel.class);

                                   assert userModel != null;
                                   if(userModel.getEmail().equalsIgnoreCase(email)) {
                                       if(userModel.getPassword().equals(password)) {

                                           SharedPreferences.Editor editor = sharedpreferences.edit();

                                           // below two lines will put values for
                                           // email and password in shared preferences.
                                           editor.putString(EMAIL_KEY, email);

                                           // to save our data with key and value.
                                           editor.apply();

                                           Intent intent = new Intent(MainActivity.this, Home.class);
                                           startActivity(intent);
                                           x = 1;
                                       }
                                       else
                                           Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                   }
                               }
                               if(x != 1)
                                   Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                           }
                           else
                               Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });
               }

           }
       });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (emailShared != null) {
            Intent i = new Intent(MainActivity.this, Home.class);
            startActivity(i);
        }
    }
}