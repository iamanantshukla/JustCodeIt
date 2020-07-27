package com.example.yogacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText username, password, email;
    Button Login, SignUp;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        mAuth= FirebaseAuth.getInstance();

        password=(EditText) findViewById(R.id.editPass);
        email= (EditText) findViewById(R.id.editEmail);
        Login= (Button) findViewById(R.id.buttonLogin);
        SignUp=(Button) findViewById(R.id.buttonSignup);



        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String passwordtxt=password.getText().toString();
                String emailtxt=email.getText().toString();
                register(emailtxt, passwordtxt);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String passwordtxt=password.getText().toString();
                String emailtxt=email.getText().toString();
                LoginTask(emailtxt, passwordtxt);
            }
        });




    }

    private void register(String usernametxt, String emailtxt){

        mAuth.createUserWithEmailAndPassword(usernametxt, emailtxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser user=mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(),"Registration Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, StartupSetup.class));





                }
                else{
                    Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void LoginTask(String usernametxt, String emailtxt){

        mAuth.signInWithEmailAndPassword(usernametxt, emailtxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser user=mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, AppHomePage.class));




                }
                else{
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
