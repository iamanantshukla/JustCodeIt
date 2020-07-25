package com.example.yogacommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartupSetup extends AppCompatActivity {

    CircleImageView profilePicture;
    private Uri mainImageURI=null;
    private String user_id;
    private StorageReference firebaseStorage;
    private Task<Uri> download_uri;

    private Task<Void> reff;
    private DatabaseReference databaseReference;
    private Button Done;
    private TextView username;
    private FirebaseAuth firebaseAuth;
    private String link;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statup_setup);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage= FirebaseStorage.getInstance().getReference();

        user_id=firebaseAuth.getCurrentUser().getUid();
        profilePicture= findViewById(R.id.imageProfile);
        username= findViewById(R.id.editUsername);
        progressBar=findViewById(R.id.progressBar);

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("username").exists()){

                    username.setText(dataSnapshot.child("username").getValue().toString());



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Done= findViewById(R.id.Setup);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(StartupSetup.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(StartupSetup.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);


                }
                else{

                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1,1)
                            .start(StartupSetup.this);
                    Log.i("else", "onClick: Done");

                }
            }
        });



        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final String usernametxt= username.getText().toString();

                if(!TextUtils.isEmpty(usernametxt) && mainImageURI!=null){

                    user_id= firebaseAuth.getCurrentUser().getUid();

                    final StorageReference imagePath= firebaseStorage.child("ProfilePictures").child(user_id+".jpg");
                    imagePath.putFile(mainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){



                                HashMap<String, String> map= new HashMap<>();
                                map.put("username", usernametxt);

                                reff= FirebaseDatabase.getInstance().getReference().child("Users").child(user_id)
                                        .setValue(map);
                                startActivity(new Intent(StartupSetup.this, Community_Home.class));




                            }
                            else{

                                Toast.makeText(getApplicationContext(), "uploadFailed", Toast.LENGTH_LONG).show();

                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            }
        });



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainImageURI = result.getUri();
                profilePicture.setImageURI(mainImageURI);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
