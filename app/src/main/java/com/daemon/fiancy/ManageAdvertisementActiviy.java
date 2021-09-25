package com.daemon.fiancy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daemon.fiancy.models.Advertisements;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class ManageAdvertisementActiviy extends AppCompatActivity {
    String TAG = "Fiancy manage advertisement    :   ";
    // initialization
    Advertisements singleEditableAd;
    Spinner EducationDropdown;
    CheckBox cbReading, cbCollecting, cbMusic, cbGardening, cbGames, cbFishing,
            cbWalking, cbShopping, cbTraveling, cbWatchingSports, cbEatingOut, cbDancing;
    EditText fullname, age, description, profession, address, phone, upReligion;
    ImageView Image1, Image2, Image3;
    RadioButton male, female;
    RadioButton r1, r2, r3, r4, r5;
    RadioGroup radioGroupStatus;
    RadioButton statusRadioBtn;
    Button imageUpdate;
    Button advertUpdateBtn;
    ProgressDialog progressDialog;

    ArrayList<String> hobbieList;
    Uri filePath1, filePath2, filePath3;

    String FullName, Age, Gender, Description, Profession, Address, Phone,
            EduLevel, religion;

    String documentKey;

    // download urls
    String downloadUrl1;
    String downloadUrl2;
    String downloadUrl3;

    /////
    int x = 0, d1 = 0, d2 = 0, d3 = 0;
    boolean clicked1 = false;
    boolean clicked2 = false;
    boolean clicked3 = false;

    Boolean clickedUploadImageBtn = false;
    Boolean getData = false;

    // firebase
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Advertisements");
    StorageReference imageFolder = FirebaseStorage.getInstance().getReference().child("Images");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_advertisement);

        Log.d(TAG, "Application is created");
        singleEditableAd = new Advertisements();

        // instances()
        getInstances();
    }

    private void getInstances() {
        // edittexts
        fullname = findViewById(R.id.etFullName);
        age = findViewById(R.id.etAge);
        description = findViewById(R.id.etDescription);
        profession = findViewById(R.id.etProffesion);
        address = findViewById(R.id.etAddress);
        phone = findViewById(R.id.etPhone);
        upReligion = findViewById(R.id.SPReligion);
        // spinner
        EducationDropdown = findViewById(R.id.SPEducationLevel);
        // image views
        Image1 = findViewById(R.id.updateImg1);
        Image2 = findViewById(R.id.updateImg2);
        Image3 = findViewById(R.id.updateImg3);
        // radios
        male = findViewById(R.id.radioMale);
        female = findViewById(R.id.radioFemale);
        r1 = findViewById(R.id.radioNevermarried);
        r2 = findViewById(R.id.radioWidowed);
        r3 = findViewById(R.id.radioDevorced);
        r4 = findViewById(R.id.radioSeparated);
        r5 = findViewById(R.id.radioOtherStatus);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        // buttons
        imageUpdate = findViewById(R.id.imageUpdate);
        advertUpdateBtn = findViewById(R.id.advertUpdateBtn);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on resume");


        // functions
        if (!getData) {
            getAdvertisementFilleddetails();
        }

        //disable upload image button when no photos to add
        if (filePath1 != null || filePath2 != null || filePath3 != null) {
            imageUpdate.setEnabled(true);
        }

        if (filePath1 != null || filePath2 != null || filePath3 != null) {
            advertUpdateBtn.setEnabled(false);
        }

    }

    private void getAdvertisementFilleddetails() {
        getData = true;
        // get document relevant document key
        documentKey = getIntent().getExtras().getString("DocumentKey");

        dbRef.child(documentKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("fullname")){
                    singleEditableAd = dataSnapshot.getValue(Advertisements.class);
                    setPreviousFilledDataTothisActivity();
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(ManageAdvertisementActiviy.this, Home.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1500);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {
            }
        });
    }

    //get checkboxes values
    public void getCheckBoxesValues() {
        if (cbReading.isChecked()) {
            hobbieList.add(cbReading.getText().toString());
        }
        if (cbCollecting.isChecked()) {
            hobbieList.add(cbCollecting.getText().toString());
        }
        if (cbMusic.isChecked()) {
            hobbieList.add(cbMusic.getText().toString());
        }
        if (cbGardening.isChecked()) {
            hobbieList.add(cbGardening.getText().toString());
        }
        if (cbGames.isChecked()) {
            hobbieList.add(cbGames.getText().toString());
        }
        if (cbFishing.isChecked()) {
            hobbieList.add(cbFishing.getText().toString());
        }
        if (cbWalking.isChecked()) {
            hobbieList.add(cbWalking.getText().toString());
        }
        if (cbShopping.isChecked()) {
            hobbieList.add(cbShopping.getText().toString());
        }
        if (cbTraveling.isChecked()) {
            hobbieList.add(cbTraveling.getText().toString());
        }
        if (cbWatchingSports.isChecked()) {
            hobbieList.add(cbWatchingSports.getText().toString());
        }
        if (cbEatingOut.isChecked()) {
            hobbieList.add(cbEatingOut.getText().toString());
        }
        if (cbDancing.isChecked()) {
            hobbieList.add(cbDancing.getText().toString());
        }

//        Toast.makeText(getApplicationContext(), hobbieList.toString(), Toast.LENGTH_LONG).show();
    }

    // get marriage status
    private String getStatus() {
        String status;
        int checkedRadioid = radioGroupStatus.getCheckedRadioButtonId();
        statusRadioBtn = findViewById(checkedRadioid);
        return statusRadioBtn.getText().toString();
    }

    // set details to editable form
    private void setPreviousFilledDataTothisActivity() {
        // set edittexts
        fullname.setText(singleEditableAd.getFullname());
        age.setText(singleEditableAd.getAge());
        description.setText(singleEditableAd.getDescription());
        profession.setText(singleEditableAd.getProfession());
        address.setText(singleEditableAd.getAddress());
        phone.setText(singleEditableAd.getPhone());
        upReligion.setText(singleEditableAd.getReligion());

        // set hobbie list
        if(!singleEditableAd.getHobbiesList().isEmpty()) {
            hobbieList = singleEditableAd.getHobbiesList();

            for (int i = 0; i < hobbieList.size(); i++) {
                if (cbReading.getText().equals(hobbieList.get(i))) {
                    cbReading.setChecked(true);
                } else if (cbCollecting.getText().equals(hobbieList.get(i))) {
                    cbCollecting.setChecked(true);
                } else if (cbDancing.getText().equals(hobbieList.get(i))) {
                    cbDancing.setChecked(true);
                } else if (cbEatingOut.getText().equals(hobbieList.get(i))) {
                    cbEatingOut.setChecked(true);
                } else if (cbFishing.getText().equals(hobbieList.get(i))) {
                    cbFishing.setChecked(true);
                } else if (cbGames.getText().equals(hobbieList.get(i))) {
                    cbGames.setChecked(true);
                } else if (cbGardening.getText().equals(hobbieList.get(i))) {
                    cbGardening.setChecked(true);
                } else if (cbMusic.getText().equals(hobbieList.get(i))) {
                    cbMusic.setChecked(true);
                } else if (cbShopping.getText().equals(hobbieList.get(i))) {
                    cbShopping.setChecked(true);
                } else if (cbTraveling.getText().equals(hobbieList.get(i))) {
                    cbTraveling.setChecked(true);
                } else if (cbWalking.getText().equals(hobbieList.get(i))) {
                    cbWalking.setChecked(true);
                } else if (cbWatchingSports.getText().equals(hobbieList.get(i))) {
                    cbWatchingSports.setChecked(true);
                }
            }
        }


        // set dropdown value
        if (singleEditableAd.getMinEducatuinLevel().equals("Up to GCE O/L")) {
            EducationDropdown.setSelection(0);
        } else if (singleEditableAd.getMinEducatuinLevel().equals("Up to GCE A/L")) {
            EducationDropdown.setSelection(1);
        } else if (singleEditableAd.getMinEducatuinLevel().equals("Diploma")) {
            EducationDropdown.setSelection(2);
        } else if (singleEditableAd.getMinEducatuinLevel().equals("Professional Qualification")) {
            EducationDropdown.setSelection(3);
        } else if (singleEditableAd.getMinEducatuinLevel().equals("Undergraduate")) {
            EducationDropdown.setSelection(4);
        } else if (singleEditableAd.getMinEducatuinLevel().equals("Bachelor's Degree or Equivalent")) {
            EducationDropdown.setSelection(5);
        } else if (singleEditableAd.getMinEducatuinLevel().equals("Post Graduate Diploma")) {
            EducationDropdown.setSelection(6);
        } else if (singleEditableAd.getMinEducatuinLevel().equals("Master's Degree or Equivalent")) {
            EducationDropdown.setSelection(7);
        } else if (singleEditableAd.getMinEducatuinLevel().equals("Phd or Post Doctoral")) {
            EducationDropdown.setSelection(8);
        }


        // set gender radio
        if (singleEditableAd.getGender().equals("Female")) {
            female.setChecked(true);
            Gender = "Female";
        } else {
            male.setChecked(true);
            Gender = "Male";
        }

        // set status
        if (singleEditableAd.getStatus().contentEquals(r1.getText())) {
            r1.setChecked(true);
        } else if (singleEditableAd.getStatus().contentEquals(r2.getText())) {
            r2.setChecked(true);
        } else if (singleEditableAd.getStatus().contentEquals(r3.getText())) {
            r3.setChecked(true);
        } else if (singleEditableAd.getStatus().contentEquals(r4.getText())) {
            r4.setChecked(true);
        } else if (singleEditableAd.getStatus().contentEquals(r5.getText())) {
            r5.setChecked(true);
        }

        // set image 1
        if (!clicked1) {
            if (singleEditableAd.getImage1() != null) {
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(singleEditableAd.getImage1())
                        .into(Image1);
            } else {
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load("https://cdn.iconscout.com/icon/free/png-256/no-image-1771002-1505134.png")
                        .into(Image1);
            }
        }
        // set image 2
        if (!clicked2) {
            if (singleEditableAd.getImage2() != null) {
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(singleEditableAd.getImage2())
                        .into(Image2);
            } else {
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load("https://cdn.iconscout.com/icon/free/png-256/no-image-1771002-1505134.png")
                        .into(Image2);
            }
        }
        // set image 3
        if (!clicked3) {
            if (singleEditableAd.getImage3() != null) {
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(singleEditableAd.getImage3())
                        .into(Image3);
            } else {
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load("https://cdn.iconscout.com/icon/free/png-256/no-image-1771002-1505134.png")
                        .into(Image3);
            }
        }
    }

    // Image uploading
    public void updateImage(View view) {

        switch (view.getId()) {
            case R.id.updateImg1:
                x = 1;
                clicked1 = true;
                selectImageForUpload();
                Toast.makeText(this, "IMG 01", Toast.LENGTH_SHORT).show();
                break;
            case R.id.updateImg2:
                x = 2;
                clicked2 = true;
                selectImageForUpload();
                Toast.makeText(this, "IMG 02", Toast.LENGTH_SHORT).show();
                break;
            case R.id.updateImg3:
                x = 3;
                clicked3 = true;
                selectImageForUpload();
                Toast.makeText(this, "IMG 03", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void selectImageForUpload() {
        ImagePicker.Companion.with(this)
                .crop()
                //.cropOval()
                .maxResultSize(512, 512, true)
                .createIntentFromDialog(new Function1() {
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        launcher.launch(it);
                    }
                });
    }

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    // Use the uri to load the image
                    if (x == 1) {
                        filePath1 = uri;
                        Image1.setImageURI(filePath1);
                    } else if (x == 2) {
                        filePath2 = uri;
                        Image2.setImageURI(filePath2);
                    } else if (x == 3) {
                        filePath3 = uri;
                        Image3.setImageURI(filePath3);
                    }
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    Toast.makeText(this, "Image selector error!", Toast.LENGTH_SHORT).show();
                }
            });


    // upload images to firebase
    private void uploadImages() {
        progressDialog = new ProgressDialog(ManageAdvertisementActiviy.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // check images is null
        if (filePath1 != null) {
            // generate name for images
            StorageReference imageName1 = imageFolder.child(System.currentTimeMillis() + "Image01.jpeg");
            imageName1.putFile(filePath1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl1 = uri.toString();
                            d1 = 1;
                            progressDialog.dismiss();
                            Toast.makeText(ManageAdvertisementActiviy.this, "Images successfully uploaded.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ManageAdvertisementActiviy.this, "Image 01 upload failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (filePath2 != null) {
            StorageReference imageName2 = imageFolder.child(System.currentTimeMillis() + 20 + "Image02.jpeg");
            imageName2.putFile(filePath2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl2 = uri.toString();
                            d2 = 2;
                            progressDialog.dismiss();
                            Toast.makeText(ManageAdvertisementActiviy.this, "Images successfully uploaded.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ManageAdvertisementActiviy.this, "Image 02 upload failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (filePath3 != null) {
            StorageReference imageName3 = imageFolder.child(System.currentTimeMillis() + 40 + "Image03.jpeg");
            imageName3.putFile(filePath3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl3 = uri.toString();
                            d3 = 3;
                            progressDialog.dismiss();
                            Toast.makeText(ManageAdvertisementActiviy.this, "Images successfully uploaded.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ManageAdvertisementActiviy.this, "Image 03 upload failed!", Toast.LENGTH_SHORT).show();
                }
            });
            ;
        }
    }


    // delete currunt images
    private void deleteCurrentImage1() {
        String url = singleEditableAd.getImage1();
        if (url != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);
            storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ManageAdvertisementActiviy.this, "Image successfully deleted", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(ManageAdvertisementActiviy.this, "Image already deleted!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void deleteCurrentImage2() {
        String url = singleEditableAd.getImage2();
        if (url != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);
            storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ManageAdvertisementActiviy.this, "Image successfully deleted", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(ManageAdvertisementActiviy.this, "Image already deleted!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void deleteCurrentImage3() {
        String url = singleEditableAd.getImage3();
        if (url != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);
            storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ManageAdvertisementActiviy.this, "Image successfully deleted", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(ManageAdvertisementActiviy.this, "Image already deleted!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // close image uploading
    // update image button
    public void uploadImage(View view) {
        uploadImages();
        clickedUploadImageBtn = true;
        advertUpdateBtn.setEnabled(true);
        imageUpdate.setEnabled(false);
    }

    // update the advertisement
    private Boolean updateAdvertisementDetails() {
        boolean isNull;

        // prevent null value
        if (profession.getText().toString().equals("")) {
            isNull = true;
        } else if (address.getText().toString().equals("")) {
            isNull = true;
        } else if (phone.getText().toString().equals("")) {
            isNull = true;
        } else if (upReligion.getText().toString().equals("")) {
            isNull = true;
        } else if (Gender == null) {
            isNull = true;
        } else if (EducationDropdown.getSelectedItem().toString().equals("")) {
            isNull = true;
        } else {
            singleEditableAd.setFullname(fullname.getText().toString());
            singleEditableAd.setAge(age.getText().toString());
            singleEditableAd.setDescription(description.getText().toString());
            singleEditableAd.setProfession(profession.getText().toString());
            singleEditableAd.setAddress(address.getText().toString());
            singleEditableAd.setPhone(phone.getText().toString());
            singleEditableAd.setReligion(upReligion.getText().toString());
            singleEditableAd.setGender(Gender);
            singleEditableAd.setMinEducatuinLevel(EducationDropdown.getSelectedItem().toString());
            isNull = false;
        }

        return isNull;
    }

    // update advertisements button
    public void updateAd(View view) {

        boolean isNull = updateAdvertisementDetails();
        Log.d("TAG1", String.valueOf(isNull));
        hobbieList.clear();
        getCheckBoxesValues();
        Log.d("Hobbies", "hobbielist 1 "+hobbieList.toString());
        if (!isNull && !hobbieList.isEmpty()) {
            if (clickedUploadImageBtn) {
                // delete current image
                if (d1 == 1) {
                    deleteCurrentImage1();
                }
                if (d2 == 2) {
                    deleteCurrentImage2();
                }
                if (d3 == 3) {
                    deleteCurrentImage3();
                }
                // new download url update
                if (downloadUrl1 != null) {
                    singleEditableAd.setImage1(downloadUrl1);
                }
                if (downloadUrl2 != null) {
                    singleEditableAd.setImage2(downloadUrl2);
                }
                if (downloadUrl3 != null) {
                    singleEditableAd.setImage3(downloadUrl3);
                }
            }
            hobbieList.clear();
            String status = getStatus();
            singleEditableAd.setStatus(status);
            getCheckBoxesValues();
            singleEditableAd.setHobbiesList(hobbieList);
            Log.d("Hobbies", "hobbielist 2 "+hobbieList.toString());

            progressDialog = new ProgressDialog(ManageAdvertisementActiviy.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            DatabaseReference updateRef = dbRef.child(documentKey);
            updateRef.setValue(singleEditableAd).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ManageAdvertisementActiviy.this, "Advertisement update successful", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 2500);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(ManageAdvertisementActiviy.this, "Advertisement update failed!", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
        }

    }

    // delete button
    public void deleteAd(View view) {
        Intent intent = new Intent(getApplicationContext(), deleteAdvertisementActivity.class);
        intent.putExtra("DocumentKey", documentKey);
        startActivity(intent);
        finish();
    }
}