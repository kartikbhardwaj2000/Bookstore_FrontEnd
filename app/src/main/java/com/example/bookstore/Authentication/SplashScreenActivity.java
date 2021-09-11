package com.example.bookstore.Authentication;
import com.example.bookstore.HomePage.HomeActivity;
import com.example.bookstore.R;
import com.example.bookstore.SessionManager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SplashScreenActivity extends AppCompatActivity {
    private SessionManager sessionManager;
   private String CHANNEL_ID="#113";
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
        createNotificationChannel();
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


    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            String name = "important channel";
            String description= "this channel is for receiving notifications ";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel= new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}