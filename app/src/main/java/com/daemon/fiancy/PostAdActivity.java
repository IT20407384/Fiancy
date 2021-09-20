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
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import java.util.ArrayList;

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
    RadioGroup radioGroupGender, radioGroupStatus;
    RadioButton gender, statusRadioBtn;
    ArrayList<String> hobbieList;
    Uri filePath1, filePath2, filePath3;

    /////
    int x = 0;

    Advertisements advertisements;

    String EduLevel;



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
        // hobbies arraylist declaration
        hobbieList = new ArrayList<String>();
        advertisements = new Advertisements();
    }

//    // Clear user inputs
//    private void clearUserInputs() {
//        fullname.setText("");
//        age.setText("");
//        description.setText("");
//        profession.setText("");
//        address.setText("");
//        phone.setText("");
//        email.setText("");
//    }

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
                String Elevel = EducationDropdown.getSelectedItem().toString();
                EduLevel = Elevel;
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
                    // Use the uri to load the image

                    if (x == 1){
                        filePath1 = result.getData().getData();
                        postImage1.setImageURI(filePath1);
                    }
                    else if (x == 2) {
                        filePath2 = result.getData().getData();
                        postImage2.setImageURI(filePath2);
                    }
                    else if (x == 3) {
                        filePath3 = result.getData().getData();
                        postImage3.setImageURI(filePath3);
                    }

                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });

    // set all values to the Advertisement model
    private Boolean setAllValuesToModel() {
        String maleOrFemale =  getGender();
        String status = getStatus();

        // validate
        if(maleOrFemale == null || status == null) {
            return false;
        } else if(TextUtils.isEmpty(fullname.getText().toString())) {
            return false;
        } else if(TextUtils.isEmpty(age.getText().toString())) {
            return false;
        } else if(TextUtils.isEmpty(profession.getText().toString())) {
            return false;
        } else if(TextUtils.isEmpty(address.getText().toString())) {
            return false;
        } else if(TextUtils.isEmpty(phone.getText().toString())) {
            return false;
        } else if(TextUtils.isEmpty(email.getText().toString())) {
            return false;
        } else if(TextUtils.isEmpty(EduLevel)) {
            return false;
        } else if(TextUtils.isEmpty(hobbieList.toString())) {
            return false;
        } else {
            advertisements.setFullname(fullname.getText().toString());
            advertisements.setAge(age.getText().toString());
            advertisements.setGender(maleOrFemale);
            advertisements.setStatus(status);
            advertisements.setDescription(description.getText().toString());
            advertisements.setProfession(profession.getText().toString());
            advertisements.setAddress(address.getText().toString());
            advertisements.setPhone(phone.getText().toString());
            advertisements.setEmail(email.getText().toString());
            advertisements.setMinEducatuinLevel(EduLevel);
            advertisements.setHobbiesList(hobbieList);
            return true;
        }
    }


    // next button to load adconfiramation page
    public void adConfirmation(View view) {
        getCheckBoxesValues();
        Boolean validate = setAllValuesToModel();


        if(!validate) {
            Toast.makeText(this, "Please fill out the all fields", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, AdConfirmActivity.class);
            intent.putExtra("advertisements", advertisements);
            intent.putExtra("image1", filePath1);
            intent.putExtra("image2", filePath2);
            intent.putExtra("image3", filePath3);
            startActivity(intent);
        }
    }

    // application cancel button
    public void cancelBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.putExtra("advertisements", advertisements);
        startActivity(intent);
    }

    // to back
    public void clicktoBack(View view) {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
    }
}