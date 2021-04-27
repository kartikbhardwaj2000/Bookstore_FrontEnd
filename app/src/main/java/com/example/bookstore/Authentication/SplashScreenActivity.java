package com.example.bookstore.Authentication;
import com.example.bookstore.HomePage.HomeActivity;
import com.example.bookstore.R;
import com.example.bookstore.SessionManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SplashScreenActivity extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isNewSession())
                {

                    startHomeActivity();
                }else {
                    startGettingStarted();
                }
            }
        },2*1000);



    }


    public void startHomeActivity()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean isNewSession()
    {
        sessionManager=new SessionManager(this);

        return sessionManager.getUSER_ID()==null;
    }

    public void startGettingStarted(){

        Intent intent = new Intent(this,GettingStartedActivity.class);
        startActivity(intent);
        finish();
    }


}