package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private EditText UserName, FullName, CountryName;
    private Button SaveInformationbutton;
    private CircleImageView ProfileImage;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private ProgressDialog loadingBar;
    String currentUserId;
    private StorageReference UserProfileImageRef;
    final static int Gallery_Pick =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);



        UserName = (EditText) findViewById(R.id.setup_username);
        FullName = (EditText) findViewById(R.id.setup_fullname);
        CountryName = (EditText) findViewById(R.id.setup_country_name);
        SaveInformationbutton = (Button)findViewById(R.id.setup_information_button);
        ProfileImage = (CircleImageView) findViewById(R.id.setup_profile_image);

        loadingBar= new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child(currentUserId);

        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("profile images");


        SaveInformationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                saveAccountSetupinformation();
            }


        });


        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Pick);

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gallery_Pick && resultCode == RESULT_OK && data!= null)
        {
            Uri ImageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode==RESULT_OK)
            {
                loadingBar.setTitle("Profile Image");
                loadingBar.setMessage("Please Wait, while we are updating Your Profile Image...");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                Uri resultUri = result.getUri();

                StorageReference filePath = UserProfileImageRef.child(currentUserId+ ".jpg");

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SetupActivity.this, "Profile image stored succesfully to firebase storage...", Toast.LENGTH_SHORT).show();
                            final String downloadUrl = task.getResult().getMetadata().getReference().getDownloadUrl().toString();
                            UsersRef.child("profileimage").setValue(downloadUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                       
                                            if (task.isSuccessful())
                                            {
                                                Intent selfIntent = new Intent(SetupActivity.this,SetupActivity.class);
                                                startActivity(selfIntent);
                                                Toast.makeText(SetupActivity.this, "Profile Image is stored to firebase database successfully...", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                            else 
                                            {
                                                String message = task.getException().getMessage();
                                                Toast.makeText(SetupActivity.this, "Error Occured: "+message, Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                            
                                        }
                                    });
                        }
                    }
                });

            }
            else
            {

                Toast.makeText(this, "Error occured image canot crop", Toast.LENGTH_SHORT).show();

                loadingBar.dismiss();
            }

        }
    }

    private void saveAccountSetupinformation() {

        String username = UserName.getText().toString();
        String fullname = FullName.getText().toString();
        String country = CountryName.getText().toString();

        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(SetupActivity.this, "Please write your username...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(fullname))
        {
            Toast.makeText(SetupActivity.this, "Please write your full name...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(country))
        {
            Toast.makeText(SetupActivity.this, "Please write your country...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Saving Information");
            loadingBar.setMessage("Please Wait, while we are your new account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            HashMap userMap = new HashMap();
            userMap.put("username",username);
            userMap.put("fullname",fullname);
            userMap.put("country",country);
            userMap.put("status","Hey there, i am using poster social newtwork, developed by Awais Raza.");
            userMap.put("gender","none");
            userMap.put("dob","none");
            userMap.put("relationshipstatus","none");

            UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if ( task.isSuccessful())
                    {
                        SendUserToMainActivity();
                        Toast.makeText(SetupActivity.this, "your account is created succesfully", Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();

                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(SetupActivity.this, "Error occured: "+message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }

    }

    private void SendUserToMainActivity() {

        Intent mainIntent = new Intent(SetupActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();

    }
}
