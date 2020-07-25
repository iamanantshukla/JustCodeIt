package com.example.yogacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class Community_Home extends AppCompatActivity {

    private FloatingActionButton postPhoto;
    private DatabaseReference reff;
    private FirebaseAuth auth;
    private String user_id;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community__home);
        btn = findViewById(R.id.BeginnerBtn);


        postPhoto= findViewById(R.id.postPhoto);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Community_Home.this, Beginner.class));
            }
        });
        postPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Community_Home.this, AddingPost.class));
            }
        });



    }

    /*@Override
    protected void onStart() {
        super.onStart();
        auth=FirebaseAuth.getInstance();
        user_id=auth.getCurrentUser().getUid();
        reff= FirebaseDatabase.getInstance().getReference();
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){
                    Log.i("Recheck User", "onDataChange: UserAccountUpToDate");
                }
                else{
                    startActivity(new Intent(Community_Home.this, StartupSetup.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/
}
