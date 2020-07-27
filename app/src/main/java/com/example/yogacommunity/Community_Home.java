package com.example.yogacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    private AccountFragment accountFragment;
    private NotificationFragment notificationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community__home);
        bottomNavigationView=findViewById(R.id.BottomNavigationView);
        postPhoto= findViewById(R.id.postPhoto);
        homeFragment= new HomeFragment();
        accountFragment= new AccountFragment();
        replaceFragment(homeFragment);
        notificationFragment= new NotificationFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.navigation_Home:
                        replaceFragment(homeFragment);
                        return true;
                    case R.id.navigation_Account:
                        replaceFragment(accountFragment);
                        return true;
                    case R.id.navigation_Notification:
                        replaceFragment(notificationFragment);
                        return true;
                }

                return false;
            }
        });



        postPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Community_Home.this, AddingPost.class));
            }
        });



    }

    private void replaceFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.MenuContainer, fragment);
        fragmentTransaction.commit();

    }


}
