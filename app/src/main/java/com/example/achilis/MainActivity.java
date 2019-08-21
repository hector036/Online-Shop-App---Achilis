package com.example.achilis;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null) {
            SystemClock.sleep(3000);
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }else {
            SystemClock.sleep(3000);
            //Intent loginIntent = new Intent(MainActivity.this,RegisterActivity.class);
            Intent loginIntent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(loginIntent);
            finish();
        }


    }
}
