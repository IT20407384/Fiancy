package com.daemon.fiancy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.daemon.fiancy.models.Advertisements;
import com.github.drjacky.imagepicker.ImagePicker;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;


public class PostAdActivity extends AppCompatActivity {

    // initialization
    Spinner EducationDropdown, ReligionDD;
    CheckBox cbReading, cbCollecting, cbMusic, cbGardening, cbGames, cbFishing,
            cbWalking, cbShopping, cbTraveling, cbWatchingSports, cbEatingOut, cbDancing;
    EditText fullname, age, description, profession, address, phone, email;
    ImageView postImage1, postImage2, postImage3;
    RadioGroup radioGroupGender, radioGroupStatus;
    RadioButton gender, statusRadioBtn;
    ArrayList<String> hobbieList;
    Uri filePath1, filePath2, filePath3;

    /////
    int x = 0;

    Advertisements advertisements;

    // all strings
    String FullName, Age, Gender, Status, Description, Profession, Address, Phone, Email,
            EduLevel, religion;

    String savereligion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);

        // get instance() view by id
        getImageInstance();
        getEditTextInstance();

        advertisements = new Advertisements();

        // functions
        setAdapterToEducationDropDown();
        setAdapterToReligionDropdown();
        // hobbies arraylist declaration
        hobbieList = new ArrayList<String>();

    }

    @Override
    protected void onResume() {
        super.onResume();

        setAdapterToEducationDropDown();
        setAdapterToReligionDropdown();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("lifecycle", "Fiancy is stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("lifecycle", "Fiancy is Destroyed");
    }

    // Clear user inputs
    private void clearUserInputs() {
        fullname.setText("");
        age.setText("");
        description.setText("");
        profession.setText("");
        address.setText("");
        phone.setText("");
        email.setText("");
    }

    private void getEditTextInstance() {
        // get instance() of editTexts
        fullname = findViewById(R.id.etFullName);
        age = findViewById(R.id.etAge);
        description = findViewById(R.id.etDescription);
        profession = findViewById(R.id.etProffesion);
        address = findViewById(R.id.etAddress);
        phone = findViewById(R.id.etPhone);
        email = findViewById(R.id.etMail);
        // get instance() of radiobuttons
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        //get dropdown instances
        EducationDropdown = findViewById(R.id.SPEducationLevel);
        ReligionDD = findViewById(R.id.SPReligion);
    }

    private void getImageInstance() {
        // get instance() for uploading images
        postImage1 = findViewById(R.id.insertImg1);
        postImage2 = findViewById(R.id.insertImg2);
        postImage3 = findViewById(R.id.insertImg3);
    }

    // get Gender
    private String getGender() {
        String maleOrFemale;
       int checkedRadioid = radioGroupGender.getCheckedRadioButtonId();
       if (checkedRadioid != -1) {
           gender = findViewById(checkedRadioid);
           maleOrFemale = gender.getText().toString();
       }
       else
           maleOrFemale = null;

       return maleOrFemale;
    }

    // get marriage status
    private String getStatus() {
        String status;
        int checkedRadioid = radioGroupStatus.getCheckedRadioButtonId();
        if (checkedRadioid != -1) {
            statusRadioBtn = findViewById(checkedRadioid);
            status = statusRadioBtn.getText().toString();
        } else
            status = null;

        return status;
    }

    // set adapter for education level dropdown
    private void setAdapterToEducationDropDown() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.CI_MinEducation, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        EducationDropdown.setAdapter(adapter);

        EducationDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EduLevel = EducationDropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setAdapterToReligionDropdown() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.CI_Religions, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ReligionDD.setAdapter(adapter);

        ReligionDD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                religion = ReligionDD.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //get checkboxes values
    public void getCheckBoxesValues() {
        //Getting instance of CheckBoxes and Button from the activity_post_ad.xml file
        cbReading = findViewById(R.id.cbReading);
        cbCollecting = findViewById(R.id.cbCollecting);
        cbMusic = findViewById(R.id.cbMusic);
        cbGardening = findViewById(R.id.cbGardening);
        cbGames = findViewById(R.id.cbGames);
        cbFishing = findViewById(R.id.cbFishing);
        cbWalking = findViewById(R.id.cbWalking);
        cbShopping = findViewById(R.id.cbShopping);
        cbTraveling = findViewById(R.id.cbTraveling);
        cbWatchingSports = findViewById(R.id.cbWatchingSports);
        cbEatingOut = findViewById(R.id.cbEatingOut);
        cbDancing = findViewById(R.id.cbDancing);

        StringBuilder result=new StringBuilder();
        if(cbReading.isChecked()) {
            hobbieList.add(cbReading.getText().toString());
            result.append("\nReading");
        }
        if(cbCollecting.isChecked()) {
            hobbieList.add(cbCollecting.getText().toString());
            result.append("\nCollecting");
        }
        if(cbMusic.isChecked()) {
            hobbieList.add(cbMusic.getText().toString());
            result.append("\nMusic");
        }
        if(cbGardening.isChecked()) {
            hobbieList.add(cbGardening.getText().toString());
            result.append("\nGardening");
        }
        if(cbGames.isChecked()) {
            hobbieList.add(cbGames.getText().toString());
            result.append("\nVideo Games");
        }
        if(cbFishing.isChecked()) {
            hobbieList.add(cbFishing.getText().toString());
            result.append("\nFishing");
        }
        if(cbWalking.isChecked()) {
            hobbieList.add(cbWalking.getText().toString());
            result.append("\nWalking");
        }
        if(cbShopping.isChecked()) {
            hobbieList.add(cbShopping.getText().toString());
            result.append("\nShopping");
        }
        if(cbTraveling.isChecked()) {
            hobbieList.add(cbTraveling.getText().toString());
            result.append("\nTraveling");
        }
        if(cbWatchingSports.isChecked()) {
            hobbieList.add(cbWatchingSports.getText().toString());
            result.append("\nWatching Sports");
        }
        if(cbEatingOut.isChecked()) {
            hobbieList.add(cbEatingOut.getText().toString());
            result.append("\nEating Out");
        }
        if(cbDancing.isChecked()) {
            hobbieList.add(cbDancing.getText().toString());
            result.append("\nDancing");
        }

//        Toast.makeText(getApplicationContext(), hobbieList.toString(), Toast.LENGTH_LONG).show();
    }

    // Image uploading
    public void uploadImage(View view) {

        switch (view.getId()) {
            case R.id.insertImg1:
                x = 1;
                selectImageForUpload();
                Toast.makeText(this, "IMG 01", Toast.LENGTH_SHORT).show();
                break;
            case R.id.insertImg2:
                x = 2;
                selectImageForUpload();
                Toast.makeText(this, "IMG 02", Toast.LENGTH_SHORT).show();
                break;
            case R.id.insertImg3:
                x = 3;
                selectImageForUpload();
                Toast.makeText(this, "IMG 03", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void selectImageForUpload() {
        Intent gallery = new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null) {
            if(x == 1) {
                filePath1 = data.getData();
                postImage1.setImageURI(filePath1);
            } else if (x == 2) {
                filePath2 = data.getData();
                postImage2.setImageURI(filePath2);
            } else if (x == 3) {
                filePath3 = data.getData();
                postImage3.setImageURI(filePath3);
            }
        }
    }

    // set all values to the Advertisement model
    private Boolean setAllValuesToModel() {
        String maleOrFemale =  getGender();
        String status = getStatus();

        FullName = fullname.getText().toString();
        Age = age.getText().toString();
        Gender = maleOrFemale;
        Status = status;
        Description = description.getText().toString();
        Profession = profession.getText().toString();
        Address = address.getText().toString();
        Phone = phone.getText().toString();
        Email = email.getText().toString();
        
        // validate
        if(maleOrFemale == null || status == null) {
            return false;
        } else if(TextUtils.isEmpty(FullName)) {
            return false;
        } else if(TextUtils.isEmpty(Age)) {
            return false;
        } else if(TextUtils.isEmpty(Profession)) {
            return false;
        } else if(TextUtils.isEmpty(Address)) {
            return false;
        } else if(TextUtils.isEmpty(Phone)) {
            return false;
        } else if(TextUtils.isEmpty(Email)) {
            return false;
        } else if(TextUtils.isEmpty(EduLevel)) {
            return false;
        } else if(TextUtils.isEmpty(hobbieList.toString())) {
            return false;
        } else if(TextUtils.isEmpty(religion)) {
            return false;
        } else {
            return true;
        }
    }


    // next button to load adconfiramation page
    public void adConfirmation(View view) {
        getCheckBoxesValues();
        String maleOrFemale =  getGender();
        String status = getStatus();
        Boolean validate = setAllValuesToModel();


        if(!validate) {
            Toast.makeText(this, "Please fill out the all fields", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, AdConfirmActivity.class);
            //send normal strings
            intent.putExtra("fullname", FullName);
            intent.putExtra("age", Age);
            intent.putExtra("profession", Profession);
            intent.putExtra("address", Address);
            intent.putExtra("description", Description);
            intent.putExtra("gender", maleOrFemale);
            intent.putExtra("status", status);
            intent.putExtra("phone", Phone);
            intent.putExtra("email", Email);
            intent.putExtra("minEduLevel", EduLevel);
            intent.putExtra("religion", religion);
            // pass array list
            intent.putExtra("hobbieList", hobbieList);

            //send uris
            if(filePath1 != null) {
                intent.putExtra("image1", filePath1.toString());
            }
            if(filePath2 != null) {
                intent.putExtra("image2", filePath2.toString());
            }
            if(filePath3 != null) {
                intent.putExtra("image3", filePath3.toString());
            }
            startActivity(intent);
        }
    }

    // application cancel button
    public void cancelBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
    }

    // to back
    public void clicktoBack(View view) {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
    }
}