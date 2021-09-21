package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daemon.fiancy.models.Advertisements;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdConfirmActivity extends AppCompatActivity {
    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;

    String emailShared;

    // initialization
    ImageView back, img01, img02, img03;
    TextView advertID, adFee, adDiscount, adTotFee;
    CheckBox TandC;
    ProgressDialog progressDialog;
    Button imageUpload;

    Boolean uploadBtnClicked = false;

    // firebase
    DatabaseReference myRef;
    StorageReference imageFolder = FirebaseStorage.getInstance().getReference().child("Images");

    // ad id
    String adID = "AD001";

    double getFee;
    double fee;
    double discount = 155.65;
    double totFee;

    // all strings
    String fullname, age, gender, status, description, profession, address, phone, minEduLevel, religion;

    ArrayList<String> hobbieList;

    // get from previous activity
    Advertisements advertisements;
    Uri filePath1;
    Uri filePath2;
    Uri filePath3;

    // download urls
    String downloadUrl1;
    String downloadUrl2;
    String downloadUrl3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_confirm);

        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        emailShared = sharedpreferences.getString(EMAIL_KEY, null);

        advertisements = new Advertisements();

        // back button in toolbar
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdConfirmActivity.this, PostAdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        // get instance()
        getInstances();
        // get form filled details in previous activity (activity_post_ad)
        // get form data form previous activity
        getData();
        // call functions for calculate total fee
        int words = countWords(description);
        double total = calculateAdFee(words);
        totFee = roundToTwodecimal(total);
        fee = roundToTwodecimal(getFee);

        // setting up data functions
        setData();

        //disable upload image button when no photos to add
        if (filePath1 != null || filePath2 != null || filePath3 != null) {
            imageUpload.setEnabled(true);
        }

    }

    private void getData() {
        Intent getintent = getIntent();
        fullname = getintent.getExtras().getString("fullname");
        age = getintent.getExtras().getString("age");
        profession = getintent.getExtras().getString("profession");
        address = getintent.getExtras().getString("address");
        description = getintent.getExtras().getString("description");
        gender = getintent.getExtras().getString("gender");
        status = getintent.getExtras().getString("status");
        phone = getintent.getExtras().getString("phone");
        minEduLevel = getintent.getExtras().getString("minEduLevel");
        religion = getintent.getExtras().getString("religion");
        // get arraylist using parcelable
        hobbieList = (ArrayList<String>) getIntent().getSerializableExtra("hobbieList");
        // get uris from previous intent
        String uri1 = getintent.getExtras().getString("image1");
        String uri2 = getintent.getExtras().getString("image2");
        String uri3 = getintent.getExtras().getString("image3");

        if (!TextUtils.isEmpty(uri1)) {
            filePath1 = Uri.parse(uri1);
            img01.setImageURI(filePath1);
        }
        if (!TextUtils.isEmpty(uri2)) {
            filePath2 = Uri.parse(uri2);
            img02.setImageURI(filePath2);
        }
        if (!TextUtils.isEmpty(uri3)) {
            filePath3 = Uri.parse(uri3);
            img03.setImageURI(filePath3);
        }
    }

    // get instances
    private void getInstances() {
        advertID = findViewById(R.id.advertID);
        adFee = findViewById(R.id.ADfee);
        adDiscount = findViewById(R.id.adDiscount);
        adTotFee = findViewById(R.id.adTotFee);
        TandC = findViewById(R.id.TandC);
        img01 = findViewById(R.id.img01);
        img02 = findViewById(R.id.img02);
        img03 = findViewById(R.id.img03);
        imageUpload = findViewById(R.id.imageUpload);
    }

    // set data for textViews
    private void setData() {
        advertID.setText(String.format("AD ID :- %s", adID));
        adFee.setText(String.format(": Rs. %s", fee));
        adDiscount.setText(String.format(": Rs. %s", discount));
        adTotFee.setText(String.format(": Rs. %s", totFee));
    }

    // get description word count for calculate the advertisement fee
    private int countWords(String description) {
        String words = description.trim();
        if (words.isEmpty())
            return 0;
        return words.split("\\s+").length; // separate string around spaces
    }

    // calculate total fee
    private double calculateAdFee(int wordCount) {
        int fixedFee = 2500;
        double feePerWord = 15.50;
        double countFeeForWord = feePerWord * wordCount;
        getFee = fixedFee + countFeeForWord;
        return getFee - discount;
    }

    // get image file extension
    private String getFileExt(Uri mUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(mUri));
    }

    // upload images to firebase
    private void uploadImages() {


        progressDialog = new ProgressDialog(AdConfirmActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // check images is null
        if (filePath1 != null) {
            // generate name for images
            StorageReference imageName1 = imageFolder.child(System.currentTimeMillis() + "Image01." + getFileExt(filePath1));
            imageName1.putFile(filePath1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl1 = uri.toString();
                            progressDialog.dismiss();
                            Toast.makeText(AdConfirmActivity.this, "Images successfully uploaded.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AdConfirmActivity.this, "Image 01 upload failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (filePath2 != null) {
            StorageReference imageName2 = imageFolder.child(System.currentTimeMillis() + 20 + "Image02." + getFileExt(filePath2));
            imageName2.putFile(filePath2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl2 = uri.toString();
                            progressDialog.dismiss();
                            Toast.makeText(AdConfirmActivity.this, "Images successfully uploaded.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AdConfirmActivity.this, "Image 02 upload failed!", Toast.LENGTH_SHORT).show();
                }
            });
            ;
        }

        if (filePath3 != null) {
            StorageReference imageName3 = imageFolder.child(System.currentTimeMillis() + 40 + "Image03." + getFileExt(filePath3));
            imageName3.putFile(filePath3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl3 = uri.toString();
                            progressDialog.dismiss();
                            Toast.makeText(AdConfirmActivity.this, "Images successfully uploaded.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AdConfirmActivity.this, "Image 03 upload failed!", Toast.LENGTH_SHORT).show();
                }
            });
            ;
        }
    }

    // upload data to firebase
    private void uploadData() {
        progressDialog = new ProgressDialog(AdConfirmActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // upload to the firebase
        myRef = FirebaseDatabase.getInstance().getReference().child("Advertisements");

        advertisements.setFullname(fullname);
        advertisements.setAge(age);
        advertisements.setGender(gender);
        advertisements.setStatus(status);
        advertisements.setDescription(description);
        advertisements.setProfession(profession);
        advertisements.setAddress(address);
        advertisements.setPhone(phone);
        advertisements.setOwner(emailShared);
        advertisements.setMinEducatuinLevel(minEduLevel);
        advertisements.setReligion(religion);
        advertisements.setHobbiesList(hobbieList);
        advertisements.setImage1(downloadUrl1);
        advertisements.setImage2(downloadUrl2);
        advertisements.setImage3(downloadUrl3);
        advertisements.setFee(String.valueOf(fee));
        advertisements.setDiscount(String.valueOf(discount));
        advertisements.setTotatlFee(String.valueOf(totFee));

        // when uploaded success
        myRef.push().setValue(advertisements).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                progressDialog.dismiss();

                View adconfirmlayout = findViewById(R.id.adconfirmlayout);
                Snackbar snackbar = Snackbar
                        .make(adconfirmlayout, "Your advertisement posting as pending", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    // convert to two decimal
    private double roundToTwodecimal(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

    // upload images
    public void btnUpload(View view) {
        uploadImages();
        uploadBtnClicked = true;
        imageUpload.setEnabled(false);
    }

    public void adConfirmation(View view) {
        if(filePath1 != null || filePath2 != null || filePath3 != null) {
            if (uploadBtnClicked) {
                if (TandC.isChecked()) {
                    uploadData();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            uploadBtnClicked = false;
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 4000);
                } else {
                    Toast.makeText(this, "Agree to the T & C", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please upload your photos first!", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (TandC.isChecked()) {
                uploadData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uploadBtnClicked = false;
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                        finish();
                    }
                }, 4000);
            } else {
                Toast.makeText(this, "Agree to the T & C", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btnCancel(View view) {
        if (uploadBtnClicked) {
            Toast.makeText(this, "You can't cancel after upload the photos", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }
    }

    // back with back button

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (uploadBtnClicked) {
            Toast.makeText(this, "You can't cancel after upload the photos", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }
    }
}