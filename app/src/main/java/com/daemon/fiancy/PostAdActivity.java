package com.daemon.fiancy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.drjacky.imagepicker.ImagePicker;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;


public class PostAdActivity extends AppCompatActivity {

    // initialization
    Spinner EducationDropdown;
    CheckBox cbReading, cbCollecting, cbMusic, cbGardening, cbGames, cbFishing,
            cbWalking, cbShopping, cbTraveling, cbWatchingSports, cbEatingOut, cbDancing;
    EditText fullname, age, description, profession, address, phone, email;
    ImageView postImage1, postImage2, postImage3;

    /////
    int x = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        // get instance() view by id
        EducationDropdown = findViewById(R.id.SPEducationLevel);
        getImageInstance();
        getEditTextInstance();
        // functions
        setAdapterToEducationDropDown();


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
    }

    private void getImageInstance() {
        // get instance() for uploading images
        postImage1 = findViewById(R.id.insertImg1);
        postImage2 = findViewById(R.id.insertImg2);
        postImage3 = findViewById(R.id.insertImg3);
    }

    // get Gender
    public void getGender(View view) {
        // Is the button now checked
        boolean isChecked = ((RadioButton) view).isChecked();

        // Check with radio button was checked
        switch (view.getId()) {
            case R.id.radioMale:
                if(isChecked)
                    Toast.makeText(this, "Male", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioFemale:
                if(isChecked)
                    Toast.makeText(this, "Female", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // get marriage status
    public void getStatus(View view) {
        // Is the button now checked
        boolean isChecked = ((RadioButton) view).isChecked();

        // Check with radio button was checked
        switch (view.getId()) {
            case R.id.radioNevermarried:
                if(isChecked)
                    Toast.makeText(this, "Never Married", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioWidowed:
                if(isChecked)
                    Toast.makeText(this, "Widowed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioDevorced:
                if(isChecked)
                    Toast.makeText(this, "Devorced", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioSeparated:
                if(isChecked)
                    Toast.makeText(this, "Separated", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioOtherStatus:
                if(isChecked)
                    Toast.makeText(this, "Other", Toast.LENGTH_SHORT).show();
                break;
        }
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
                String Elevel = EducationDropdown.getSelectedItem().toString();
                Toast.makeText(PostAdActivity.this, "You selected : "+Elevel, Toast.LENGTH_SHORT).show();
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
        result.append("Selected Items:");
        if(cbReading.isChecked()) {
            result.append("\nReading");
        }
        if(cbCollecting.isChecked()) {
            result.append("\nCollecting");
        }
        if(cbMusic.isChecked()) {
            result.append("\nMusic");
        }
        if(cbGardening.isChecked()) {
            result.append("\nGardening");
        }
        if(cbGames.isChecked()) {
            result.append("\nVideo Games");
        }
        if(cbFishing.isChecked()) {
            result.append("\nFishing");
        }
        if(cbWalking.isChecked()) {
            result.append("\nWalking");
        }
        if(cbShopping.isChecked()) {
            result.append("\nShopping");
        }
        if(cbTraveling.isChecked()) {
            result.append("\nTraveling");
        }
        if(cbWatchingSports.isChecked()) {
            result.append("\nWatching Sports");
        }
        if(cbEatingOut.isChecked()) {
            result.append("\nEating Out");
        }
        if(cbDancing.isChecked()) {
            result.append("\nDancing");
        }

        Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
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

        ImagePicker.Companion.with(this)
                .crop()
//                .cropOval()
                .maxResultSize(1024, 1024, true)
                .createIntentFromDialog((Function1) (new Function1() {
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        launcher.launch(it);
                    }
                }));


    }

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    // Use the uri to load the image
                    if (x == 1)
                        postImage1.setImageURI(uri);
                    else if (x == 2)
                        postImage2.setImageURI(uri);
                    else if (x == 3)
                        postImage3.setImageURI(uri);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });


    // next button to load adconfiramation page
    public void adConfirmation(View view) {
        getCheckBoxesValues();

        Intent intent = new Intent(this, AdConfirmActivity.class);
        startActivity(intent);
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