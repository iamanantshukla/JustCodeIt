package com.example.yogacommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AppHomePage extends AppCompatActivity {

    private ImageView imageYoga, imageCommunity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home_page);
        imageCommunity= findViewById(R.id.imageCommunity);
        imageYoga= findViewById(R.id.imageYoga);

        imageCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppHomePage.this, Community_Home.class));

            }
        });

        imageYoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppHomePage.this, Beginner.class));
            }
        });
    }
}