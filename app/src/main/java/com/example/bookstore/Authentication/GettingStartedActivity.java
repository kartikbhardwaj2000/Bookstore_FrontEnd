package com.example.bookstore.Authentication;

import com.example.bookstore.Authentication.Fragments.FragmentListener;
import com.example.bookstore.R;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GettingStartedActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);
    }

    public void startSignUp(View view){

        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }


}