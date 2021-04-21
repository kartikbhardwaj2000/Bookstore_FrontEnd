
package com.example.bookstore.CreateListing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.bookstore.Authentication.Fragments.Step1Fragment;
import com.example.bookstore.Authentication.Fragments.Step2Fragment;
import com.example.bookstore.Authentication.Fragments.Step3Fragment;
import com.example.bookstore.Authentication.Fragments.Step4Fragment;
import com.example.bookstore.CreateListing.Fragments.CreateListingStep1Fragment;
import com.example.bookstore.CreateListing.Fragments.CreateListingStep2Fragment;
import com.example.bookstore.CreateListing.Fragments.CreateListingStep3Fragment;
import com.example.bookstore.CreateListing.Fragments.CreateListingStep4Fragment;
import com.example.bookstore.CreateListing.Fragments.CreateListingStep5Fragment;
import com.example.bookstore.CreateListing.Fragments.Listener;
import com.example.bookstore.R;

import java.util.List;

public class CreateListingActivity extends AppCompatActivity implements Listener {

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);
        progressBar=findViewById(R.id.progress_bar);

        Toolbar supportToolBar= findViewById(R.id.tool_bar);
        progressBar =findViewById(R.id.progress_bar);

        setSupportActionBar(supportToolBar);

        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            setTitle("Sell your book");
        }
        addFirstFragment();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getFragments().size();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment=fragments.get(fragments.size()-1);
        if(fragment instanceof CreateListingStep1Fragment)
        {

            progressBar.setProgress(20);
        }
        if(fragment instanceof CreateListingStep2Fragment)
        {

            progressBar.setProgress(40);
        }
        if(fragment instanceof CreateListingStep3Fragment)
        {

            progressBar.setProgress(60);
        }
        if(fragment instanceof CreateListingStep4Fragment)
        {

            progressBar.setProgress(80);
        }
        if(fragment instanceof CreateListingStep5Fragment)
        {

            progressBar.setProgress(100);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNextClick(String nextFragName) {
        Log.v("onNextClick",nextFragName);
        Fragment nextFragment=null;
        switch (nextFragName)
        {
            case "step1":
                nextFragment=new CreateListingStep1Fragment();
                break;
            case "step2":
                nextFragment=new CreateListingStep2Fragment();
                progressBar.setProgress(40);
                break;
            case "step3":
                nextFragment=new CreateListingStep3Fragment();
                progressBar.setProgress(60);

                break;
            case "step4":
                nextFragment=new CreateListingStep4Fragment();
                progressBar.setProgress(80);

                break;
            case "step5":
                nextFragment=new CreateListingStep5Fragment();
                progressBar.setProgress(100);

                break;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        CreateListingStep1Fragment fragment = new CreateListingStep1Fragment();
        fragmentTransaction.replace(R.id.fragmentContainer,nextFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void addFirstFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        CreateListingStep1Fragment fragment = new CreateListingStep1Fragment();
        fragmentTransaction.add(R.id.fragmentContainer,fragment);
        fragmentTransaction.commit();

    }


}