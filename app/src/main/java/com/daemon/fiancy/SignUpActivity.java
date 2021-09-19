package com.daemon.fiancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.daemon.fiancy.models.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText fullName, email, password, phone;
    CheckBox agreeCheck;
    Button signIn;

    DatabaseReference dbRef;
    UserModel userModel;

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

        userModel = new UserModel();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("AppUser");

                if((TextUtils.isEmpty(fullName.getText().toString())) || (TextUtils.isEmpty(phone.getText().toString())) || (TextUtils.isEmpty(email.getText().toString())) || (TextUtils.isEmpty(password.getText().toString())) )
                    Toast.makeText(getApplicationContext(), "Please fillOut all fields", Toast.LENGTH_SHORT).show();

                else {
                    userModel.setFullName(fullName.getText().toString().trim());
                    userModel.setPhoneNumber(phone.getText().toString().trim());
                    userModel.setEmail(email.getText().toString().trim());
                    userModel.setPassword(password.getText().toString().trim());

                    dbRef.push().setValue(userModel);
                    Toast.makeText(getApplicationContext(), "Welcome to FIANCY", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this, Home.class);
                    startActivity(intent);
                }
            }
        });

    }
    public void agreeTerms(View view) {
        signIn.setEnabled(((CheckBox) view).isChecked());
    }
}