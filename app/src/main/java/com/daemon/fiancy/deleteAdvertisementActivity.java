package com.daemon.fiancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daemon.fiancy.models.ReasonForDeleteAD;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class deleteAdvertisementActivity extends AppCompatActivity {
    // initialization
    ImageView back;

    CheckBox res1, res2, res3;
    EditText otherRes, resConf;

    String documentKey;
    String delres1, delres2, delres3, delotherres;
    ArrayList<String> resList;

    ReasonForDeleteAD reasonForDeleteAD;

    String downloadUrl1, downloadUrl2, downloadUrl3;

    //firebase
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_advertisement);
        // document key
        documentKey = getIntent().getExtras().getString("DocumentKey");
        resList = new ArrayList<>();
        reasonForDeleteAD = new ReasonForDeleteAD();

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(deleteAdvertisementActivity.this, ManageAdvertisementActiviy.class);
                intent.putExtra("DocumentKey", documentKey);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        //get instances
        getInstances();
        getDownloadUrl();
    }

    private void getDownloadUrl() {
        dbRef.child("Advertisements").child(documentKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("image1")){
                    downloadUrl1 = dataSnapshot.child("image1").getValue().toString();
                }
                if(dataSnapshot.hasChild("image2")){
                    downloadUrl2 = dataSnapshot.child("image2").getValue().toString();
                }
                if(dataSnapshot.hasChild("image3")){
                    downloadUrl3 = dataSnapshot.child("image3").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

            }
        });
    }

    // find by id ::
    private void getInstances() {
        res1 = findViewById(R.id.res1);
        res2 = findViewById(R.id.res2);
        res3 = findViewById(R.id.res3);
        otherRes = findViewById(R.id.otherRes);
        resConf = findViewById(R.id.resConf);
    }

    // getting values from xml file
    private void getValuesfromacXMLfields() {
        resList.clear();
        if(res1.isChecked()) {
            resList.add(res1.getText().toString());
        }
        if(res2.isChecked()) {
            resList.add(res2.getText().toString());
        }
        if(res3.isChecked()) {
            resList.add(res3.getText().toString());
        }
        if(!TextUtils.isEmpty(otherRes.getText())) {
            resList.add(otherRes.getText().toString());
        }
    }

    // set all reasons to model class
    private void setReasons () {
        reasonForDeleteAD.setDocumentKey(documentKey);
        reasonForDeleteAD.setReasons(resList);
    }

    // delete photos first
    private void deletePhotos() {
        if (downloadUrl1 != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(downloadUrl1);
            storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                }
            });
        }
        if (downloadUrl2 != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(downloadUrl2);
            storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                }
            });
        }
        if (downloadUrl3 != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(downloadUrl3);
            storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                }
            });;
        }
    }

    // deleteAdvertisement
    private void deleteAdvertisement() {
        dbRef.child("Advertisements").child(documentKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dbRef.child("Reason_for_delete_the_advertisements").push().setValue(reasonForDeleteAD);
            }
        });
    }


    // buttons
    // permanatly delete
    public void permanantDelete(View view) {
        getValuesfromacXMLfields();
        if(!resList.isEmpty()) {
            if(resConf.getText().toString().toLowerCase().equals("yes")){
                // snackbar
                LinearLayout deleteAdvertisement = findViewById(R.id.deleteAdvertisement);
                Snackbar snackbar = Snackbar.make(deleteAdvertisement, "Your advertisement is successfully deleted..", Snackbar.LENGTH_LONG);
                snackbar.show();
                // deletation
                deletePhotos();
                setReasons();
                deleteAdvertisement();
            } else {
                Toast.makeText(this, "Please confirm! Do you want do delete this advertisement!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please provide a reason for delete this advertisement.", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void cancelDelete(View view) {
        Intent intent = new Intent(deleteAdvertisementActivity.this, ManageAdvertisementActiviy.class);
        intent.putExtra("DocumentKey", documentKey);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(deleteAdvertisementActivity.this, ManageAdvertisementActiviy.class);
        intent.putExtra("DocumentKey", documentKey);
        startActivity(intent);
    }
}