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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class AddingPost extends AppCompatActivity {

    private ImageView postImage;
    private Button postButton;
    private TextView postDiscription;
    private Uri postimageuri=null;
    private StorageReference firebaseStorage;
    private Task<Void> reff;
    private FirebaseAuth auth;
    private String user_id;
    private HashMap<String, String> map= new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_post);
        firebaseStorage=FirebaseStorage.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        user_id= auth.getCurrentUser().getUid();

        postImage= findViewById(R.id.imagePost);
        postButton=findViewById(R.id.postFinal);
        postDiscription=findViewById(R.id.editDiscription);

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
                String Discription= postDiscription.getText().toString();
                if(postimageuri!=null){

                    if(Discription.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Discription Box is Empty", Toast.LENGTH_LONG).show();
                    }
                    else{

                        String random= UUID.randomUUID().toString();
                        final StorageReference filePath=firebaseStorage.child("image_Post").child(user_id).child(random+ ".jpg");
                        filePath.putFile(postimageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if(task.isSuccessful()){
                                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Uri downloadUrl = uri;
                                            String Download_Uri = downloadUrl.toString();

                                            reff=FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).
                                                    child("Post").setValue(Download_Uri);

                                        }
                                        });
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