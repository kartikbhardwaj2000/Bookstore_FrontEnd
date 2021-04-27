package com.example.bookstore.Authentication;

import com.example.bookstore.HomePage.HomeActivity;
import com.example.bookstore.R;
import com.example.bookstore.SessionManager;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GettingStartedActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

//        if(!isNewSession())
//        {
//
//            startHomeActivity();
//        }

    }

//    public void startHomeActivity()
//    {
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    public boolean isNewSession()
//    {
//        sessionManager=new SessionManager(this);
//
//        return sessionManager.getUSER_ID()==null;
//    }

    public void startSignUp(View view){

        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
        finish();
    }



}