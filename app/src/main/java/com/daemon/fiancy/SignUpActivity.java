package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.daemon.fiancy.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    EditText fullName, email, password, phone;
    CheckBox agreeCheck;
    Button signIn;

    DatabaseReference dbRef;
    UserModel userModel;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    SharedPreferences sharedpreferences;
    String emailShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = findViewById(R.id.SMupdatename);
        phone = findViewById(R.id.SMupdatephone);
        email = findViewById(R.id.SMupdateemail);
        password = findViewById(R.id.SMsigninpassword);

        agreeCheck = findViewById(R.id.SMagreeCheckSign);
        signIn = findViewById(R.id.SMupdateprofilebtn);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        emailShared = sharedpreferences.getString(EMAIL_KEY, null);

        userModel = new UserModel();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((TextUtils.isEmpty(fullName.getText().toString())) || (TextUtils.isEmpty(email.getText().toString())))
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();

                else if(phone.getText().toString().length() != 10)
                    Toast.makeText(getApplicationContext(), "Invalid mobile number", Toast.LENGTH_SHORT).show();

                else if(password.getText().toString().length() < 8)
                    Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();

                else {
                    dbRef = FirebaseDatabase.getInstance().getReference().child("AppUser");

                    dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String userValid ;
                            boolean found = false;

                            for(DataSnapshot mySnap : snapshot.getChildren()){
                                userValid = mySnap.child("email").getValue().toString();
                                if(userValid.equals(email.getText().toString()))
                                    found = true;
                            }
                            if(found)
                                Toast.makeText(getApplicationContext(), "Email address already exists", Toast.LENGTH_SHORT).show();

                            else {
                                userModel.setFullName(fullName.getText().toString().trim());
                                userModel.setPhoneNumber(phone.getText().toString().trim());
                                userModel.setEmail(email.getText().toString().trim());
                                userModel.setPassword(password.getText().toString().trim());

                                dbRef.push().setValue(userModel);
                                Toast.makeText(getApplicationContext(), "Welcome to FIANCY", Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(EMAIL_KEY, email.getText().toString());
                                editor.apply();

                                Intent intent = new Intent(SignUpActivity.this, Home.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void agreeTerms(View view) {
        signIn.setEnabled(((CheckBox) view).isChecked());
    }
}