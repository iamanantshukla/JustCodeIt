package com.example.yogacommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Script;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import id.zelory.compressor.Compressor;
import id.zelory.compressor.constraint.Compression;

public class AddingPost extends AppCompatActivity {

    private ImageView postImage;
    private Button postButton;
    private TextView postDiscription;
    private Uri postimageuri=null;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private String user_id;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_post);

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
        storageReference=FirebaseStorage.getInstance().getReference();
        auth=FirebaseAuth.getInstance();

        user_id= auth.getCurrentUser().getUid();


        final StorageReference mStorageReference= storageReference.child("image_Post").child(user_id);


        postImage= findViewById(R.id.imagePost);
        postButton=findViewById(R.id.postFinal);
        postDiscription=findViewById(R.id.editDiscription);
        progressBar=findViewById(R.id.progressBar2);

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .setMinCropResultSize(512,512)
                        .start(AddingPost.this);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final String Discription= postDiscription.getText().toString();
                if(postimageuri!=null){

                    if(Discription.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Discription Box is Empty", Toast.LENGTH_LONG).show();
                    }
                    else{

                        final String random= UUID.randomUUID().toString();
                        final StorageReference filePath=storageReference.child("image_Post").child(user_id).child(random+ ".jpg");
                        filePath.putFile(postimageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if(task.isSuccessful()){

                                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            String Download_Uri = uri.toString();

                                            String currentTime = Calendar.getInstance().getTime().toString();
                                            HashMap<String, String>map= new HashMap<>();
                                            map.put("DownloadLink", Download_Uri);
                                            map.put("Discription", Discription);
                                            map.put("userID", user_id);
                                            map.put("TimeStamp", currentTime);
                                            databaseReference.child("Post").child(random).setValue(map);

                                        }
                                    });
                                    startActivity(new Intent(AddingPost.this, Community_Home.class));
                                    /*filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Uri downloadUrl = uri;
                                            String Download_Uri = downloadUrl.toString();

                                            reff=FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).
                                                    child("Post").setValue(Download_Uri);

                                        }
                                        });*/

                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Couldn't update in Database", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "No Image", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                postimageuri=result.getUri();
                postImage.setImageURI(postimageuri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}